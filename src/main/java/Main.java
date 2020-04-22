package main.java;

import java.util.Vector;

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
        
        Character ch = new Character.Builder().isHero(false).constructCharacter();
        ch.print();
        
        Character ch2 = new Character.Builder().isHero(false).setLevel(4).constructCharacter();
        ch2.print();
        
        Character ch3 = new Character.Builder().isHero(false).constructCharacter();
        ch3.print();
        
        Vector<Character> heroVector = new Vector<Character>(5);
        heroVector.add(ch);
        heroVector.add(ch2);
        //System.out.println("+++++++++++++++++HeroVector" +  heroVector);
        //System.out.println(characters.size());
        String name = "Test Base";
        Base base = new Base.Builder().setName(name).setState("Lair").setMemberVector(heroVector).addMemeber(ch3).baseConstruct();
        
       // base.print();
//        Character strong = base.getStrongestMember();
          Character weak = base.getWeakestMember();
//          weak.setHealth(0);
//        base.removeMember(weak.getName(), weak.getPower());
//        base.removeMember(strong.getName(), strong.getPower());
//        System.out.println("fn\nAfter Remove");
//        base.print();
       
//        try {
//            base.addMember(ch3);
//            System.out.println("\n\nAfter Add");
//            base.print();
//        }catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
        
//         base.removeDead();
//         
//         base.addMember(ch3);
//         base.removeDead();
          
//         base.removeMember(ch.getName(), ch.getPower());
//         base.removeMember(ch2.getName(), ch2.getPower());
//         base.setName("Changed name");
//         base.setState("Base");
//         base.print();
//         base.setMembers(heroVector);
//         System.out.println("------------Renovated");
//         ch3.setHealth(0);
         System.out.println("\n\n");
         //base.print();
         //System.out.println(heroVector);
         World aWorld = new World.Builder().setName("MyWorld1")
                 .addBase(base).worldConstruct();
         
         aWorld.print();

        

    }
    
    

}
