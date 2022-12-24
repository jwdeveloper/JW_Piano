package jw.generator;

import jw.fluent.api.utilites.code_generator.ObserverClassGenerator;
import jw.piano.api.data.models.midi.MidiPlayerSettings;
import org.junit.Test;

public class ObserverGenerator
{


    @Test
    public void generate()
    {

         ObserverClassGenerator.generate(MidiPlayerSettings.class, "D:\\MC\\paper_1.19\\plugins\\JW_Piano");

    }

}
