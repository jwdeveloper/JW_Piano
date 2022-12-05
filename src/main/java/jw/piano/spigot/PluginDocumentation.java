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
        addImage("https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/style.png",documentation);
        addImage("https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/webclient.png",documentation);
    }


    private void createHeader(Documentation documentation) {
        addImage("https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/banner.png",documentation);
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
