package jw.piano.request_handlers.web_clinet;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebClientLinkDto
{
    private String serverIP;

    private int port;

    private String a;

    private String b;
    //JavaScript is too stupid to properly translate Long from Json so I was forced to changed 'a' and 'b' to String
}
