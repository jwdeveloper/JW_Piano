package jw.piano.mediator.midi.download;

import jw.fluent.api.utilites.java.StringUtils;

import java.util.function.Consumer;

public class MidiDownload
{
    public record Request(String fileUrl, Consumer<Dto> event)
    {

    }

    public record Response(boolean startDownloading, String message)
    {

    }

    public record Dto(String name, String path, boolean isSuccess, String errorMessage)
    {
        public static Dto error(String message)
        {
            return new Dto(StringUtils.EMPTY,StringUtils.EMPTY,false,message);
        }
    }
}
