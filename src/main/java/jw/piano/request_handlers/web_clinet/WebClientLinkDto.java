package jw.piano.request_handlers.web_clinet;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WebClientLinkDto
{
    private int port;

    private long a;

    private long b;
}
