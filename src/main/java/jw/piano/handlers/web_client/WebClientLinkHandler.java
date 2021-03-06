package jw.piano.handlers.web_client;

import jw.piano.data.Settings;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.desing_patterns.mediator.interfaces.MediatorHandler;
import jw.spigot_fluent_api.utilites.files.json.JsonUtility;

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
                settings.getServerIp(),
                settings.getWebSocketPort(),
                Long.toString(pianoId.getMostSignificantBits()),
                Long.toString(pianoId.getLeastSignificantBits()));
    }

    private String encodePayLoad(final WebClientLinkDto webClientLinkDto) {
        final var gson = JsonUtility.getGson();
        final var json = gson.toJson(webClientLinkDto);
        return Base64.getUrlEncoder().encodeToString(json.getBytes());
    }

    private String getClientUrl(String payload) {
        return payload;
    }
}
