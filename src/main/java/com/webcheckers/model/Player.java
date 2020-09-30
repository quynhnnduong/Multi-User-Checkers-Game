package com.webcheckers.model;

/**
 * Model class: Player Object
 *
 * @author Shubhang Mehrotra (sm9943), Joel Clyne (jmc4514), Sasha Persaud (srp4581), Quynh Duong (qnd5128)
 */
public class Player {
    private String name;
    private boolean isSignedIn;

    public Player(String name){
        this.name = name;
        this.isSignedIn = true;
    }

    public String getName(){
        return name;
    }

    public boolean getIsSignedIn() {
        return isSignedIn;
    }

    public void signIn() {
        isSignedIn = true;
    }

    public void signOut() {
        isSignedIn = false;
    }
}
