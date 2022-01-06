package jw.piano.commands;

import jw.commands.FluentCommandGUI;
import jw.dependency_injection.Injectable;
import jw.piano.data.Settings;
import jw.piano.gui.MenuGUI;

@Injectable(autoInit = true)
public class MainCommand extends FluentCommandGUI
{
    private final Settings settings;

    public MainCommand(Settings settings) {
        super("piano2",MenuGUI.class);
        this.settings = settings;
    }

    @Override
    public void onInitialize()
    {
        this.addSubCommand("texturepack",(player, args) ->
        {
              player.setTexturePack(settings.texturepackURL);
        });

    }
}
