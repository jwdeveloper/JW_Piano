
/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package jw.piano.spigot;

import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.spigot.documentation.api.DocumentationDecorator;
import jw.fluent.api.spigot.documentation.api.models.Documentation;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.FluentPlugin;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.api.data.PluginConsts;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.charset.StandardCharsets;

public class PluginDocumentation extends DocumentationDecorator {

    private final Plugin plugin;

    public PluginDocumentation() {
        this.plugin = FluentApi.plugin();
    }

    @Override
    public void decorate(Documentation documentation) {

        createHeader(documentation);
        createDescription(documentation);
        createOraxenInfo(documentation);
        createApiDocumentation(documentation);
        createFAQInfo(documentation);
        addVideo("https://www.youtube.com/watch?v=0KSN7dfi7PQ&ab_channel=JW", documentation);
        addVideo("https://www.youtube.com/watch?v=PSbwsbX7xc0&t=27s&ab_channel=JW", documentation);
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

        addText("This is only plugin documentation changing it will NOT affect plugin in any way", documentation, "spigot-ignore","github-ignore");

        addText("A Minecraft plugin that adds a playable piano to the game would allow players to interact with a piano in the game world.", documentation, "plugin-ignore");
        addText("This piano would function just like a real-life piano, allowing players to play individual notes or full melodies using their real keyboard with Desktop app connection.", documentation,"plugin-ignore");
        addText("The plugin would add a new level of creativity and musical expression to the game, allowing players to showcase their musical talents and compose their own tunes.", documentation,"plugin-ignore");
        addText("Additionally, the piano could be used as a decorative item in the game world, adding a new element of realism and immersion to the game.", documentation,"plugin-ignore");
        addText("Overall, this plugin would be a fun and unique addition to Minecraft, providing players with a new way to interact with the game world and express themselves.", documentation,"plugin-ignore");


        addText(documentation);
        addLink("Download Desktop App", PluginConsts.CLIENT_APP_URL, documentation);
        addLink("Download Resourcepack", PluginConsts.RESOURCEPACK_URL, documentation);
        addText(documentation);





        addText(documentation);
    }


    public void createFAQInfo(Documentation documentation)
    {
        addText("[SPOILER=\"Common issues\"][B]Common issues:[/B]", documentation, "github-ignore", "plugin-ignore");
        addText("<details>", documentation, "spigot-ignore", "plugin-ignore");
        addText("<summary>Common issues</summary>", documentation, "spigot-ignore", "plugin-ignore");


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
        addText("Wrong: `craftplayer.com:22225`", documentation);
        addText(documentation);
        addText("Correct: `craftplayer.com`", documentation);
        addText(documentation);
        addListMember("When you are running server locally set value to `localhost` to `plugin.websocket.server-ip`", documentation);
        addListMember("When above solutions does not help set IP that you use in Minecraft server lists to `plugin.websocket.server-ip`", documentation);

        addText("</details>", documentation, "spigot-ignore", "plugin-ignore");
        addText("[/SPOILER]", documentation, "github-ignore", "plugin-ignore");
    }

    public void createOraxenInfo(Documentation documentation) {

        var in = plugin.getResource("oraxen/jw_piano_oraxen_config.yml");
        if (in == null) {
            return;
        }

        var content = StringUtils.EMPTY;
       try (in) {
            var charset = StandardCharsets.UTF_8;
            var bytes = in.readAllBytes();
            content = new String(bytes, charset);
        } catch (Exception e) {
           FluentLogger.LOGGER.log("oraxen documentation not generated");
            return;
        }

        addText("[SPOILER=\"Oraxen\"][B]Oraxen:[/B]", documentation, "github-ignore", "plugin-ignore");
        addText("<details>", documentation, "spigot-ignore", "plugin-ignore");
        addText("<summary>Oraxen</summary>", documentation, "spigot-ignore", "plugin-ignore");


        addText(documentation);
        addText("Oraxen configuration", documentation, "bold","plugin-ignore");
        addListMember("Setup for all users that willing to use Piano with Oraxen", documentation);
        addLink("open piano Oraxen config file","https://github.com/jwdeveloper/JW_Piano/blob/master/src/main/resources/oraxen/jw_piano_oraxen_config.yml", documentation);

        addYml(content, documentation,"plugin-ignore");

        addText("</details>", documentation, "spigot-ignore", "plugin-ignore");
        addText("[/SPOILER]", documentation, "github-ignore", "plugin-ignore");


    }

    public void createApiDocumentation(Documentation documentation) {
        addText("[SPOILER=\"API for developers\"][B]API for plugin developers:[/B]", documentation, "github-ignore", "plugin-ignore");
        addText("<details>", documentation, "spigot-ignore", "plugin-ignore");
        addText("<summary>API for plugin developers</summary>", documentation, "spigot-ignore", "plugin-ignore");


        addText(documentation);
        addText("JW_Piano provides programming API to manipulate Pianos behaviour.", documentation, "plugin-ignore");
        addText("You can use it but adding JW_Piano.jar as soft dependency to your Plugin", documentation, "plugin-ignore");

        addText(" ", documentation);
        addText("Create Piano", documentation, "bold", "plugin-ignore");
        addCode("""
                       
                        public void creatingPiano(Player player) {
                         Optional<Piano> optional = PianoApi.create(player.getLocation(), "new piano");
                         if (optional.isEmpty()) {
                            Bukkit.getConsoleSender().sendMessage("Unable to create piano ;<");
                            return;
                         }
                         Piano piano = optional.get();
                        }
                """, documentation);
        addText(" ", documentation);
        addText("Register new skin", documentation, "bold", "plugin-ignore");
        addCode("""
                  
                  
                  public void addSkin(Piano piano) {
                     int customModelId = 100;
                     String name = "custom skin";
                     ItemStack itemStack = new ItemStack(Material.STICK);
                     PianoSkin customSkin = new PianoSkin(customModelId, name, itemStack);
                     piano.getSkinManager().register(customSkin);
                     piano.getSkinManager().setCurrent(customSkin);
                  }
                """, documentation);
        addText("Register new effect", documentation, "bold", "plugin-ignore");
        addCode("""
                  
                  
                  public void addNewEffect(Piano piano) {
                        EffectInvoker customEffect = new CustomEffect();
                        piano.getEffectManager().register(customEffect);
                        piano.getEffectManager().setCurrent(customEffect);
                    }
                               
                               
                    public class CustomEffect implements EffectInvoker {
                        @Override
                        public String getName() {
                            return "custom";
                        }
                               
                        @Override
                        public void onNote(PianoKey pianoKey, Location location, int noteIndex, int velocity, Color color) {
                            Bukkit.getConsoleSender().sendMessage(color + "Note: " + noteIndex + "  Volume:" + velocity);
                            location.getWorld().spawnParticle(Particle.NOTE, location, 1);
                        }
                               
                        @Override
                        public void onDestroy() {
                            Bukkit.getConsoleSender().sendMessage(getName() + "Destroyed");
                        }
                               
                        @Override
                        public void onCreate() {
                            Bukkit.getConsoleSender().sendMessage(getName() + "Created");
                        }
                               
                        @Override
                        public void refresh() {
                            Bukkit.getConsoleSender().sendMessage(getName() + "Refreshed");
                        }
                    }
                  }
                """, documentation);


        addText("</details>", documentation, "spigot-ignore", "plugin-ignore");
        addText("[/SPOILER]", documentation, "github-ignore", "plugin-ignore");
    }
}
