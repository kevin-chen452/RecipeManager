package model;

import exceptions.EmptyGameTipException;
import exceptions.EmptyGameTipListException;
import java.util.Random;

import java.util.List;

// Represents game tips for this game.
public class GameTip {
    public static String gameTip;
    public static List<String> GameTip;

    // MODIFIES: this
    // EFFECTS: creates default game tips upon startup
    public void setGameTips() {
        GameTip.add("Use your paddle to keep the ball in play, so don't let it touch the bottom!");
        GameTip.add("If the current difficulty is too hard, try Medium or Easy mode!");
        GameTip.add("Control the paddle with the left and right arrow keys.");
        GameTip.add("Try activating a Powerup for a boost!");
        GameTip.add("The ball can only travel in diagonal directions.");
        GameTip.add("Hit the ball with the left side of the paddle to send it"
                + " diagonally to the left, and vice versa.");
    }

    // REQUIRES: string is non-zero length
    // MODIFIES: this
    // EFFECTS: adds the string into the list of game tips saved.
    public void addGameTips(String s) throws EmptyGameTipException {
        if (0 == s.length()) {
            throw new EmptyGameTipException();
        }
        GameTip.add(s);
    }

    // REQUIRES: string is non-zero length
    // MODIFIES: this
    // EFFECTS: removes the game tip from the list of game tips saved
    public void removeGameTips(String s) throws EmptyGameTipException {
        if (0 == s.length()) {
            throw new EmptyGameTipException();
        }
        GameTip.remove(s);
    }

    // REQUIRES: GameTip list is not empty
    // EFFECTS: picks a game tip at random
    public String pickGameTip() throws EmptyGameTipListException {
        if (GameTip.size() == 0) {
            throw new EmptyGameTipListException();
        }
        Random rand = new Random();
        return GameTip.get(rand.nextInt(GameTip.size()));
    }
}
