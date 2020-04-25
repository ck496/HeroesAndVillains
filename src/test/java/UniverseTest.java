package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Vector;

import main.java.creation.Base;
import main.java.creation.Character;
import main.java.creation.Universe;
import main.java.creation.World;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * UniverseTest runs Unit Test on the Universe. CODE COVERAGE: test.java
 * achieves 91.1% code coverage for the enter project.
 * 
 * @author Chris Kurian
 * @version 3.0
 *
 */
public class UniverseTest {
    private Universe aUniv;
    private World aWorld;
    private World aWorld2;
    private World aWorld3;
    private Base base1;
    private Base base2;
    private Character ch1;
    private Character ch2;
    private Character ch3;
    private Character ch4;
    Vector<Base> baseVector1;
    HashMap<String, World> worldMap = new HashMap<String, World>();

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

        aWorld2 = new World.Builder().setName("World2")
                .setbaseVector(baseVector1).addBase(base2)
                .worldConstruct();

        aWorld3 = new World.Builder().setName("World3")
                .setbaseVector(baseVector1).addBase(base2)
                .worldConstruct();

        worldMap.put(aWorld.getName(), aWorld);

        aUniv = new Universe.Builder().setName("aUniv")
                .setWorldMap(worldMap).addWorld(aWorld2)
                .universeConstruct();

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
        aWorld2 = null;
        aWorld3 = null;
        worldMap.clear();
        aUniv = null;
    }

    /**
     * Create Univ using default constructor and add worlds.
     */
    @Test
    public void testDefaultConstruction() {
        Universe tmp = new Universe.Builder().setName("tmp")
                .universeConstruct();
        try {
            World tmpWorld = tmp.getWorld("World1");
            fail("Cant get world from empty unv,"
                    + "should have thrown an exception");
        } catch (Exception e) {
        }
        assertEquals("tmp", tmp.getName());
        assertEquals(0, tmp.getWorldMap().size());
        assertEquals(false, tmp.isWorldInUniverse("World1"));
    }

    /**
     * Test Adding of worlds.
     */
    @Test
    public void testAddWorlds() {
        // Test if you can add a duplicate
        aUniv.addWorld(aWorld);
        assertEquals(2, aUniv.getWorldMap().size());

        // Test if you can add normal
        aUniv.addWorld(aWorld3);
        assertEquals(3, aUniv.getWorldMap().size());

        World aWorld4 = new World.Builder().setName("World4")
                .setbaseVector(baseVector1).addBase(base2)
                .worldConstruct();
        World aWorld5 = new World.Builder().setName("World5")
                .setbaseVector(baseVector1).addBase(base2)
                .worldConstruct();
        aUniv.addWorld(aWorld4);
        aUniv.addWorld(aWorld5);
        // Try to add a 6th world, should throw Excep.
        World aWorld6 = new World.Builder().setName("World6")
                .setbaseVector(baseVector1).addBase(base2)
                .worldConstruct();
        try {
            aUniv.addWorld(aWorld6);
            fail("Cant add more than 5 worlds to a Universe, "
                    + "shouldve thrown an exception");
        } catch (Exception e) {
            // EMPTY
        }
    }

    /**
     * Test removing of worlds.
     */
    @Test
    public void testRemoveWorlds() {
        aUniv.removeWorld(aWorld.getName());
        assertEquals(1, aUniv.getWorldMap().size());
        try {
            aUniv.removeWorld("World 2");
            fail("No wolrd in univ. named 'World 2' "
                    + "(its 'World2'), "
                    + "shouldve thrown an exception");
        } catch (Exception e) {
            // EMPTY
        }
    }

    /**
     * Test getting of worlds.
     */
    @Test
    public void testGetWorlds() {
        World tmp = aUniv.getWorld(aWorld.getName());
        Iterable<String> names = aUniv.getWorldNames();
        aUniv.print();
        assertEquals(tmp.getName(), aWorld.getName());

    }

}
