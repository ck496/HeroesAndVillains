package main.java.behavior;

import main.java.creation.*;

/**
 * Interface is used to enable a chain of Responsibility.
 * 
 * CHAIN OF RESPONSIBILITY DESIGN PATTERN is used to handle the different
 * behavior and states of all the different objects. A World object is passed to
 * the first class in the chain and it does the work and passes it to the next
 * and loops back until the Worlds "State" is no longer at risk
 * WorkChain->CreateChain->FightChain->ExitChain. ExitChain loops back to
 * WorkChain if the World's state is "At Risk", if the World is no longer "At
 * Risk" the program exits and prints final messages
 * 
 * @author Chris Kurian
 * @version 3.0
 *
 */
public interface Chain {

    public void setNextChain(Chain nextChain);

    public void doWork(World aWorld);
}
