package jw.piano.core.mediator.piano.token_data;

import jw.piano.api.data.models.PianoData;
import lombok.AllArgsConstructor;
import lombok.Data;

public class TokenData
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
        private PianoData pianoData;
    }

    @Data
    @AllArgsConstructor
    public static class Response
    {
        private String url;
    }

}
