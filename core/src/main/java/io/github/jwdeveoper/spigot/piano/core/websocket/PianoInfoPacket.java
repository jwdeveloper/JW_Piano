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

package io.github.jwdeveoper.spigot.piano.core.websocket;
import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.extension.websocket.api.annotations.PacketProperty;
import io.github.jwdeveloper.ff.extension.websocket.implementation.packet.WebSocketPacket;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.mediator.FluentMediator;
import io.github.jwdeveoper.spigot.piano.core.mediator.piano.info.PianoInfo;
import org.java_websocket.WebSocket;

import java.util.UUID;

@Injection
public class PianoInfoPacket extends WebSocketPacket {

    @PacketProperty
    public long a;
    @PacketProperty
    public long b;

    private final FluentMediator mediator;

    public PianoInfoPacket(FluentTaskFactory manager, FluentMediator mediator) {
        super(manager);
        this.mediator = mediator;
    }

    @Override
    public int getPacketId() {
        return 1;
    }


    @Override
    public void onPacketTriggered(final WebSocket webSocket)
    {
        final var request = new PianoInfo.Request(new UUID(a,b));
        final var data = mediator.resolve(request, PianoInfo.Request.class);
        final var response = new ActionResult<>(data,data != null);
        sendJson(webSocket, response);
    }
}