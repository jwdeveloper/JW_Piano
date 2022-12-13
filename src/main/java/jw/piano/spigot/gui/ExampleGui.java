package jw.piano.spigot.gui;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.desing_patterns.observer.implementation.ObserverBag;
import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.chest_ui.ChestUI;
import jw.fluent.api.spigot.gui.fluent_ui.styles.FluentButtonStyle;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.code_generator.SimpleMessageGenerator;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.FluentFiles;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.data.enums.PianoEffect;
import jw.piano.data.models.PianoData;
import jw.piano.factory.ArmorStandFactory;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@PlayerContext
@Injection
public class ExampleGui extends ChestUI {

    private final FluentFiles fluentFiles;
    private final FluentButtonStyle fluentButtonStyle;

    private final FluentChestUI fluentButtonUI;

    @Inject
    public ExampleGui(FluentFiles fluentFiles,
                      FluentButtonStyle buttonStyle,
                      FluentChestUI fluentButtonUI) {
        super("siema", 6);
        this.fluentFiles = fluentFiles;
        this.fluentButtonStyle = buttonStyle;
        this.fluentButtonUI = fluentButtonUI;
    }


    public List<String> generated() {
        var builder = new MessageBuilder();

        //line 1:
        builder.newLine();

        //line 2:                   %3%l[%bPiano%3%l]
        builder.text("                 ").
                color(ChatColor.DARK_AQUA).
                color(ChatColor.BOLD).
                text("[").
                color(ChatColor.AQUA).
                text("Piano").
                color(ChatColor.DARK_AQUA).
                color(ChatColor.BOLD).
                text("]").
                newLine();

        //line 3: %b%l--------------------------
        builder.color(ChatColor.AQUA).
                color(ChatColor.BOLD).
                text("--------------------------").
                newLine();

        //line 4:
        builder.newLine();

        //line 5: %2 Uruchom aplikacje JW API w  które
        builder.color(ChatColor.DARK_GREEN).
                text(" Uruchom aplikacje JW API w  które ").
                newLine();

        //line 6: %2 będziesz mogł stworzyć własny
        builder.color(ChatColor.DARK_GREEN).
                text(" będziesz mogł stworzyć własny ").
                newLine();

        //line 7: %2 ukinalny tekst
        builder.color(ChatColor.DARK_GREEN).
                text(" ukinalny tekst").
                newLine();

        //line 8:
        builder.newLine();

        //line 9: Mordo
        builder.text("ordo").
                newLine();

        //line 10:  %3>%3%o Click info <
        builder.color(ChatColor.DARK_AQUA).
                text(">").
                color(ChatColor.DARK_AQUA).
                color(ChatColor.ITALIC).
                text(" Click info <").
                newLine();

        //line 11: %3%l--------------------------
        builder.color(ChatColor.DARK_AQUA).
                color(ChatColor.BOLD).
                text("--------------------------").
                newLine();

        //line 12:   %3%l[%bLeft%3%l]  %b%l> %7Open menu
        builder.text(" ").
                color(ChatColor.DARK_AQUA).
                color(ChatColor.BOLD).
                text("[").
                color(ChatColor.AQUA).
                text("Left").
                color(ChatColor.DARK_AQUA).
                color(ChatColor.BOLD).
                text("]  ").
                color(ChatColor.AQUA).
                color(ChatColor.BOLD).
                text("> ").
                color(ChatColor.GRAY).
                text("Open menu").
                newLine();

        //line 13:
        builder.newLine();

        //line 14:   %3%l[%bRight%3%l] %b%l> %7Close menu
        builder.text(" ").
                color(ChatColor.DARK_AQUA).
                color(ChatColor.BOLD).
                text("[").
                color(ChatColor.AQUA).
                text("Right").
                color(ChatColor.DARK_AQUA).
                color(ChatColor.BOLD).
                text("] ").
                color(ChatColor.AQUA).
                color(ChatColor.BOLD).
                text("> ").
                color(ChatColor.GRAY).
                text("Close menu").
                newLine();

        //line 15:
        builder.newLine();

        //line 16:   %3%l[%bShift%3%l] %b%l> %7Close menu
        builder.text(" ").
                color(ChatColor.DARK_AQUA).
                color(ChatColor.BOLD).
                text("[").
                color(ChatColor.AQUA).
                text("Shift").
                color(ChatColor.DARK_AQUA).
                color(ChatColor.BOLD).
                text("] ").
                color(ChatColor.AQUA).
                color(ChatColor.BOLD).
                text("> ").
                color(ChatColor.GRAY).
                text("Close menu").
                newLine();

        //line 17: %3%l--------------------------
        builder.color(ChatColor.DARK_AQUA).
                color(ChatColor.BOLD).
                text("--------------------------").
                newLine();


        return List.of(builder.toArray());
    }


    private PianoEffect effect = PianoEffect.FLYING_NOTE;
    private Observer<PianoEffect> enumObserver = new Observer(this, "effect");
    private ObserverBag<Boolean> isOpenObserver = new ObserverBag(false);
    private ObserverBag<Integer> intObserver = new ObserverBag(0);

