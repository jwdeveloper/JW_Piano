package jw.piano.web_http_server;

public class ServerResource {
    public final byte[] content;
    public final String name;
    public final String fileType;

    public ServerResource(byte[] content,String name,String type)
    {
        this.content = content;
        this.name = name;
        this.fileType =type;
    }
}
