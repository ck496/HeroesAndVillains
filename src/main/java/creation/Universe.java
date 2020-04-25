package main.java.creation;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Universe class will be the universe in which heroes and villains reside.
 * Universe has a name and a HashMap of all the worlds.
 * 
 * <p>
 * BUILDER DESIGN PATTERN is used to provide a better and more flexible approach
 * to object creation. Allows you to create objects with or without manual entry
 * of all the instance variables
 * 
 * @author Chris Kurian
 * @version 1.0
 */
public class Universe {

    private String name;
    private HashMap<String, World> worldMap = new HashMap<String, World>();

    /**
     * Constructor to create universe using a builder.
     * 
     * @param builder to construct from
     */
    private Universe(Builder builder) {
        this.name = builder.name;
        this.worldMap = builder.worldMap;
    }

    /**
     * Gets the name of the universe.
     * 
     * @return name to set
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the worlds vector.
     * 
     * @return worldMap with all the worlds
     */
    public HashMap<String, World> getWorldMap() {
        return worldMap;
    }

    /**
     * Get the world names.
     * 
     * @return worldNames with names of the worlds
     */
    public Iterable<String> getWorldNames() {
        Iterable<String> worldNames = worldMap.keySet();
        return worldNames;
    }

    /**
     * Is a world in the universe.
     * 
     * @param worldName to search
     * @return true if WorldName is in the universe
     */
    public boolean isWorldInUniverse(String worldName) {
        if (worldMap.size() == 0) {
            return false;
        } else {
            return worldMap.containsKey(worldName);
        }
    }

    /**
     * Get the world based on a name.
     * 
     * @param worldName to get
     * @return World returned
     */
    public World getWorld(String worldName) {
        // World aWorld = null;
        if (!isWorldInUniverse(worldName)) {
            throw new NoSuchElementException("Cant get '" + worldName + "', not in worldMap");
        }

        return worldMap.get(worldName);
    }

    /**
     * Add a World to the universe.
     * 
     * @param aWorld to add
     */
    public void addWorld(World aWorld) {
        if (worldMap.size() >= 5) {
            throw new ArrayIndexOutOfBoundsException("Exception in Universe:"
                    + "Universe can only have 5 worlds");
        }
        if (!isWorldInUniverse(aWorld.getName())) {
            World newWorld = new World.Builder().setName(aWorld.getName())
                    .setbaseVector(aWorld.getBaseVector())
                    .worldConstruct();
            this.worldMap.put(newWorld.getName(), newWorld);
        } else {
            System.out.println("Cant add '" + aWorld.getName() + "', since it already exists");
        }
    }

    /**
     * Remove a world if its in the universe.
     * 
     * @param worldName to remove
     */
    public void removeWorld(String worldName) {
        if (!isWorldInUniverse(worldName)) {
            throw new NoSuchElementException("Cant remove '" + worldName + "', not in worldMap");
        } else {
            worldMap.remove(worldName);
            System.out.println("Removed '" + worldName + "' from " + this.name);
        }
    }

    /**
     * Print the state of the universe
     */
    public void print() {
        System.out.println("Universe Name: " + this.name
                + "\n\tWorlds: " + getWorldNames() + "\n\t---World Detials---");

        Iterable<String> names = getWorldNames();

        for (String name : names) {
            worldMap.get(name).print("\t");
            System.out.println();
        }
    }

    /**
     * Builder Class for Builder Design pattern.
     * 
     * @author Chrris Kurian
     *
     */
    public static class Builder {
        private String name;
        private HashMap<String, World> worldMap = new HashMap<String, World>();

        /**
         * Set the name of the builder.
         * 
         * @param aName to set
         * @return this Builder
         */
        public Builder setName(String aName) {
            this.name = aName;
            return this;
        }

        /**
         * Set the world map.
         * 
         * @param aWorldMap to set
         * @return this Builder
         */
        public Builder setWorldMap(HashMap<String, World> aWorldMap) {
            this.worldMap = aWorldMap;
            return this;
        }

        /**
         * Add a world to the world map.
         * 
         * @param aWorld top add
         * @return this Builder
         */
        public Builder addWorld(World aWorld) {
            World newWorld = new World.Builder().setName(aWorld.getName())
                    .setbaseVector(aWorld.getBaseVector())
                    .worldConstruct();
            this.worldMap.put(newWorld.getName(), newWorld);
            return this;

        }

        /**
         * Construct the universe.
         * 
         * @return new Universe
         */
        public Universe universeConstruct() {
            return new Universe(this);
        }
    }

}
