package model;

import java.awt.Color;

// Source: B02-SpaceInvadersBase
// Represents a ball.
public class Ball {
    public static final int RADIUS = 10;
    public static final int SPEED = 5;
    public static Color color = new Color(128, 128, 128); // default gray

    public int positionX;
    public int positionY;
    public Direction direction;

    // EFFECTS: the constructed ball is placed at coordinates (x, y)
    public Ball(int x, int y) {
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
    // EFFECTS: ball moves SPEED units diagonally
    public void moveBalls() {
        switch (direction) {
            case UPPERLEFT:
                positionX = positionX - SPEED / 2;
                positionY = positionY - SPEED / 2;
            case UPPERRIGHT:
                positionX = positionX + SPEED / 2;
                positionY = positionY - SPEED / 2;
            case LOWERLEFT:
                positionX = positionX - SPEED / 2;
                positionY = positionY + SPEED / 2;
            case LOWERRIGHT:
                positionX = positionX + SPEED / 2;
                positionY = positionY + SPEED / 2;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets colour to be specified colour for ball
    public void setBallColour(int r, int g, int b) {
        color = new Color(r, g, b);
    }
}

