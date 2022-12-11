package jw.piano.mediator.piano.webclient_link;

import jw.fluent.plugin.implementation.modules.websocket.api.FluentWebsocket;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.mediator.api.MediatorHandler;
import jw.fluent.api.files.implementation.json.JsonUtility;

import java.util.Base64;

@Injection
public class WebClientLinkHandler implements MediatorHandler<WebClientLink.Request, WebClientLink.Response> {

    private final FluentWebsocket websocket;
    @Inject
    public WebClientLinkHandler(FluentWebsocket websocket)
    {
        this.websocket = websocket;
    }

    @Override
    public WebClientLink.Response handle(WebClientLink.Request request) {
        if (request.getPlayer() == null || request.getPianoData() == null) {
            return null;
        }
        final var webClientDto = getWebClientLinkDto(request);
        final var payLoad = encodePayLoad(webClientDto);
        final var link = getClientUrl(payLoad);
        return new WebClientLink.Response(link);
    }

    private WebClientLink.Dto getWebClientLinkDto(WebClientLink.Request request) {
        final var pianoId = request.getPianoData().getUuid();
        return new WebClientLink.Dto(
                websocket.getServerIp(),
                websocket.getPort(),
                Long.toString(pianoId.getMostSignificantBits()),
                Long.toString(pianoId.getLeastSignificantBits()));
    }

    private String encodePayLoad(final WebClientLink.Dto webClientLinkDto) {
        final var gson = JsonUtility.getGson();
        final var json = gson.toJson(webClientLinkDto);
        return Base64.getUrlEncoder().encodeToString(json.getBytes());
    }

    private String getClientUrl(String payload) {
        return payload;
    }


}