    @Override
    protected void onInitialize() {

        fluentButtonUI
                .buttonFactory()
                .observeBool(isOpenObserver.getObserver(), options ->
                {

                })
                .setDescription(e ->
                {
                    e.setTitle("Bool observer");
                })
                .setLocation(3, 3)
                .build(this);

        fluentButtonUI
                .buttonFactory()
                .observeInt(intObserver.getObserver(), options ->
                {
                    options.setPrefix("ELO");
                    options.setMinimum(10);
                    options.setMaximum(200);
                    options.setYield(10);
                })
                .setDescription(e ->
                {
                    e.setTitle("Integer observer");
                })
                .setLocation(3, 4)
                .build(this);

        fluentButtonUI
                .buttonFactory()
                .observeEnum(enumObserver, options ->
                {

                })
                .setDescription(e ->
                {
                    e.setTitle("Enum observer");
                })
                .setLocation(3, 5)
                .build(this);


        var indexObserver = new ObserverBag<Integer>(0);
        var values = new ArrayList<PianoData>();

        var a = new PianoData();
        a.setName("Piano 1");
        a.setVolume(10);

        var b = new PianoData();
        b.setName("Piano 2");
        b.setVolume(20);
        values.add(a);
        values.add(b);


        for (var i = 1; i <= 8; i++) {
            var index = i;
            var factory = new ArmorStandFactory();
            fluentButtonUI.buttonBuilder()
                    .setLocation(1, i)
                    .setMaterial(Material.ARROW, i)
                    .setOnLeftClick((player, button) ->
                    {
                        player.getInventory().setItemInMainHand(button.getItemStack());
                        player.getEquipment().setHelmet(button.getItemStack());
                    })
                    .setDescription(buttonDescriptionInfoBuilder ->
                    {

                        buttonDescriptionInfoBuilder.setTitle("Figure");
                    }).build(this);
        }


        fluentButtonUI.buttonBuilder()
                .setLocation(2, 0)
                .setMaterial(Material.BELL)
                .setOnLeftClick((player, button) ->
                {
                    var factory = new ArmorStandFactory();

                    for (var i = 1; i <= 8; i++) {
                        var btn = new ButtonUI();
                        btn.setCustomMaterial(Material.ARROW,i);
                        var armorstand = factory.create(player.getLocation().clone().add(i, 0, 0), "siema");
                        armorstand.getEquipment().setHelmet(btn.getItemStack());
                    }
                })
                .setDescription(buttonDescriptionInfoBuilder ->
                {

                    buttonDescriptionInfoBuilder.setTitle("Create chess");
                }).build(this);

        fluentButtonUI.buttonBuilder()
                .setLocation(1, 2)
                .setMaterial(Material.ARROW, 100)
                .setDescription(buttonDescriptionInfoBuilder ->
                {
                    buttonDescriptionInfoBuilder.setTitle("GUITAR");
                }).build(this);

        var listBtn = fluentButtonUI
                .buttonFactory()
                .observeList(indexObserver.getObserver(), values, options ->
                {
                    options.setOnNameMapping(pianoData ->
                    {
                        return ChatColor.WHITE + pianoData.getName() + " Volume:" + pianoData.getVolume();
                    });
                    options.setOnSelectionChanged(pianoDataonSelectEvent ->
                    {

                    });
                })
                .setDescription(e ->
                {
                    e.setTitle("List observer");
                    e.addDescriptionLine("SIEMA SIEMA");
                    e.addDescriptionLine("SIEMA SIEMA2");
                })
                .setOnShiftClick((player, button) ->
                {

                })
                .setLocation(3, 6)
                .build(this);

        fluentButtonUI.buttonBuilder()
                .setMaterial(Material.DIAMOND_BLOCK)
                .setDescription(buttonDescriptionInfoBuilder ->
                {
                    buttonDescriptionInfoBuilder.setTitle("DODAJ/USUN");
                })
                .setOnLeftClick((player, button) ->
                {
                    var x = new PianoData();
                    x.setName(UUID.randomUUID().toString().substring(0, 5));
                    values.add(x);
                    refreshButton(listBtn);
                })
                .setOnRightClick((player, button) ->
                {
                    values.remove(values.size() - 1);
                    refreshButton(listBtn);
                })
                .setLocation(3, 7)
                .build(this);


        var filePath = FluentApi.path() + FileUtility.separator() + "dupa.txt";
        var watcher = fluentFiles.createFileWatcher(filePath);
        var button = fluentButtonUI.buttonBuilder()
                .setLocation(4, 4)
                .build(this);
        watcher.onFileChanged(path ->
        {
            FluentLogger.LOGGER.info("File changed");
            FluentApi.tasks().task(unused ->
            {
                try {

                    var input = FluentApi.path() + FileUtility.separator() + "dupa.txt";
                    var output = FluentApi.path() + FileUtility.separator() + "output.txt";
                    SimpleMessageGenerator.generate(input, output);

                    List<String> allLines = Files.readAllLines(Path.of(filePath));
                    for (int i = 0; i < allLines.size(); i++) {
                        var line = allLines.get(i);
                        line = line.replaceAll("\\%", "§");

                        if (line.contains("#")) {
                            line = translateHexCodes(line);
                        }

                        allLines.set(i, line);
                    }

                    button.setTitle(allLines.get(0));
                    for (var i = 0; i < 10; i++) {
                        FluentApi.messages().chat().text(" ").send(getPlayer());
                    }
                    var res = new ArrayList<String>();
                    for (var i = 1; i < allLines.size(); i++) {
                        res.add(allLines.get(i));
                    }
                    button.setDescription(res);
                    for (var line : allLines) {
                        FluentApi.messages().chat().text(line).send(getPlayer());
                    }

                    refreshButton(button);


                } catch (Exception e) {
                    FluentLogger.LOGGER.error("ERROR FILES", e);
                }
            });
        });

    }

    public static final Pattern HEX_PATTERN = Pattern.compile("&#(\\w{5}[0-9a-f])");

    public String translateHexCodes(String textToTranslate) {
        var matcher = HEX_PATTERN.matcher(textToTranslate);
        var buffer = new StringBuilder();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of("#" + matcher.group(1)).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());

    }
}
