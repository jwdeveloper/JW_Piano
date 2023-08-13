/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package io.github.jwdeveoper.spigot.piano.core.mediator.piano.token_data;

import io.github.jwdeveloper.ff.core.files.json.JsonUtility;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.mediator.api.MediatorHandler;
import io.github.jwdeveloper.ff.extension.websocket.api.FluentWebsocket;
import org.bukkit.plugin.Plugin;

import java.util.Base64;

@Injection
public class TokenDataGeneratorHandler implements MediatorHandler<TokenData.Request, TokenData.Response> {

    private final FluentWebsocket websocket;
    private final Plugin plugin;
    @Inject
    public TokenDataGeneratorHandler(FluentWebsocket websocket, Plugin plugin)
    {
        this.websocket = websocket;
        this.plugin = plugin;
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
                Long.toString(pianoId.getLeastSignificantBits()),
                plugin.getDescription().getVersion());
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
