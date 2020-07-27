package model;

import exceptions.EmptyRecipeListException;
import exceptions.NoRecipeFoundException;

import java.util.LinkedList;
import model.Recipe;

// represents a collection of recipes
public class Collection {
    public LinkedList recipeList;

    // EFFECTS: creates new collection of recipes, three different recipes as default
    public Collection() {
        this.recipeList = new LinkedList();
        Recipe recipe1 = new Recipe("kimchi fried rice");
        Recipe recipe2 = new Recipe("injeolmi bingsu");
        Recipe recipe3 = new Recipe("chicken sandwich");
        recipeList.add(recipe1);
        recipeList.add(recipe2);
        recipeList.add(recipe3);
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
        for (Recipe recipe: recipeList) { // I don't understand why this must be of type Object
            if (recipe.getName() == recipeName) {
                recipeList.remove(recipeName);
            } else {
                throw new NoRecipeFoundException();
            }
        }
    }

    /*
    public void removeRecipe(String recipeName) throws NoRecipeFoundException {
        if (recipeList.contains(recipeName)) {
            recipeList.remove(recipeName);
        } else {
            throw new NoRecipeFoundException();
        }
    } */



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
