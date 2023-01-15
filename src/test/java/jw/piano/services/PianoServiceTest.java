/*
 * MIT License
 *
 * Copyright (c)  2023. jwdeveloper
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package jw.piano.services;

import be.seeseemelk.mockbukkit.entity.ArmorStandMock;
import jw.fluent.api.utilites.FluentApiMock;
import jw.piano.api.data.config.PluginConfig;
import jw.piano.api.data.models.PianoData;
import jw.piano.core.repositories.PianoDataRepository;
import jw.piano.core.services.PianoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PianoServiceTest {

    private PianoService sut;
    private PluginConfig pluginConfigMock;
    private PianoDataRepository pianoDataRepositoryMock;


    private ArmorStandMock armorStandMock;


    @Before
    public void setup() {
        FluentApiMock.getInstance();

        pluginConfigMock = new PluginConfig();
        pianoDataRepositoryMock = mock(PianoDataRepository.class);
      //  armorStandFactoryMock = mock(ArmorStandFactory.class);

        sut = new PianoService(pluginConfigMock, pianoDataRepositoryMock);

        armorStandMock = mock(ArmorStandMock.class);
     //   when(armorStandFactoryMock.create(any(),any())).thenReturn(armorStandMock);

    }

    @Test
    public  void canCreate_success() {
        //Arrange
        pluginConfigMock.getPianoConfig().setPianoInstancesLimit(10);

        //Act
        var result = sut.canCreate();

        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void canCreate_fail() {
        //Arrange
        pluginConfigMock.getPianoConfig().setPianoInstancesLimit(0);

        //Act
        var result = sut.canCreate();

        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void getNearestPiano() {
    }

    @Test
    public void create_success() {
        var location = FluentApiMock.getInstance().getPlayer().getLocation();
        var pianoData = new PianoData();
        pianoData.setLocation(location);
        when(pianoDataRepositoryMock.insert(any())).thenReturn(true);

        var result = sut.create(pianoData);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void create_failed_when_piano_is_inserted()  {
        var location = FluentApiMock.getInstance().getPlayer().getLocation();
        var pianoData = new PianoData();
        pianoData.setLocation(location);
        when(pianoDataRepositoryMock.insert(any())).thenReturn(false);

        var result = sut.create(pianoData);

        Assertions.assertTrue(result.isEmpty());
    }
    @Test
    public void initialize_success() {
    }

    @Test
    public void delete_success() {
    }
}