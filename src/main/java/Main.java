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
        ch3.setState("Dead");
        ch3.print();
        
        Vector<Character> heroVector = new Vector<Character>(5);
        heroVector.add(ch);
        heroVector.add(ch2);
        System.out.println("+++++++++++++++++HeroVector" +  heroVector);
        //System.out.println(characters.size());
        String name = "Test Base";
        Base base = new Base.Builder().setName(name).setState("Lair").setMemberVector(heroVector).baseConstruct();
        
       // base.print();
//        Character strong = base.getStrongestMember();
//        Character weak = base.getWeakestMember();
//        base.removeMember(weak.getName(), weak.getPower());
//        base.removeMember(strong.getName(), strong.getPower());
//        System.out.println("\n\nAfter Remove");
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
         
         ch.setState("Dead");
         ch.setHealth(0);
         ch.setLevel(0);
         base.print();
         //System.out.println(heroVector);
      

        

    }
    
    

}
