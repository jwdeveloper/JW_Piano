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

package jw.piano.repositories;

import jw.fluent.api.utilites.FluentApiMock;
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
        FluentApiMock.getInstance();
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