package com.webcheckers.model;

/**
 * Model class: Player Object
 *
 * @author Shubhang Mehrotra (sm9943), Joel Clyne (jmc4514),
 * Sasha Persaud (srp4581), Quynh Duong (qnd5128), Dmitry Selin (des3358)
 */
public class Player {

    private final String name;

    private boolean signedIn;

    private boolean calledForGame;

    private boolean midGame;

    private Player opponent = null;

    public Player(String name) {
        this.name = name;
        signedIn = true;
        midGame = false;
        calledForGame = false;
    }

    public String getName(){ return name; }

    public boolean isSignedIn() { return signedIn; }

    public void signIn() { signedIn = true; }

    public void signOut() { signedIn = false; }

    public void joinGame(){ midGame = true; }

    public void exitGame(){ midGame = false; }

    public boolean isMidGame(){ return midGame; }

    public void call(){ calledForGame = true; }

    public void stopCalling(){ calledForGame = false; }

    public boolean isCalledForGame(){ return calledForGame; }

    public void setOpponent(Player opponent){ this.opponent = opponent; }

    public Player getOpponent(){ return opponent; }

    /**
     * Checks if a string contains at least one alphanumeric character or a space
     *
     * @return boolean
     */
    public boolean isNameValid(){
        return name.matches("[A-Za-z0-9 ]+");
    }
}
