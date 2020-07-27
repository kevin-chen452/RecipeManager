package model;

import exceptions.EmptyRecipeListException;
import exceptions.NoRecipeFoundException;

import java.util.LinkedList;

// represents a collection of recipes
public class Collection {
    public LinkedList recipeList;

    // EFFECTS: creates new empty collection of recipes
    public Collection() {
        this.recipeList = new LinkedList();
    }

    // MODIFIES: this
    // EFFECTS: adds recipe to collection
    public void addRecipe(Recipe recipe) {
        recipeList.add(recipe);
    }

    // REQUIRES: recipe is in collection
    // MODIFIES: this
    // EFFECTS: removes recipe from collection
    public void removeRecipe(Recipe recipe) throws NoRecipeFoundException {
        if (!recipeList.contains(recipe)) {
            throw new NoRecipeFoundException();
        } else {
            recipeList.remove(recipe);
        }
    }

    // REQUIRES: recipe list is not empty
    // EFFECTS: returns list of recipes
    public LinkedList getRecipeList() throws EmptyRecipeListException {
        if (recipeList.size() == 0) {
            throw new EmptyRecipeListException();
        } else {
            return recipeList;
        }
    }


    // EFFECTS: returns specific recipe if found
    public Boolean getRecipe(String recipeName) {
        return recipeList.contains(recipeName);
    }
}
