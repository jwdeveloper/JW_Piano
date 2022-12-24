package jw.piano.core.mediator.piano.token_data;

import jw.fluent.plugin.implementation.modules.websocket.api.FluentWebsocket;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.mediator.api.MediatorHandler;
import jw.fluent.api.files.implementation.json.JsonUtility;

import java.util.Base64;

@Injection
public class TokenDataGeneratorHandler implements MediatorHandler<TokenData.Request, TokenData.Response> {

    private final FluentWebsocket websocket;
    @Inject
    public TokenDataGeneratorHandler(FluentWebsocket websocket)
    {
        this.websocket = websocket;
    }

    @Override
    public TokenData.Response handle(TokenData.Request request) {
        if ( request.getPianoData() == null) {
            return null;
        }
        final var webClientDto = getWebClientLinkDto(request);
        final var payLoad = encodePayLoad(webClientDto);
        final var link = getClientUrl(payLoad);
        return new TokenData.Response(link);
    }

    private TokenData.Dto getWebClientLinkDto(TokenData.Request request) {
        final var pianoId = request.getPianoData().getUuid();
        return new TokenData.Dto(
                websocket.getServerIp(),
                websocket.getPort(),
                Long.toString(pianoId.getMostSignificantBits()),
                Long.toString(pianoId.getLeastSignificantBits()));
    }

    private String encodePayLoad(final TokenData.Dto webClientLinkDto) {
        final var gson = JsonUtility.getGson();
        final var json = gson.toJson(webClientLinkDto);
        return Base64.getUrlEncoder().encodeToString(json.getBytes());
    }

    private String getClientUrl(String payload) {
        return payload;
    }


}
