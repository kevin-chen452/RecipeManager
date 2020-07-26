package model;

import java.awt.Color;

// Source: B02-SpaceInvadersBase
// Represents a paddle.
public class Paddle {

    public static final int SIZE_X = 30;
    public static final int SIZE_Y = 8;
    public static final int DX = 20;
    public static Color color = new Color(128, 128, 128); // default gray

    private int direction;
    private int positionX;

    // EFFECTS: places paddle at (x, Y_POS) and does not move
    public Paddle(int x) {
        this.positionX = x;
        direction = 0;
    }

    public int getX() {
        return positionX;
    }

    // MODIFIES: this
    // EFFECTS: paddle faces left
    public void faceLeft() {
        direction = -1;
    }

    // MODIFIES: this
    // EFFECTS: paddle faces left
    public void faceRight() {
        direction = 1;
    }

    // MODIFIES: this
    // EFFECTS: paddle is moved DX units in either right or left direction
    public void movePaddle() {
        if (direction == 1) {
            positionX = positionX + DX;
        } else if (direction == -1) {
            positionX = positionX - DX;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets colour to be specified colour for paddle
    public void setPaddleColour(int r, int g, int b) {
        color = new Color(r, g, b);
    }
}
