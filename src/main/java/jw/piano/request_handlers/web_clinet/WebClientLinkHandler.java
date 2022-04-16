package jw.piano.request_handlers.web_clinet;

import jw.piano.data.Settings;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.desing_patterns.mediator.interfaces.MediatorHandler;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.files.json.JsonUtility;
import org.bukkit.Bukkit;

import java.util.Base64;

@Injection
public class WebClientLinkHandler implements MediatorHandler<WebClientLinkRequest, String> {
    @Inject
    private Settings settings;

    @Override
    public String handle(final WebClientLinkRequest request) {
        if (request.getPlayer() == null || request.getPianoData() == null) {
            return null;
        }
        final var webClientDto = getWebClientLinkDto(request);
        final var payLoad = encodePayLoad(webClientDto);
        return getClientUrl(payLoad);
    }

    private WebClientLinkDto getWebClientLinkDto(final WebClientLinkRequest request) {
        final var pianoId = request.getPianoData().getUuid();
        return new WebClientLinkDto(
                settings.getServerURL(),
                settings.getWebSocketPort(),
                Long.toString(pianoId.getMostSignificantBits()),
                Long.toString(pianoId.getLeastSignificantBits()));
    }

    private String encodePayLoad(final WebClientLinkDto webClientLinkDto) {
        final var gson = JsonUtility.getGson();
        final var json = gson.toJson(webClientLinkDto);
        FluentPlugin.logSuccess(json);
        return Base64.getUrlEncoder().encodeToString(json.getBytes());
    }

    private String getClientUrl(String payload) {
        return new StringBuilder()
                .append("http://")
                .append(settings.getServerURL())
                .append(":")
                .append(settings.getWebClientPort())
                .append("/piano-client")
                .append("?payload=")
                .append(payload)
                .toString();
    }
}
