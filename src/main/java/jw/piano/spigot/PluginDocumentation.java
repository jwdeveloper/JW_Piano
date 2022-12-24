package jw.piano.spigot;

import jw.fluent.api.spigot.documentation.api.DocumentationDecorator;
import jw.fluent.api.spigot.documentation.api.models.Documentation;
import jw.piano.api.data.PluginConsts;

public class PluginDocumentation extends DocumentationDecorator {
    @Override
    public void decorate(Documentation documentation) {

        createHeader(documentation);
        createDescription(documentation);

        addVideo("https://www.youtube.com/watch?v=F4iKXAMIioo&t=2s&ab_channel=JW",documentation);
        addVideo("https://www.youtube.com/watch?v=PSbwsbX7xc0&t=27s&ab_channel=JW",documentation);
        addImage("https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/style.png", documentation);
        addImage("https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/webclient.png", documentation);
    }


    private void createHeader(Documentation documentation) {
        addImage("https://raw.githubusercontent.com/jwdeveloper/JW_Piano/master/resources/images/banner.png", documentation);
        addText("<p align=\"center\">", documentation, "spigot-ignore", "plugin-ignore");
        addText("[CENTER]", documentation, "github-ignore", "plugin-ignore");
        addImageWithLink("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/discord.png", PluginConsts.DISCORD_URL, documentation);
        addImageWithLink("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/github.png", PluginConsts.GITHUB_URL, documentation);
        addImageWithLink("https://raw.githubusercontent.com/jwdeveloper/SpigotFluentAPI/master/resources/social-media/spigot.png", PluginConsts.SPIGOT_URL, documentation);
        addText("[/CENTER]", documentation, "github-ignore", "plugin-ignore");
        addText("</p>", documentation, "spigot-ignore", "plugin-ignore");

    }

    private void createDescription(Documentation documentation) {

        addText(documentation);
        addText("A Minecraft plugin that adds a playable piano to the game would allow players to interact with a piano in the game world.", documentation);
        addText("This piano would function just like a real-life piano, allowing players to play individual notes or full melodies using their real keyboard with Desktop app connection.", documentation);
        addText("The plugin would add a new level of creativity and musical expression to the game, allowing players to showcase their musical talents and compose their own tunes.", documentation);
        addText("Additionally, the piano could be used as a decorative item in the game world, adding a new element of realism and immersion to the game.", documentation);
        addText("Overall, this plugin would be a fun and unique addition to Minecraft, providing players with a new way to interact with the game world and express themselves.", documentation);



        addText(documentation);
        addLink("Download Desktop App", PluginConsts.CLIENT_APP_URL, documentation);
        addLink("Download Resourcepack", PluginConsts.RESOURCEPACK_URL, documentation);
        addText(documentation);




        addText("[SPOILER=\"Common issues\"][B]Common issues:[/B]",documentation,"github-ignore", "plugin-ignore");
        addText("<details>",documentation,"spigot-ignore", "plugin-ignore");
        addText("<summary>Common issues</summary>",documentation,"spigot-ignore", "plugin-ignore");


        addText(documentation);
        addText("Resourcepack", documentation, "bold");
        addListMember("When you have some problems with resourcepack download it directly", documentation);

        addText("Desktop app configuration, `config.yml` > `plugin.websocket.server-ip`", documentation, "bold");
        addListMember("Make sure port you are trying to use is open", documentation);
        addListMember("When you've got problems with connection try to change `plugin.websocket.server-ip` or  `plugin.websocket.port`", documentation);
        addListMember("Check if you need to create new port in the server hosting panel and then set in to `plugin.websocket.port`", documentation);
        addListMember("When your server use proxy use Proxy IP to `plugin.websocket.server-ip`", documentation);
        addListMember("When you server IP has port ignore port. Example: ", documentation);
        addText(documentation);
        addText("Wrong: `craftplayer.com:22225`",documentation);
        addText(documentation);
        addText("Correct: `craftplayer.com`",documentation);
        addText(documentation);
        addListMember("When you are running server locally set value to `localhost` to `plugin.websocket.server-ip`", documentation);
        addListMember("When above solutions does not help set IP that you use in Minecraft server lists to `plugin.websocket.server-ip`", documentation);

        addText("</details>",documentation,"spigot-ignore", "plugin-ignore");
        addText("[/SPOILER]",documentation,"github-ignore", "plugin-ignore");



        addText(documentation);
    }
}
