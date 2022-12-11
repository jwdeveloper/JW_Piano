package jw.piano.spigot.gui.piano;

import jw.fluent.api.spigot.inventory_gui.button.button_observer.ButtonNotifier;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.ButtonObserverEvent;
import jw.fluent.api.utilites.messages.Emoticons;
import jw.piano.data.PluginConsts;
import jw.piano.data.models.PianoSkin;
import org.bukkit.Material;

import java.util.List;

public final class SkinModelNotifier implements ButtonNotifier<Integer> {
    private final List<PianoSkin> skins;
    private final String prefix;
    private int currentIndex;

    public SkinModelNotifier(List<PianoSkin> skins) {
        this.skins = skins;
        this.prefix = org.bukkit.ChatColor.AQUA + Emoticons.dot + " ";
    }


    @Override
    public void onClick(ButtonObserverEvent<Integer> event) {
        if (skins.size() == 0) {
            return;
        }

        currentIndex = findIndex(event.getValue());
        currentIndex = (currentIndex + 1) % skins.size();
        var id = skins.get(currentIndex);
        event.getObserver().setValue(id.getCustomModelId());
    }

    @Override
    public void onValueChanged(ButtonObserverEvent<Integer> event) {
        final var button = event.getButton();
        final var description = new String[skins.size()];
        for (var i = 0; i < skins.size(); i++) {
            if (skins.get(i).getCustomModelId() == event.getValue()) {
                description[i] = prefix + skins.get(i).getName();

                if (event.getValue() == 0) {
                    button.setMaterial(Material.NOTE_BLOCK);
                } else {
                    button.setCustomMaterial(PluginConsts.SKINS_MATERIAL, event.getValue());
                }


            } else {
                description[i] = skins.get(i).getName();
            }
        }
        button.setDescription(description);
    }

    private int findIndex(Integer pianoId) {
        var temp = 0;
        for (var skin : skins) {
            if (skin.getCustomModelId() == pianoId) {
                return temp;
            }
            temp++;
        }
        return -1;
    }
}