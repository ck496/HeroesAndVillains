package main.java;

import java.util.Random;

public class Character {


     protected String[] stateListV = { "Attack", "Create", "Fight", "Recover", "Move", "Start", "Dead" };

     protected String[] stateListH = { "Protect", "Create", "Fight", "Recover", "Move", "Start", "Dead" };

     
    private String name;
    private boolean isHero;
    private String state;
    private String power;
    private String weakness;
    private String strength;
    private int level;
    private int health;

    public Character(Builder builder) {
        this.isHero = builder.isHero;
        this.state = builder.state;
        this.level=builder.level;
        this.health=builder.health;
        this.name = builder.name;
        this.power=builder.power;
        this.strength = builder.strength;
        this.weakness = builder.weakness;
        
    }

    /**
     * Function Returns the name.
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * Returns the current state;
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Allows you to change the state of the character.
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Get the power.
     * @return the power
     */
    public String getPower() {
        return power;
    }

    
    /**
     * Get the level;
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * THis method lets you update the level as they win battles; 
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the health.
     * @return the health
     */
    public int getHealth() {
        return health;
    }

    /**
     * @param health the health to set
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @return the isHero
     */
    public boolean isHero() {
        return isHero;
    }
    
    /**
     * @return the weaknes
     */
    public String getWeakness() {
        return weakness;
    }
    
    /**
     * @return the strength
     */
    public String getStrength() {
        return strength;
    }

  
    
    /**
     * THis method prints the state of the character.
     */
    public void print() {
        String type;
        if(isHero) {
            type = "Hero";
        }
        else {
            type = "Villain";
        }
        
        System.out.println("------ "+type + " Detials ------\n\t[Name]: " + this.name 
                + "\n\t[Health]: " + this.health + "\n\t[State]: " + this.state 
                + "\n\t[Power]: " + this.power + "\n\t[Level]: " + this.level + 
                "\n\t[+Strength]: " + this.strength + "\n\t[-Weakness]: " + this.weakness);
    }
    
    /*
     * Builder class Builds the Character.
     */
    public static class Builder {
        

        private String[] villanFirst = { "Death", "Doom", "Revenge", "Ghost", "Rosenheim", "Demon", "Kunigunde von", "Lord",
               "Rübezahl", "Hell", "Soul", "Petermännchen", "Dark", "Night", "Blood" };

        private String[] villanLast = { "Destroyer", "Creator", "Worshiper", "Orlamünde", "Reaper", "Collector", "Krampus",
               "Opressor", "Poltergeist", "Hansel", "Grethel", "Elwetritschen", "Vader", "Bone", "Terror" };

        private String[] heroFirst = { "Captian", "Sonic", "Super", "Beast", "Crystal", "Speed", "Light", "Power", "Lord",
               "Vision", "Hell", "Soul", "Doctor", "Galactic", "Aqua" };

        private String[] heroLast = { "Surfer", "Boy", "Women", "Man", "Girl", "Avenger", "Protector", "Defender", "Jedi",
               "Hansel", "Grethel", "Belsnickel", "Gaurdian", "Slayer", "Thunder" };
        
        private String[] powerList = { "Fire", "Water", "Earth", "Electric", "Psychic", "Dark", "Fighting" };
        
        private String name;
        private boolean isHero;
        private String state;
        private String power;
        private String strength;
        private String weakness;
        private int level;
        private int health;
        
        
        /**
         * Allows you to pick if its a hero or not.
         * @param isHero
         * @return
         */
        public Builder isHero(boolean isHero){
            this.isHero = isHero;
            return this;
            
        }
        /**
         * Function uses a random generator to pick a name
         * based on hero or villain.
         * @param name the name to set
         */
        private void setName() {
            int firstIndex = randomGen(0, villanFirst.length-1);
            int lastIndex = randomGen(0, villanLast.length-1);
            if(isHero) {
                this.name = heroFirst[firstIndex] + " " + heroLast[lastIndex];
            }
            else {
                this.name = villanFirst[firstIndex] + " " + villanLast[lastIndex];
            }
        }
        
        private int randomGen(int start, int end) {
            
            Random random = new Random();
            int ranInt = random.nextInt(end - start + 1)+start;
            return ranInt;
        }
        
        /**
         * @param power the power to set
         */
        private void setPower() {
            int powerIndex = randomGen(0, powerList.length -1);
            this.power = powerList[powerIndex];
        }
        
        /**
         * @param weaknes the weakness to set
         */
        /**
         * @param weakness the weakness to set
         */
        private void setWeakness() {
            if(this.power.equals("Fire")){
                this.weakness = "Water";
            }
            else if(this.power.equals("Water")){
                this.weakness = "Electric";
            }
            else if(this.power.equals("Earth")){
                this.weakness = "Fire";
            }
            else if(this.power.equals("Electric")){
                this.weakness = "Earth";
            }
            else if(this.power.equals("Psychic")){
                this.weakness = "Dark";
            }
            else if(this.power.equals("Dark")){
                this.weakness = "Fighting";
            }
            else if(this.power.equals("Fighting")){
                this.weakness = "Psychic";
            }
        }
      

        /**
         * @param strength the strength to set
         */
        private void setStrength() {
            if(this.power.equals("Fire")){
                this.strength = "Earth";
            }
            else if(this.power.equals("Water")){
                this.strength = "Fire";
            }
            else if(this.power.equals("Earth")){
                this.strength = "Electric";
            }
            else if(this.power.equals("Electric")){
                this.strength = "Water";
            }
            else if(this.power.equals("Psychic")){
                this.strength = "Fighting";
            }
            else if(this.power.equals("Dark")){
                this.strength = "Psychic";
            }
            else if(this.power.equals("Fighting")){
                this.strength = "Dark";
            }
        }
        
        /**
         * constructCharacter() builds and returns a Character obj.
         * 
         * @return Character
         */
        public Character constructCharacter() {
            this.state = "Start";
            this.level=1;
            this.health=100;
            setName();
            setPower();
            setStrength();
            setWeakness();
            return new Character(this);
        }
    
        
        
    }
    
    

    
    
    
    
    
    
    
    
    
  

}
