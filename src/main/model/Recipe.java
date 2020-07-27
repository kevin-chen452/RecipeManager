package model;

import exceptions.*;
import java.util.LinkedList;

// represents a recipe
public class Recipe {
    public String name;
    private int cookingTime;
    public String recipeName;
    public LinkedList ingredients = new LinkedList();
    public int rating = 0;

    // EFFECTS: creates a new Recipe with title
    public Recipe(String title)  {
        recipeName = title;
    }

    public String getName() {
        return recipeName;
    }

    public LinkedList getIngredientList() {
        return ingredients;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public int getRating() {
        return rating;
    }

    // REQUIRES: ingredient is non-zero length
    // MODIFIES: this
    // EFFECTS: adds ingredient to ingredient list
    public void addIngredient(String ingredient) throws EmptyIngredientException {

        if (ingredient.length() == 0) {
            throw new EmptyIngredientException();
        } else {
            ingredients.add(ingredient);
        }
    }

    // REQUIRES: ingredient is non-zero length and is in ingredient list
    // MODIFIES: this
    // EFFECTS: removes ingredient from ingredient list
    public void removeIngredient(String ingredient) throws EmptyIngredientException, NoIngredientException {
        if (ingredient.length() == 0) {
            throw new EmptyIngredientException();
        } else if (!ingredients.contains(ingredient)) {
            throw new NoIngredientException();
        } else {
            ingredients.remove(ingredient);
        }
    }

    // REQUIRES: time is a non-negative integer
    // EFFECTS: sets cooking time to time specified
    public void setCookingTime(int time) throws IllegalTimeException {
        if (time > 0) {
            cookingTime = time;
        } else {
            throw new IllegalTimeException();
        }
    }

    // REQUIRES: numStars is between 1 to 5
    // EFFECTS: sets rating to be number of stars specified
    public void setRating(int numStars) throws IllegalRateException {
        if (1 <= numStars && numStars <= 5) {
            rating = numStars;
        } else {
            throw new IllegalRateException();
        }
    }
}
