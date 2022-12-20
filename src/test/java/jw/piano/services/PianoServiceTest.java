package jw.piano.services;

import be.seeseemelk.mockbukkit.entity.ArmorStandMock;
import jw.fluent.api.utilites.SpigotMock;
import jw.piano.data.config.PluginConfig;
import jw.piano.data.models.PianoData;
import jw.piano.factory.ArmorStandFactory;
import jw.piano.repositories.PianoDataRepository;
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
    private ArmorStandFactory armorStandFactoryMock;

    private ArmorStandMock armorStandMock;


    @Before
    public void setup() {
        SpigotMock.getInstance();




        pluginConfigMock = new PluginConfig();
        pianoDataRepositoryMock = mock(PianoDataRepository.class);
        armorStandFactoryMock = mock(ArmorStandFactory.class);

        sut = new PianoService(pluginConfigMock, pianoDataRepositoryMock, armorStandFactoryMock);

        armorStandMock = mock(ArmorStandMock.class);
        when(armorStandFactoryMock.create(any(),any())).thenReturn(armorStandMock);




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
        var location = SpigotMock.getInstance().getPlayer().getLocation();
        var pianoData = new PianoData();
        pianoData.setLocation(location);
        when(pianoDataRepositoryMock.insert(any())).thenReturn(true);

        var result = sut.create(pianoData);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void create_failed_when_piano_is_inserted()  {
        var location = SpigotMock.getInstance().getPlayer().getLocation();
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