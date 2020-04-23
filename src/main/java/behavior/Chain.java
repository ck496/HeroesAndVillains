package main.java.behavior;

import main.java.creation.*;

public interface Chain {

    public void setNextChain(Chain nextChain);
    
    public void doWork(World aWorld);
}
