package model;

import exceptions.EmptyRecipeListException;
import exceptions.NoRecipeFoundException;
import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

// represents a collection of recipes
public class Collection implements Saveable {
    public LinkedList<Recipe> recipeList;

    // EFFECTS: creates new collection of recipes
    public Collection() {
        this.recipeList = new LinkedList();
    }

    public Recipe get(int index) {
        return recipeList.get(index);
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
    public Recipe getRecipe(String recipeName) {
        for (Recipe recipe : recipeList) {
            if (recipe.getName().equals(recipeName)) {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public void save(PrintWriter printWriter) {
        for (Recipe recipe : recipeList) {
            printWriter.print(recipe.recipeName);
            printWriter.print(Reader.DELIMITER);
            printWriter.print(recipe.ingredients);
            printWriter.print(Reader.DELIMITER);
            printWriter.print(recipe.cookingTime);
            printWriter.print(Reader.DELIMITER);
            printWriter.println(recipe.rating);
            printWriter.print(Reader.DELIMITER);
            printWriter.println(recipe.instructions);
        }
    }
}
