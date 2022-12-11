package jw.piano.spigot.gameobjects.models.key;

import jw.fluent.api.spigot.gameobjects.api.GameObject;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.tasks.FluentTasks;
import jw.piano.data.PluginConsts;
import jw.piano.data.enums.PianoEffect;
import jw.piano.factory.ArmorStandFactory;
import jw.piano.spigot.effects.EffectManager;
import jw.piano.factory.sounds.SoundPlayerFactory;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Comparator;

public class PianoKeyGroupGameObject extends GameObject
{
    private final PianoKeyGameObject[] pianoKeys = new PianoKeyGameObject[88];
    private final int TICKS = 10;
    private final EffectManager effectManager;
    private final String uuid;
    private final ArmorStandFactory armorStandFactory;
    private final SoundPlayerFactory soundPlayerFactory;

    public PianoKeyGroupGameObject(String uuid, ArmorStandFactory armorStandFactory)
    {
        effectManager = new EffectManager();
        soundPlayerFactory = FluentApi.container().findInjection(SoundPlayerFactory.class);
        this.armorStandFactory = armorStandFactory;
        this.uuid = uuid;
    }


    @Override
    public void onCreated() {
        effectManager.create();
        var startKeysLocation = location.clone().add(-1.5, -0.4, 0.3f);
        startKeysLocation.setDirection(new Vector(0, 1, 0));
        var key = 1;
        for (int i = 1; i <= 88; i++) {
            if (i > 3 && i < 88) {
                key = (i - 4) % 12;
            }
            if (i <= 3) {
                key = i + 8;
            }

            switch (key) {
                case 1, 3, 6, 8, 10:
                    pianoKeys[i - 1] = new PianoKeyGameObject(
                            uuid,
                            soundPlayerFactory,
                            effectManager,
                            armorStandFactory,
                            startKeysLocation.clone().add(0.025f, 0.02f, -0.05f),
                            true,
                            i + PluginConsts.MIDI_KEY_OFFSET - 1);

                    break;
                default:
                    pianoKeys[i - 1] = new PianoKeyGameObject(
                            uuid,
                            soundPlayerFactory,
                            effectManager,
                            armorStandFactory,
                            startKeysLocation.clone().add(0.05f, 0, 0),
                            false,
                            i + PluginConsts.MIDI_KEY_OFFSET - 1);
                    startKeysLocation = startKeysLocation.clone().add(0.05f, 0, 0);
                    break;
            }
            addGameComponent(pianoKeys[i-1]);
        }
    }


    @Override
    public void onDestroy()
    {
        effectManager.destroy();
    }

    public void invokeNote(int pressed, int index, int velocity, boolean isPedalPressed) {
        if (index < PluginConsts.MIDI_KEY_OFFSET)
            return;
        if (index - PluginConsts.MIDI_KEY_OFFSET > pianoKeys.length - 1)
            return;

        if (pressed != 0) {
            pianoKeys[index - PluginConsts.MIDI_KEY_OFFSET].press(index, velocity, isPedalPressed);

        } else
            pianoKeys[index - PluginConsts.MIDI_KEY_OFFSET].release();
    }

    public boolean onPlayerClick(Player player, boolean isPedalPressed)
    {
        for (final var pianoKey : getSortedKeys())
        {
            if (!pianoKey.hasCollection(player.getEyeLocation(), pianoKey.getRadious())) {
                continue;
            }
            FluentTasks.taskTimer(TICKS, (iteration, task) ->
                    {
                        pianoKey.press(isPedalPressed);
                    }).stopAfterIterations(1)
                    .onStop(task ->
                    {
                        pianoKey.release();
                    }).run();
            return true;
        }
        return false;
    }

    public void refreshKeys()
    {
        for (final var pianoKey : pianoKeys) {
            pianoKey.release();
        }
    }

    public final void setEffect(PianoEffect pianoEffect) {
        effectManager.setEffect(pianoEffect);
    }

    public void setVolume(int volume) {
        for (var key : pianoKeys) {
            key.setVolume(volume);
        }
    }

    private PianoKeyGameObject[] getSortedKeys() {
        var keys = pianoKeys.clone();
        Arrays.sort(keys, new Comparator<PianoKeyGameObject>() {
            @Override
            public int compare(PianoKeyGameObject o1, PianoKeyGameObject o2) {
                return Boolean.compare(o1.isWhite(), o2.isWhite());
            }
        });
        return keys;
    }
}
