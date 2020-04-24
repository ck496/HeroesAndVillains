package main.java.creation;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * Universe class will be the universe in which heros and villains reside.
 * Universe has a name and a HashMap of all the worlds.
 * 
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
     * Constructor to create universe using a builder
     * 
     * @param builder
     */
    private Universe(Builder builder) {
        this.name = builder.name;
        this.worldMap = builder.worldMap;
    }

    /**
     * gets the name of the universe
     * 
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the worlds vector
     * 
     * @return
     */
    public HashMap<String, World> getWorldMap() {
        return worldMap;
    }

    public Iterable<String> getWorldNames() {
        Iterable<String> worldNames = worldMap.keySet();
        return worldNames;
    }

    public boolean isWorldInUniverse(String worldName) {
        if (worldMap.size() == 0) {
            return false;
        } else {
            return worldMap.containsKey(worldName);
        }
    }

    public World getWorld(String worldName) {
        // World aWorld = null;
        if (!isWorldInUniverse(worldName)) {
            throw new NoSuchElementException("Cant get '" + worldName + "', not in worldMap");
        }

        return worldMap.get(worldName);
    }

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

    public void removeWorld(String worldName) {
        if (!isWorldInUniverse(worldName)) {
            throw new NoSuchElementException("Cant remove '" + worldName + "', not in worldMap");
        } else {
            worldMap.remove(worldName);
            System.out.println("Removed '" + worldName + "' from " + this.name);
        }
    }

    public void print() {
        System.out.println("Universe Name: " + this.name
                + "\n\tWorlds: " + getWorldNames() + "\n\t---World Detials---");

        Iterable<String> names = getWorldNames();

        for (String name : names) {
            worldMap.get(name).print("\t");
            System.out.println();
        }
    }

    public static class Builder {
        private String name;
        private HashMap<String, World> worldMap = new HashMap<String, World>();

        public Builder setName(String aName) {
            this.name = aName;
            return this;
        }

        public Builder setWorldMap(HashMap<String, World> aWorldMap) {
            this.worldMap = aWorldMap;
            return this;
        }

        public Builder addWorld(World aWorld) {
            World newWorld = new World.Builder().setName(aWorld.getName())
                    .setbaseVector(aWorld.getBaseVector())
                    .worldConstruct();
            this.worldMap.put(newWorld.getName(), newWorld);
            return this;

        }

        public Universe universeConstruct() {
            return new Universe(this);
        }
    }

}
