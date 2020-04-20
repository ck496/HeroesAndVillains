package main.java;

/**
 * Main Class of the HeroesAndVillans project.
 * @author chris
 *
 */

public class Main {
    /**
     * Main function.
     * @param args String
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Heros and Villans Universe! "
                + "\n\nWhich side will you choose?? \nWho will emerge "
                + "victorious? \nWhat will be the fate of the universe?"
                + "\n\nFind out soon..........");
        
        Character ch = new Character.Builder().isHero(true).constructCharacter();
        ch.print();
        
        Character ch2 = new Character.Builder().isHero(false).constructCharacter();
        ch2.print();
        

    }
    
    

}
