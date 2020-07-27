package model;

import exceptions.EmptyRecipeListException;
import exceptions.NoRecipeFoundException;

import java.util.ArrayList;
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
        boolean isRecipeFound = false;
        for (Recipe recipe : recipeList) {
            if (recipe.getName().equals(recipeName)) {
                recipeList.remove(recipe);
                isRecipeFound = true;
                break;
            }
        }
        if (!isRecipeFound) {
            throw new NoRecipeFoundException();
        }
    }

    // REQUIRES: recipe list is not empty
    // EFFECTS: returns list of recipes
    public String getRecipeList() throws EmptyRecipeListException {
        ArrayList<String> allRecipes = new ArrayList<>();
        if (recipeList.size() == 0) {
            throw new EmptyRecipeListException();
        } else {
            for (Recipe recipe : recipeList) {
                allRecipes.add(recipe.recipeName);
            }
            return String.join(", ", allRecipes);
        }
    }



    // EFFECTS: returns specific recipe if found
    public Boolean getRecipe(String recipeName) {
        Boolean wasRecipeFound = false;
        for (Recipe recipe : recipeList) {
            if (recipe.getName().equals(recipeName)) {
                wasRecipeFound = true;
                break;
            }
        }
        return wasRecipeFound;
    }
}
