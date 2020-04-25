package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Vector;

import main.java.creation.Base;
import main.java.creation.Character;
import main.java.creation.World;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * WorldTest runs Unit Test on the world class. CODE COVERAGE: test.java
 * achieves 91.1% code coverage for the enter project.
 * 
 * @author Chris Kurian
 * @version 3.0
 *
 */
public class WorldTest {
    private World aWorld;
    private Base base1;
    private Base base2;
    private Character ch1;
    private Character ch2;
    private Character ch3;
    private Character ch4;
    Vector<Base> baseVector1;

    /**
     * Method to set up before each test.
     */
    @Before
    public void setUp() {
        ch1 = new Character.Builder().isHero(true)
                .constructCharacter();
        ch2 = new Character.Builder().isHero(true).setLevel(2)
                .constructCharacter();
        ch3 = new Character.Builder().isHero(false)
                .constructCharacter();
        ch4 = new Character.Builder().isHero(false)
                .constructCharacter();

        baseVector1 = new Vector<Base>();

        base1 = new Base.Builder().setName("Base1").setState("Base")
                .addMemeber(ch1).addMemeber(ch2)
                .baseConstruct();
        base2 = new Base.Builder().setName("Base2").setState("Lair")
                .addMemeber(ch3).addMemeber(ch4)
                .baseConstruct();

        baseVector1.add(base1);

        aWorld = new World.Builder().setName("World1")
                .setbaseVector(baseVector1).addBase(base2)
                .worldConstruct();
    }

    /**
     * Method to tear down after each test.
     */
    @After
    public void tearDown() {
        ch1 = null;
        ch2 = null;
        baseVector1 = null;
        base1 = null;
        base2 = null;
        aWorld = null;
    }

    /**
     * Test default constructor.
     */
    @Test
    public void testDefaultBuilder() {
        World tmp = new World.Builder()
                .worldConstruct();
        tmp.setName("tmp");
        baseVector1 = tmp.getBaseVector();
        tmp.print("");
        assertEquals("tmp", tmp.getName());
        assertEquals("Base 1", tmp.getBase(1).getName());
        assertEquals(4, tmp.gettotalOpen());
        assertEquals("At Risk", tmp.getState());
        assertEquals(4, baseVector1.size());
    }

    /**
     * Test getBase by index and name.
     */
    @Test
    public void testGetBase() {
        Base b1 = null;
        try {
            b1 = aWorld.getBase(0);
            fail("Tried to get base from an out of bounds index"
                    + ", should have thrown an Exception");
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            b1 = aWorld.getBase("BLAH");
            fail("No base witht he name BLAH so"
                    + " should have thrown an Exception");
        } catch (Exception e) {
            // Empty
        }

        Base b2 = null;
        b1 = aWorld.getBase(1);
        b2 = aWorld.getBase("Base2");

        assertEquals("Base1", b1.getName());
        assertEquals("Base2", b2.getName());

    }

    /**
     * Test base Vectors and base names.
     */
    @Test
    public void testSetBaseVector() {
        World tmp = new World.Builder()
                .worldConstruct();
        // Set baseVector with baseVector of size <4 should
        // catch exception
        try {
            tmp.setbaseVector(baseVector1);
            fail("Tried to set a base with size != 4, "
                    + "should have caught an exception");
        } catch (Exception e) {
            // TODO: handle exception
        }
        // Set baseVector the correct way with aWorlds baseVector
        baseVector1 = aWorld.getBaseVector();
        tmp.setbaseVector(baseVector1);

        // Test getBaseNames
        Vector<String> names = tmp.getBaseNames();

        assertEquals(2, tmp.gettotalOpen());
        assertEquals("Base1", names.get(0));

    }

}
