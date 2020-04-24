package main.java.creation;

import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * World class is equivalent to cities where heros and villains can have bases
 * and lairs of 5. Each world will have 4 bases that can be occupied by Villains
 * or heros. If ALL bases are of heros then the state of the world is
 * "Protected". If all bases are of Villains then the world has "Fallen", else
 * the world is "At Risk".
 * 
 * World States: { "Secured", "Fallen", "At Risk" }
 * 
 * BUILDER DESIGN PATTERN is used to provide a better and more flexible approach
 * to object creation. Allows you to create objects with or without manual entry
 * of all the instance variables
 * 
 * @author Chris Kurian
 * @version 3.0
 */
public class World {

    private String name, state;
    private int totalOpen;
    private Vector<Base> baseVector = new Vector<Base>(4);

    public World(Builder builder) {
        this.name = builder.name;
        this.setState(builder.state);
        this.totalOpen = builder.totalOpen;
        this.baseVector = builder.baseVector;
    }

    /**
     * 
     * @return name String.
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
     * @return the totalOpen
     */
    public int gettotalOpen() {
        int total = 0;
        for (Base b : baseVector) {
            if (b.getState() == "Open") {
                total++;
            }
        }
        this.totalOpen = total;
        return totalOpen;
    }

    /**
     * @return the baseVector
     */
    public Vector<Base> getBaseVector() {
        return baseVector;
    }

    /**
     * @param baseVector the baseVector to set
     */
    public void setbaseVector(Vector<Base> aBaseVector) {
        if (aBaseVector.size() != 4) {
            throw new IndexOutOfBoundsException("Exception in World "
                    + "setBaseVector(): IndexOutOFBounds");
        }
        this.baseVector.clear();
        for (Base b : aBaseVector) {
            Base newBase = new Base.Builder().setName(b.getName())
                    .setState(b.getState()).setMemberVector(b.getMembers())
                    .baseConstruct();
            this.baseVector.add(newBase);

        }
        this.totalOpen = gettotalOpen();
    }

    /**
     * Get vector of Member names;
     * 
     * @return
     */
    public Vector<String> getBaseNames() {
        Vector<String> baseNames = new Vector<String>();
        for (Base b : baseVector) {
            baseNames.add(b.getName());
        }
        return baseNames;
    }

    /**
     * 
     * @param baseName
     * @return
     */
    public Base getBase(String baseName) {
        Base base = null;
        for (Base b : baseVector) {
            if (b.getName().equals(baseName)) {
                base = b;
            }
        }
        if (base == null) {
            throw new NoSuchElementException("Exception: No Base with the name" + baseName);
        }

        return base;
    }

    public Base getBase(int index) {
        int i = index - 1;
        if (i > 4 || i < 0) {
            throw new IndexOutOfBoundsException("Exception: No such "
                    + "index for base : " + index);
        }

        return baseVector.get(i);
    }

    public void print(String s) {
        System.out.println(s + "[World Name]: " + this.name + "\n" + s + "[State]: " + this.state
                + "\n" + s + "[Open Bases]: " + totalOpen + "\n" + s + "---Base Details---");
        for (Base b : baseVector) {
            b.print("\t\t");
            System.out.println();
        }
    }

    /**
     * Constructs the base objects using builder pattern
     */
    public static class Builder {
        private String name, state;
        private int totalOpen = 0;
        private Vector<Base> baseVector = new Vector<Base>(4);

        /**
         * @param name the name to set
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * SetBaseVector allows the user to add a specific BaseVector into the world;
         * 
         * @param baseVector the baseVector to set
         */
        public Builder setbaseVector(Vector<Base> aBaseVector) {
            for (Base b : aBaseVector) {
                Base newBase = new Base.Builder().setName(b.getName())
                        .setState(b.getState()).setMemberVector(b.getMembers())
                        .baseConstruct();
                this.baseVector.add(newBase);
                if (newBase.getState().equals("Open")) {
                    totalOpen++;
                }
            }

            return this;
        }

        /**
         * ALlows the user to add a base to the base vector
         * 
         * @param aBase
         */
        public Builder addBase(Base aBase) {
            if (baseVector.size() >= 4) {
                throw new IllegalArgumentException("Exception: Already has 4 bases");
            } else {
                Base newBase = new Base.Builder().setName(aBase.getName())
                        .setState(aBase.getState()).setMemberVector(aBase.getMembers())
                        .baseConstruct();
                this.baseVector.add(newBase);
                if (newBase.getState().equals("Open")) {
                    totalOpen++;
                }
            }
            return this;
        }

        // Private helper functions

        /**
         * Adds empty bases to the vector if empty
         */
        private void fillBaseVector() {
            while (baseVector.size() < 4) {
                Base newBase = new Base.Builder().setName("Base "
                        + Integer.toString(totalOpen + 1)).setState("Open")
                        .baseConstruct();
                baseVector.add(newBase);
                totalOpen++;
            }
        }

        public World worldConstruct() {
            fillBaseVector();
            this.state = "At Risk";
            return new World(this);
        }

    }

}
