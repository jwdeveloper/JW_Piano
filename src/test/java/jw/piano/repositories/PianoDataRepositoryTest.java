package jw.piano.repositories;

import jw.fluent.api.utilites.SpigotMock;
import jw.piano.api.data.models.PianoData;
import jw.piano.core.repositories.PianoDataRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.UUID;

public class PianoDataRepositoryTest {
    private static PianoDataRepository sut;

    @Before
    public  void setup()
    {
        SpigotMock.getInstance();
        sut = new PianoDataRepository();
    }

    @Test
    public void delete_success()
    {
        //Arrange
        var data = new PianoData();
        sut.insert(data);

        //Act
        var result = sut.delete(data.getUuid());

        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void delete_fail()
    {
        //Arrange
        var uuid = UUID.randomUUID();
        //Act
        var result = sut.delete(uuid);

        //Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void insert_success()
    {
        //Arrange
        var data = new PianoData();


        //Act
        var result = sut.insert(data);;

        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void insert_fail()
    {
        //Arrange
        var data = new PianoData();

        //Act
        var result = sut.insert(data);;
        var result2 = sut.insert(data);;

        //Assert
        Assertions.assertTrue(result);
        Assertions.assertFalse(result2);
    }
}