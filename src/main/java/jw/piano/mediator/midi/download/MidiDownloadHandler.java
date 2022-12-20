package jw.piano.mediator.midi.download;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.mediator.api.MediatorHandler;
import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Injection
public class MidiDownloadHandler implements MediatorHandler<MidiDownload.Request, MidiDownload.Response> {

    private final Pattern pattern;


    public MidiDownloadHandler()
    {
        var regex = "/((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\\+\\$,\\w]+@)[A-Za-z0-9.-]+)((?:\\/[\\+~%\\/.\\w-_]*)?\\??(?:[-\\+=&;%@.\\w_]*)#?(?:[\\w]*))?)/";
        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }
    @Override
    public MidiDownload.Response handle(MidiDownload.Request request)
    {
        if(!validateUrl(request.fileUrl()))
        {
            return new MidiDownload.Response(false, "Invalid URL");
        }
        final var url = request.fileUrl();
        final var output = outputPath();
        FluentApi.tasks().taskAsync(unused ->
        {
            try
            {
                FileUtility.downloadFile(url,output);
                request.event().accept(new MidiDownload.Dto("midi",output,true,"file has been downloaded"));
            }
            catch (Exception e)
            {
                request.event().accept(MidiDownload.Dto.error("Error while downloading file"));
                FluentLogger.LOGGER.warning("Unable to download MIDI file from",url);
            }

        });
        return new MidiDownload.Response(true, "Downloading started");
    }

    private boolean validateUrl(String url)
    {
         return pattern.matcher(url).find();
    }

    private String outputPath()
    {
        return FluentApi.path()+FileUtility.separator()+"midi";
    }
}
