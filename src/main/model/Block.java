package model;

import java.awt.Color;

// Source: B02-SpaceInvadersBase
// Represents a block.
public class Block {
    public static final int SIZE_X = 30;
    public static final int SIZE_Y = 8;
    public static Color color = new Color(211, 211, 211); // default light gray

    private int positionX;
    private int positionY;

    // EFFECTS: block is positioned at coordinates (x, y)
    public Block(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    public int getX() {
        return positionX;
    }

    public int getY() {
        return positionY;
    }

    // MODIFIES: this
    // EFFECTS: sets colour to be specified colour for block
    public void setBlockColour(int r, int g, int b) {
        color = new Color(r, g, b);
    }
}
