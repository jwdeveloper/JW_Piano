package jw.piano.handlers.webclient;

import jw.piano.api.data.models.PianoData;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.entity.Player;

public class WebClientLink
{

    @Data
    @AllArgsConstructor
    public static class Dto
    {
        private String serverIP;

        private int port;

        private String a;

        private String b;
        //JavaScript is too stupid to properly translate Long from Json so I was forced to changed 'a' and 'b' to String
    }

    @Data
    @AllArgsConstructor
    public static class Request
    {
        private Player player;

        private PianoData pianoData;
    }

    @Data
    @AllArgsConstructor
    public static class Response
    {
        private String url;
    }

}
