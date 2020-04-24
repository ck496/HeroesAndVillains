package test.java;

import main.java.creation.Character;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

class ChatacterTest {

    private Character ch1;

    @Before
    public void setUp() {
        ch1 = new Character.Builder().setName("TestCh1").setLevel(0)
                .constructCharacter();
    }

    @Test
    void getName() {
        String name = "TestCh1";
        assertEquals(name, ch1.getName());
    }

}