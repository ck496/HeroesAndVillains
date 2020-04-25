package test.java;

import static org.junit.Assert.fail;

import main.java.behavior.Chain;
import main.java.behavior.CreateChain;
import main.java.behavior.ExitChain;
import main.java.behavior.FightChain;
import main.java.behavior.RecoverChain;
import main.java.behavior.WorkChain;

import main.java.creation.Base;
import main.java.creation.Character;
import main.java.creation.World;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * BehaviorTest runs Unit Test on the Behavior classes. CODE COVERAGE: test.java
 * achieves 91.1% code coverage for the enter project.
 * 
 * @author Chris Kurian
 * @version 3.0
 *
 */
public class BehaviorTest {
    private World aWorld;
    private Base base1;
    private Base base2;
    private Character ch1;
    private Character ch2;
    private Character ch3;
    private Character ch4;

    /**
     * Method to set up before each test.
     */
    @Before
    public void setUp() {
        ch1 = new Character.Builder().isHero(true)
                .constructCharacter();
        ch2 = new Character.Builder().isHero(true)
                .constructCharacter();
        ch3 = new Character.Builder().isHero(false)
                .constructCharacter();
        ch4 = new Character.Builder().isHero(false)
                .constructCharacter();

        base1 = new Base.Builder().setName("Alpha").setState("Base")
                .addMemeber(ch1).addMemeber(ch2)
                .baseConstruct();
        base2 = new Base.Builder().setName("Omega").setState("Lair")
                .addMemeber(ch3).addMemeber(ch4)
                .baseConstruct();

        aWorld = new World.Builder().setName("Earth")
                .addBase(base1).addBase(base2)
                .worldConstruct();

    }

    /**
     * Test the default builder pattern without setting attributes.
     */
    @After
    public void tearDown() {
        ch1 = null;
        ch2 = null;

        base1 = null;
        base2 = null;
        aWorld = null;
    }

    /**
     * Test WorkChain behavior to see if it successfully passes the responsibility
     * to whoever is next in line.
     */
    @Test
    public void workChain() {
        ch3 = new Character.Builder().isHero(true)
                .constructCharacter();
        ch4 = new Character.Builder().isHero(true)
                .constructCharacter();
        base2 = new Base.Builder().setName("Omega").setState("Base")
                .addMemeber(ch3).addMemeber(ch4)
                .baseConstruct();
        aWorld = new World.Builder().setName("Earth")
                .addBase(base1).addBase(base2)
                .worldConstruct();

        Chain workChain = new WorkChain();
        Chain exitChain = new ExitChain();
        workChain.setNextChain(exitChain);
        exitChain.setNextChain(null);
        // Chain Should throw exception since there isn't a member
        // in all bases, therefore cant exit and exits nextChain = null
        try {
            workChain.doWork(aWorld);
            fail("Fail: chain should have thrown an exception "
                    + "while passing to next chain in exitChain");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Add a like member to each base now chain should exit
        // without Excep.
        aWorld.getBase(3).addMember(ch1);
        aWorld.getBase(4).addMember(ch2);
        try {
            workChain.doWork(aWorld);
        } catch (Exception e) {
            fail("Fail: Chain should not have thrown an exception "
                    + "while passing responsibility to next chain");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Run the full chain of responsibilities and see if any exceptions are caught.
     * Check to see if the state of the world is not 'At Risk' when the world exits
     * out of the chain of responsibilities.
     */
    @Test
    public void fullChainRun() {

        Chain workChain = new WorkChain();
        Chain createChain = new CreateChain();
        Chain fightChain = new FightChain();
        Chain recoverChain = new RecoverChain();
        Chain exitChain = new ExitChain();
        workChain.setNextChain(createChain);
        createChain.setNextChain(fightChain);
        fightChain.setNextChain(recoverChain);
        recoverChain.setNextChain(exitChain);
        exitChain.setNextChain(workChain);

        base1.setActionState("Create");

        // Should loop around until there is a hero in
        // each base and then exit & make change the state of the
        // world to "Secured"
        try {
            workChain.doWork(aWorld);
        } catch (Exception e) {
            fail("Fail: Chain should not have thrown an exception "
                    + "while passing responsibility to next ");
            System.out.println(e.getMessage());
        }

        // When exits the world should not be 'At Risk'
        // Secured or fallen
        if (aWorld.getState().equals("At Risk")) {
            fail("Fail: When exitting the chain of responsibility the "
                    + "state of the world must not be 'At Risk'");
        }
    }
}
