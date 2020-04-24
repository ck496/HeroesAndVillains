package test.java;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import main.java.creation.Character;

public class TestCharacter {
    private Character ch1;

    @Before
    public void setUp() {
        ch1 = new Character.Builder().isHero(false)
                .setName("TestCh1").setLevel(0)
                .constructCharacter();
    }

    /**
     * Test the default builder pattern without setting attributes
     */
    @Test
    public void builderTestDefault() {
        Character tmp = new Character.Builder().isHero(true)
                .constructCharacter();
        String name = tmp.getName();
        String power = tmp.getPower();
        assertEquals(true, tmp.isHero());
        assertEquals("Protect", tmp.getState());
        assertEquals(1, tmp.getLevel());
        assertEquals(100, tmp.getHealth());
    }

    /**
     * Test Custom builder with attributes for power etc
     */
    @Test
    public void builderTestCustom() {
        Character tmp = new Character.Builder().isHero(false)
                .setLevel(3).setPower("Fighting").setState("Fight")
                .constructCharacter();
        tmp.print("");
        tmp.setHealth(50);
        tmp.setLevel(1);
        tmp.setState("Work");

        assertEquals("Dark", tmp.getStrength());
        assertEquals("Psychic", tmp.getWeakness());
        assertEquals(1, tmp.getLevel());
        assertEquals(50, tmp.getHealth());
        assertEquals("Work", tmp.getState());

    }

    /**
     * Check to see if health = 0 then state = dead
     */
    @Test
    public void testDead() {
        ch1.setHealth(0);
        assertEquals("Dead", ch1.getState());
    }

}
