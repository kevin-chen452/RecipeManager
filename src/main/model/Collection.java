package model;

import exceptions.EmptyRecipeListException;
import exceptions.NoRecipeFoundException;

import java.util.LinkedList;

// represents a collection of recipes
public class Collection {
    public LinkedList<Recipe> recipeList;

    // EFFECTS: creates new collection of recipes
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
    public void removeRecipe(String recipeName) throws NoRecipeFoundException {
        for (Recipe recipe: recipeList) {
            if (recipe.getName() == recipeName) {
                recipeList.remove(recipe);
                break;
            } else {
                throw new NoRecipeFoundException();
            }
        }
    }

    // REQUIRES: recipe list is not empty
    // EFFECTS: returns list of recipes
    public String getRecipeList() throws EmptyRecipeListException {
        if (recipeList.size() == 0) {
            throw new EmptyRecipeListException();
        } else {
            for (Recipe recipe : recipeList) {
                return recipe.getName();
            }
        }
        return null;
    }



    // EFFECTS: returns specific recipe if found
    public Boolean getRecipe(String recipeName) {
        Boolean wasRecipeFound = false;
        for (Recipe recipe : recipeList) {
            if (recipe.getName() == recipeName) {
                wasRecipeFound = true;
                break;
            }
        }
        return wasRecipeFound;
    }
}
