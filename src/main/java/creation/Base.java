package main.java.creation;

import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * Base class creates the bases for the villains and heros within the Worlds.
 * There can be a total of 5 members of the same type in a base. Base's State is
 * "Open" if it has no members, "Lair" if members are Villains and "Base" if
 * members are heros.
 * 
 * <p>
 * Base states: { "Lair", "Base", "Open" }; Base Actions: { "Work", "Create",
 * "Fight", "Recover" };
 * 
 * <p>
 * BUILDER DESIGN PATTERN is used to provide a better and more flexible approach
 * to object creation. Allows you to create objects with or without manual entry
 * of all the instance variables
 * 
 * @author Chris Kurian
 * @version 3.0
 *
 *
 */
public class Base {

    private String name;
    private String state;
    private String actionState;
    private int totalMembers;
    private int points;
    private Vector<Character> members = new Vector<Character>(5);

    /**
     * Construct Base using a Builder.
     * 
     * @param builder Builder
     */
    public Base(Builder builder) {
        this.members = builder.members;
        this.name = builder.name;
        this.totalMembers = builder.totalMembers;
        this.state = builder.state;
        this.actionState = builder.actionState;
        this.points = builder.points;
    }

    /**
     * Get Members of base.
     * 
     * @return the members
     */
    public Vector<Character> getMembers() {
        return members;
    }

    /**
     * Set Members of base.
     * 
     * @param chArr the members to set
     */
    public void setMembers(Vector<Character> chArr) {
        for (Character ch : chArr) {
            Character newCharacter = new Character.Builder().isHero(ch.isHero())
                    .setName(ch.getName()).setState(ch.getState())
                    .setLevel(ch.getLevel()).setPower(ch.getPower())
                    .constructCharacter();
            this.members.add(newCharacter);
        }

        this.totalMembers = members.size();
    }

    /**
     * Get name of base.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * set Name of base.
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get state of base.
     * 
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Set state of base.
     * 
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Get Action State of base.
     * 
     * @return the actionState
     */
    public String getActionState() {
        return actionState;
    }

    /**
     * Set Action state of base.
     * 
     * @param actionState the actionState to set
     */
    public void setActionState(String actionState) {
        this.actionState = actionState;
    }

    /**
     * Get points of base.
     * 
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Set Points of base.
     * 
     * @param points the points to set
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Get total member of the base.
     * 
     * @return the charecterNum
     */
    public int getTotalMembers() {
        return totalMembers;
    }

    /**
     * Add Characters to vector.
     * 
     * @param ch character
     * @throws IllegalAccessException if max capacity
     */
    public void addMember(Character ch) {

        if (totalMembers >= 5) {
            throw new IndexOutOfBoundsException("Base is at Max Capacity (5) right now, "
                    + "cant add new members");
        }

        if (ch.isHero() && state.equals("Lair")) {
            throw new IllegalArgumentException("Hero cant be added to a Villians Lair");
        } else if (!ch.isHero() && state.equals("Base")) {
            throw new IllegalArgumentException("Villian cant be added to a Heros Base");
        }

        Character newCharacter = new Character.Builder().isHero(ch.isHero())
                .setName(ch.getName()).setState(ch.getState())
                .setLevel(ch.getLevel()).setPower(ch.getPower())
                .constructCharacter();
        this.members.add(newCharacter);
        this.totalMembers++;
        // if state is open change the state based on the
        // first member that was added
        if (this.state.equals("Open")) {
            this.state = (ch.isHero() ? "Base" : "Lair");
        }

    }

    /**
     * Function used to split up base as members are greater than 5.
     * 
     * @return splitMembers with 3 members
     */
    public Vector<Character> getSplitMembers() throws Exception {
        removeDead();
        if (totalMembers < 5) {
            throw new Exception("Exception: Cant split, not full yet");
        }

        Vector<Character> splitMembers = new Vector<Character>();
        for (int i = 0; i < 3; i++) {
            Character splitCharacter = new Character.Builder()
                    .isHero(members.elementAt(0).isHero())
                    .setName(members.elementAt(0).getName())
                    .setState(members.elementAt(0).getState())
                    .setLevel(members.elementAt(0).getLevel())
                    .setPower(members.elementAt(0).getPower())
                    .constructCharacter();
            splitMembers.add(splitCharacter);
            members.remove(0);
            totalMembers--;
        }
        return splitMembers;
    }

