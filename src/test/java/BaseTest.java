package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Vector;

import main.java.creation.Base;
import main.java.creation.Character;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * BaseTest runs Unit Test on the Base class. CODE COVERAGE: test.java achieves
 * 91.1% code coverage for the enter project.
 * 
 * @author Chris Kurian
 * @version 3.0
 *
 */
public class BaseTest {
    private Base base1;
    private Character ch1;
    private Character ch2;
    private Character ch3;
    private Character ch4;
    private Character ch5;
    private Character deadCharacter;
    private Character oppCharacter;
    Vector<Character> members;

    /**
     * Method to set up before each test.
     */
    @Before
    public void setUp() {
        ch1 = new Character.Builder().isHero(true)
                .constructCharacter();
        ch2 = new Character.Builder().isHero(true).setLevel(2)
                .constructCharacter();
        ch3 = new Character.Builder().isHero(true)
                .constructCharacter();
        ch4 = new Character.Builder().isHero(true)
                .constructCharacter();
        ch5 = new Character.Builder().isHero(true)
                .constructCharacter();
        deadCharacter = new Character.Builder().isHero(true)
                .setState("Dead")
                .constructCharacter();
        oppCharacter = new Character.Builder().isHero(false)
                .constructCharacter();

        members = new Vector<Character>();

        base1 = new Base.Builder().setName("Base1").setState("Base")
                .addMemeber(ch1).addMemeber(ch2)
                .baseConstruct();
    }

    /**
     * Test the default builder pattern without setting attributes.
     */
    @After
    public void tearDown() {
        ch1 = null;
        ch2 = null;
        deadCharacter = null;
        oppCharacter = null;
        members = null;
        base1 = null;
    }

    /**
     * Test the default builder pattern without setting attributes.
     */
    @Test
    public void builderTestDefault() {
        base1 = new Base.Builder().setName("Base1")
                .baseConstruct();

        assertEquals(0, base1.getTotalMembers());
        assertEquals("Open", base1.getState());
        assertEquals("Work", base1.getActionState());
        assertEquals("Base1", base1.getName());
        assertEquals(0, base1.getPoints());
    }

    /**
     * Test Custom builder with attributes for power etc.
     * 
     * @throws Exception
     */
    @Test
    public void builderTestCustom() throws Exception {
        Vector<Character> members = new Vector<Character>();
        members.add(ch1);
        members.add(ch2);
        members.add(ch3);
        members.add(ch4);
        base1 = new Base.Builder().setName("Base1").setMemberVector(members)
                .setState("Base")
                .baseConstruct();
        base1.setActionState("Fight");
        base1.setState("Open");
        base1.setPoints(50);
        base1.addMember(deadCharacter);
        base1.removeDead();

        assertEquals(members.size(), base1.getMembers().size());
        assertEquals("Fight", base1.getActionState());
        assertEquals(4, base1.getTotalMembers());

    }

    /**
     * Test to see if you can set the hero/villain vector.
     */
    @Test
    public void testSetMemebrs() {
        members = base1.getMembers();
        Base tmp = new Base.Builder().setName("Tmp")
                .baseConstruct();
        tmp.setMembers(members);
        tmp.print("");
        assertEquals(base1.getTotalMembers(), tmp.getTotalMembers());
    }

    /**
     * Test remove and removeDead method on empty base, base with dead members and
     * base with the correct amount of members.
     */
    @Test
    public void testRemove() {
        try {
            base1.removeMember(ch1.getName(), ch1.getPower());
        } catch (Exception e) {
            fail("Should not have caught eception while removing");
            System.out.println(e.getMessage());
        }
        try {
            base1.removeMember(ch1.getName(), ch1.getPower());
            fail("Should have caught a NoSuchElement Exception, "
                    + "since ch1 is no longer in base");
        } catch (Exception e) {
            // EMPTY
        }

        try {
            base1.removeMember(ch2.getName(), ch2.getPower());
        } catch (Exception e) {
            fail("Should not have caught an Exception "
                    + "since ch2 is still in base");
        }
        // try to remove from an empty base
        try {
            base1.removeMember(ch2.getName(), ch2.getPower());
            fail("Should have caught a ArithmeticException Exception, "
                    + "while trying to remove  from an empty base");
        } catch (Exception e) {
            // EMPTY
        }

        // remove dead on an empty base
        try {
            base1.removeDead();
            fail("Should have caught a ArithmeticException Exception, "
                    + "while trying to remove dead from an empty base");
        } catch (Exception e) {
            // EMPTY
        }

        // add dead member to see if remove dead can remove the dead member
        base1.addMember(deadCharacter);
        try {
            base1.removeDead();

        } catch (Exception e) {
            fail("Should not have caught an Exception, "
                    + "while trying to remove dead from the base with a dead");
        }

        assertEquals("Open", base1.getState());
    }

    /**
     * TestTo see if split function splits the members and gives a resulting vector
     * of members.
     */
    @Test
    public void testSplitMemebrs() {

        try {
            members = base1.getSplitMembers();
            fail("Not allowed to split a non full base");
        } catch (Exception e) {
            // EMPTY
        }
        try {
            base1.addMember(oppCharacter);
            fail("Cant add a villain to a hero base");
        } catch (Exception e) {
            // EMPTY

        }
        base1.addMember(ch3);
        base1.addMember(ch4);
        base1.addMember(deadCharacter);
        try {
            members = base1.getSplitMembers();
            fail("Shouldnt split base with dead members");
        } catch (Exception e) {
            // TODO: handle exception
        }
        base1.addMember(ch5);
        try {
            members = base1.getSplitMembers();
        } catch (Exception e) {
            fail("Should have had no problem splitting 5 member"
                    + "Base, shoudlnt have caught exception");
        }

        assertEquals(3, members.size());
    }

    /**
     * Test the getMemberNames function.
     */
    @Test
    public void testGetMemberNames() {
        Vector<String> names = base1.getMemberNames();
        assertEquals(ch1.getName(), names.get(0));
        assertEquals(ch2.getName(), names.get(1));
        assertEquals(2, names.size());

        // get member names of an empty base, should throw an exception
        Base tmp = new Base.Builder()
                .baseConstruct();
        try {
            names = tmp.getMemberNames();
            fail("Should have caught an exception while "
                    + "trying to get memeber names of an empty base");
        } catch (Exception e) {
            // EMPTY
        }
    }

    /**
     * Test getStrongest and weakest.
     */
    @Test
    public void getDifferentStrengths() {
        Base tmp = new Base.Builder()
                .baseConstruct();
        Character strongest = null;
        Character weakest = null;
        // Trying to get strongest and weakest from an
        // empty base should throw an exception.
        try {
            strongest = tmp.getStrongestMember();
            fail("Should have caught an exception while "
                    + "trying to get strongest from an empty base");
        } catch (Exception e) {
            // EMPTY
        }
        try {
            weakest = tmp.getWeakestMember();
            fail("Should have caught an exception while "
                    + "trying to get weakest from an empty base");
        } catch (Exception e) {
            // EMPTY
        }

        // Get strongest and weakest from base1
        try {
            strongest = base1.getStrongestMember();
        } catch (Exception e) {
            fail("Should not have caught an exception while "
                    + "trying to get strongest from base1");
        }
        try {
            weakest = base1.getWeakestMember();
        } catch (Exception e) {
            fail("Should not have caught an exception while "
                    + "trying to get weakest from base1");
        }

        assertEquals(ch2.getName(), strongest.getName());
        assertEquals(ch1.getName(), weakest.getName());

    }

}
