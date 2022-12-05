package jw.piano.spigot;

import jw.fluent.api.spigot.documentation.api.DocumentationDecorator;
import jw.fluent.api.spigot.documentation.api.models.Documentation;
import jw.piano.api.data.PluginConfig;
import jw.piano.gameobjects.utils.Consts;

public class PluginDocumentation extends DocumentationDecorator {
    @Override
    public void decorate(Documentation documentation) {

        createHeader(documentation);
        createDescription(documentation);

        addVideo("https://www.youtube.com/watch?v=F4iKXAMIioo&feature=emb_logo&ab_channel=JW",documentation);
        addVideo("https://www.youtube.com/watch?v=AxljLMjh4Ac&feature=emb_logo&ab_channel=JW",documentation);
        addImage("https://proxy.spigotmc.org/65d5465e8dd82d7e765c4b783222f26fc31eb1e1?url=https%3A%2F%2Fwww.linkpicture.com%2Fq%2F8_375.png",documentation);
        addImage("https://proxy.spigotmc.org/203a8caf5aced0a92081d4df8329e46a6630deab?url=https%3A%2F%2Fwww.linkpicture.com%2Fq%2FSupport-13.png",documentation);
    }


    private void createHeader(Documentation documentation) {
        addImage("https://proxy.spigotmc.org/9ca3b3577528bb7fc9bbfd2f11cec73c5c2f2109?url=https%3A%2F%2Fwww.linkpicture.com%2Fq%2Fpiano.png",documentation);
        addText("<p align=\"center\">",documentation,"spigot-ignore", "plugin-ignore");
        addImageWithLink("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/discord.png", PluginConfig.DISCORD_URL, documentation);
        addImageWithLink( "https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/github.png", PluginConfig.GITHUB_URL, documentation);
        addImageWithLink("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/spigot.png", PluginConfig.SPIGOT_URL, documentation);
        addText("</p>",documentation,"spigot-ignore", "plugin-ignore");
    }

    private void createDescription(Documentation documentation) {

        addText("JW Piano is a one in own kind plugin that give you opportunity to play piano with your friends! But this is only top on the iceberg.",documentation);
        addText("If you want to play MIDI file or send notes from real piano, download desktop app and connect to your server!",documentation);


        addText("Common problems:", documentation, "bold");
        addListMember("When you've got error 'Can't connect to server' on desktop app open new Port at your hosting panel. Then go to plugins/jw_piano/config.yml and change value of port", documentation);
        addListMember("When you have some problems with resourcepack download it directly: "+PluginConfig.TEXTURES_URL, documentation);
    }


}
