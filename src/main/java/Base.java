package main.java;

import java.util.NoSuchElementException;
import java.util.Vector;


/**
 * Base class creates the bases for the villians and heros within the 
 * words. There can be a total of 5  m 
 * @author chris
 *
 */
public class Base {

    public String[] stateList = { "Lair", "Base", "Open" , "Closed"};
    private Vector<Character> members = new Vector<Character>(5);

    private String name;
    private String state;
    private int totalMembers;

    public Base(Builder builder) {
        this.members = builder.members;
        this.name = builder.name;
        this.totalMembers = builder.totalMembers;
        this.setState(builder.state);
    }

    /**
     * @return the members
     */
    public Vector<Character> getMembers() {
        return members;
    }

    /**
     * @param members the members to set
     */
    public void setMembers(Vector<Character> chArr) {
        for(Character ch : chArr) {
            Character newCharacter = new Character.Builder().isHero(ch.isHero())
                                   .setName(ch.getName()).setState(ch.getState())
                                   .setLevel(ch.getLevel()).setPower(ch.getPower())
                                   .constructCharacter();
            this.members.add(newCharacter);
        }

        
        this.totalMembers = members.size();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the charecterNum
     */
    public int getTotalMembers() {
        return totalMembers;
    }
    

    /**
     * Add Characters to vector.
     * @param ch
     * @return Builder
     * @throws IllegalAccessException 
     */
    public void addMember(Character ch) {
        
        if(totalMembers >= 5) {
            throw new IndexOutOfBoundsException("Base is at Max Capacity (5) right now, "
                    + "cant add new members");
        }
        
        if(ch.isHero() && state.equals("Lair")) {
            throw new IllegalArgumentException("Hero cant be added to a Villians Lair");
        }
        else if(!ch.isHero() && state.equals("Base")) {
            throw new IllegalArgumentException("Villian cant be added to a Heros Base");
        }
        
        Character newCharacter = new Character.Builder().isHero(ch.isHero())
                .setName(ch.getName()).setState(ch.getState())
                .setLevel(ch.getLevel()).setPower(ch.getPower())
                .constructCharacter();
        this.members.add(newCharacter);
        this.members.add(ch);
        this.totalMembers++;
       
    }
    
    /**
     * Function used to split up base as members are greater than 5
     * @return
     * @throws Exception
     */
    public Vector<Character> getSplitMembers() throws Exception{
        removeDead();
        if(totalMembers < 5) {
            throw new Exception("Exception: Cant split, not full yet");
        }
        
        Vector<Character> splitMembers = new Vector<Character>();
        for(int i=0; i<3; i++) {
            Character splitCharacter = new Character.Builder().isHero(members.elementAt(0).isHero())
                    .setName(members.elementAt(0).getName()).setState(members.elementAt(0).getState())
                    .setLevel(members.elementAt(0).getLevel()).setPower(members.elementAt(0).getPower())
                    .constructCharacter();
            splitMembers.add(splitCharacter);
            members.remove(0);
            totalMembers--;
        }
        return splitMembers;
        }
    
    
    /**
     * Remove Dead Characters from base.
     * @throws ArithmeticException 
     */
    public void removeDead() {
        if(totalMembers == 0) {
            throw new ArithmeticException("Base is empty, no one to remove!");
        }
        boolean hasDead = false;
        Vector<Character> tmp = (Vector<Character>) members.clone();
        for(Character ch : tmp) {
            if(ch.getState().equals("Dead")) {
                members.remove(ch);
                totalMembers--;
                hasDead = true;
                System.out.println("REMOVED DEAD:  " +ch.getName() + " from " + this.name);
                //ch.print();
            }
        }
        if(hasDead) {
            print();
        }
        else {
            System.out.println("No Dead members in " + this.name );
        }
        
    }
    
    /**
     * 
     * Remove Member given a name and their power.
     * @param name
     * @param power
     */
    public void removeMember(String name, String power) {
        if(totalMembers == 0) {
            throw new ArithmeticException("Base is empty, no one to remove!");
        }
        boolean inBase = false;
        for(Character ch : members) {
            if(ch.getName().equals(name) && ch.getPower().equals(power)) {
                members.remove(ch);
                totalMembers--;
                System.out.println("REMOVED:  " +ch.getName() + " from " + this.name);
                ch.print();
                inBase = true;
                break;
                
            }
        }
        
        if(!inBase) {
            throw new NoSuchElementException("No member with the name '"+ name 
                    + "' and power: '" + power + "' exists in " + this.name);
        }
    }
    
    
    
    
    /**
     * Gets the strongest. Returns a reference to 
     * the actual member not a copy. So outside 
     * change affects inside member
     * @return strongest
     */
    public Character getStrongestMember() {
        if(totalMembers == 0) {
            throw new ArithmeticException("Base is empty, no one to remove!");
        }
        Character strongest = members.get(0);
        
        for(Character ch : members) {
            if(ch.getLevel() > strongest.getLevel()) {
                strongest = ch;
            }
        } 
        
        System.out.println(this.name +" STRONGEST Member: " + strongest.getName() 
                        + ", lvl: " + strongest.getLevel());

        return strongest;
    }
    
    /**
     *Gets the Weakest. Returns a reference to 
     * the actual member not a copy. So outside 
     * change affects inside member
     * @return Weakest
     */
    public Character getWeakestMember() {
        if(totalMembers == 0) {
            throw new ArithmeticException("Base is empty, no one to remove!");
        }
        Character weakest = members.get(0);
        
        for(Character ch : members) {
            if(ch.getLevel() < weakest.getLevel()) {
                weakest = ch;
            }
        }
        
        System.out.println(this.name +" WEAKEST Member: " + weakest.getName()
                        + ", lvl: " + weakest.getLevel());
        return weakest;
    }
    
    
    public void print() {
        System.out.println("---- Base Detials ----");
        System.out.println("\t[Name]: " + this.name + "\n\t[State]: " 
                        + this.state + "\n\t[Total Memebrs]: " + this.totalMembers 
                        + "\n\t[Memeber Detils]: ");
        if(totalMembers!=0) {
            int i=0;
            for(Character ch :members) {
                i++;
                System.out.println("\n         [MEMBER " +i +"]");
                ch.print();
            }
        }
        else {
            System.out.println("0 memebers");
        }
    }


    /**
     * Constructs the base objects using builder pattern
     */
    public static class Builder {
        private String name;
        private String state = "";
        private int totalMembers;
        Vector<Character> members = new Vector<Character>(5);

        /**
         * 
         * @param name
         * @return Builder
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        /**
         * sets the state;
         * @param state
         * @return
         */
        public Builder setState(String state) {
            this.state = state;
            return this;
        }

        /**
         * 
         * @param memberVector
         * @return Builder
         */
        public Builder setMemberVector(Vector<Character> chArr) {
            for(Character ch : chArr) {
                Character newCharacter = new Character.Builder().isHero(ch.isHero())
                                       .setName(ch.getName()).setState(ch.getState())
                                       .setLevel(ch.getLevel()).setPower(ch.getPower())
                                       .constructCharacter();
                members.add(newCharacter);
            }
            return this;
        }
        
        /**
         * Add Characters to vector.
         * @param ch
         * @return Builder
         */
        public Builder addMemeber(Character ch) {
            Character newCharacter = new Character.Builder().isHero(ch.isHero())
                    .setName(ch.getName()).setState(ch.getState())
                    .setLevel(ch.getLevel()).setPower(ch.getPower())
                    .constructCharacter();
            this.members.add(newCharacter);
            return this;
        }

        /**
         * Constructs the base objects using builder pattern
         * 
         * @return Base
         */
        public Base baseConstruct() {
            this.totalMembers = this.members.size();
            if(this.state.equals("")) {
                this.state = "Open";
            }
            return new Base(this);
        }

    }

}