    /**
     * Remove Dead Characters from base.
     * 
     * @throws ArithmeticException if empty
     */
    public void removeDead() {
        if (totalMembers == 0) {
            throw new ArithmeticException("Base is empty, no one to remove!");
        }
        boolean hasDead = false;
        Vector<Character> tmp = (Vector<Character>) members.clone();
        for (Character ch : tmp) {
            if (ch.getState().equals("Dead")) {
                members.remove(ch);
                totalMembers--;
                hasDead = true;
                System.out.println("REMOVED DEAD:  " + ch.getName() + " from " + this.name);
                ch.print("");
            }
        }

        if (this.totalMembers == 0) {
            this.state = "Open";
        }

    }

    /**
     * 
     * Remove Member given a name and their power.
     * 
     * @param name  to remove
     * @param power of member
     */
    public void removeMember(String name, String power) {
        if (totalMembers == 0) {
            throw new ArithmeticException("Base is empty, no one to remove!");
        }
        boolean inBase = false;
        for (Character ch : members) {
            if (ch.getName().equals(name) && ch.getPower().equals(power)) {
                members.remove(ch);
                totalMembers--;
                System.out.println("REMOVED:  " + ch.getName() + " from " + this.name);
                ch.print("");
                inBase = true;
                break;

            }
        }
        if (!inBase) {
            throw new NoSuchElementException("No member with the name '" + name
                    + "' and power: '" + power + "' exists in " + this.name);
        }
        if (this.totalMembers == 0) {
            this.state = "Open";
        }
    }

    /**
     * Gets the strongest. Returns a reference to the actual member not a copy. So
     * outside change affects inside member.
     * 
     * @return strongest member
     */
    public Character getStrongestMember() {
        if (totalMembers == 0) {
            throw new ArithmeticException("Base is empty, no Strongest member!");
        }
        Character strongest = members.get(0);

        for (Character ch : members) {
            if (ch.getLevel() > strongest.getLevel()) {
                strongest = ch;
            }
        }

        return strongest;
    }

    /**
     * Gets the Weakest. Returns a reference to the actual member not a copy. So
     * outside change affects inside member.
     * 
     * @return weakest member
     */
    public Character getWeakestMember() {
        if (totalMembers == 0) {
            throw new ArithmeticException("Base is empty, no weakestMember!");
        }
        Character weakest = members.get(0);

        for (Character ch : members) {
            if (ch.getLevel() < weakest.getLevel()) {
                weakest = ch;
            }
        }
        return weakest;
    }

    /**
     * Returns a vector of the member names.
     * 
     * @return memberNames name of memebers
     */
    public Vector<String> getMemberNames() {
        if (totalMembers == 0) {
            throw new NoSuchElementException("Exception: Cant get member names,"
                    + " 0 memebers in base");
        }
        Vector<String> memberNames = new Vector<String>();
        for (Character ch : members) {
            memberNames.add(ch.getName());
        }
        return memberNames;
    }

    /**
     * Print the state of the base.
     */
    public void print(String s) {
        System.out.println(s + "[Base Name]: " + this.name + "\n" + s + "[State]: "
                + this.state + "\n" + s + "[Action State]: "
                + this.actionState + "\n" + s + "[Total Memebrs]: " + this.totalMembers
                + "\n" + s + "--Memeber Detils-- ");
        try {
            for (Character ch : members) {
                ch.print("\t\t\t");
            }
        } catch (NoSuchElementException e) {
            System.out.println(s + "0 memebers in this base");
        }
        if (members.size() == 0) {
            System.out.println(s + "0 Members : Empty Base");
        }
    }

    /**
     * Constructs the base objects using builder pattern.
     */
    public static class Builder {
        private String name;
        private String state = "";
        private String actionState;
        private int totalMembers;
        private int points;
        Vector<Character> members = new Vector<Character>(5);

        /**
         * Set the name of the builder.
         * 
         * @param name to set
         * @return Builder this
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the state.
         * 
         * @param state to set
         * @return Builder this
         */
        public Builder setState(String state) {
            this.state = state;
            return this;
        }

        /**
         * Set the member Vector of the builder.
         * 
         * @param chArr to set
         * @return Builder this
         */
        public Builder setMemberVector(Vector<Character> chArr) {
            for (Character ch : chArr) {
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
         * 
         * @param ch Character
         * @return Builder this
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
         * Constructs the base objects using builder pattern.
         *
         * @return Base constructed
         */
        public Base baseConstruct() {
            this.totalMembers = this.members.size();
            if (this.state.equals("")) {
                this.state = "Open";
            }
            this.points = 0;
            this.actionState = "Work";
            return new Base(this);
        }

    }

}
