package main.java.console;

import main.java.creation.*;
import main.java.creation.Character;
import main.java.behavior.*;
import java.util.Vector;

/**
 * Main Class of the HeroesAndVillans project.
 * 
 * @author chris
 *
 */

public class Main {
    /**
     * Main function.
     * 
     * @param args String
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Heros and Villans Universe! "
                + "\n\nWhich side will you choose?? \nWho will emerge "
                + "victorious? \nWhat will be the fate of the universe?"
                + "\n\nFind out soon..........");

        Character ch = new Character.Builder().isHero(false).constructCharacter();
        Character ch2 = new Character.Builder().isHero(false).setLevel(4).constructCharacter();
        Character ch3 = new Character.Builder().isHero(false).constructCharacter();
        Character ch4 = new Character.Builder().isHero(false).constructCharacter();
        Character ch5 = new Character.Builder().isHero(false).constructCharacter();

        Vector<Character> villainVector = new Vector<Character>(5);
        villainVector.add(ch);
        villainVector.add(ch2);
        villainVector.add(ch3);
        villainVector.add(ch4);
        villainVector.add(ch5);

        // BADGUYS
        String name = "Thanos";
        Base thanos = new Base.Builder().setName(name).setState("Lair")
                .setMemberVector(villainVector)
                .baseConstruct();

        // GOODGUYS
        Character chg = new Character.Builder().isHero(true).constructCharacter();
        // ch.print();
        Character chg2 = new Character.Builder().isHero(true).setLevel(4).constructCharacter();
        // ch2.print();
        Character chg3 = new Character.Builder().isHero(true).constructCharacter();
        Character chg4 = new Character.Builder().isHero(true).constructCharacter();
        Character chg5 = new Character.Builder().isHero(true).constructCharacter();

        // ch3.print();

        Vector<Character> heroVector = new Vector<Character>(5);
        heroVector.add(chg);
        heroVector.add(chg2);
        heroVector.add(chg3);
        heroVector.add(chg4);
        heroVector.add(chg5);

        String name2 = "Avengers";
        Base avengers = new Base.Builder().setName(name2).setState("Base")
                .setMemberVector(heroVector)
                .baseConstruct();

        Base alpha = new Base.Builder().setName("Alpha")
                .baseConstruct();

        Base omega = new Base.Builder().setName("Omega")
                .baseConstruct();

        Vector<Base> worldBase0 = new Vector<Base>();
        worldBase0.add(thanos);
        worldBase0.add(avengers);
        worldBase0.add(alpha);
        worldBase0.add(omega);

        // Create World
        World aWorld = new World.Builder().setName("Nebula")
                .setbaseVector(worldBase0).worldConstruct();

        aWorld.print("");
//        Universe aUniverse = new Universe.Builder().setUniverseName("Gramada")
//                .addWorld(aWorld).constrUniverse();
//        
//        aUniverse.print();

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

        try {
            workChain.doWork(aWorld);
        } catch (Exception e) {
            System.out.println("Exception In Main: \n" + e.getMessage());
        }

    }

}
