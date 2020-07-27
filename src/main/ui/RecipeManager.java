package ui;

import exceptions.EmptyRecipeListException;
import exceptions.IllegalRateException;
import exceptions.NoRecipeFoundException;
import model.Collection;
import model.Recipe;

import java.util.Scanner;

// recipe manager application
public class RecipeManager {
    private Scanner input;
    private Recipe recipe;
    private Collection collection;

    // Source: TellerApp
    // EFFECTS: runs recipe manager application
    public RecipeManager() {
        runRecipeManager();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runRecipeManager() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to Recipe Manager! What would you like to do today?");
        System.out.println("\ta -> add recipe");
        System.out.println("\tr -> remove recipe");
        System.out.println("\tv -> view recipes");
        System.out.println("\tl -> locate recipe");
        System.out.println("\tra -> rate existing recipe");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddRecipe();
        } else if (command.equals("r")) {
            doRemoveRecipe();
        } else if (command.equals("v")) {
            doViewRecipes();
        } else if (command.equals("l")) {
            doLocateRecipe();
        } else if (command.equals("ra")) {
            doRecipeRating();
        } else if (command.equals("m")) {
            displayMenu();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the adding of a recipe
    private void doAddRecipe() {
        System.out.print("\nEnter the name of your new recipe: ");
        String name = input.nextLine();
        new Recipe(name);
        printRecipeName(name);
    }


    // MODIFIES: this
    // EFFECTS: conducts the removal of a recipe
    private void doRemoveRecipe() {
        System.out.println("\nEnter the name of the recipe you want to remove: ");
        String name = input.nextLine();
        try {
            collection.removeRecipe(recipe);
        } catch (NoRecipeFoundException e) {
            System.out.println("Recipe not found...\n");
        }
        System.out.println("\nRecipe " + name + " has been successfully removed!");
        System.out.println("\nPress m to return to the main menu.");
    }


    // MODIFIES: this
    // EFFECTS: conducts the viewing of a recipe
    private void doViewRecipes() {
        System.out.println("\nHere are the recipes present in the system:");
        try {
            collection.getRecipeList();
        } catch (EmptyRecipeListException e) {
            System.out.println("\nSorry! It seems there are no recipes in the system currently...");
        }
        System.out.println("\nPress m to return to the main menu.");
    }

    // MODIFIES: this
    // EFFECTS: conducts the locating of a recipe
    private void doLocateRecipe() {
        System.out.println("\nInput the name of the recipe you are trying to find. Here's a list to help you:");
        tryGetRecipeList();
        String name = input.nextLine();
        System.out.println("\nLet's see if we can find recipe " + name + "...");
        if (collection.getRecipe(recipe.recipeName)) {
            System.out.println("\nYes! it has been located. Here are the details:");
            recipe.getName();
            recipe.getIngredientList();
            recipe.getCookingTime();
            recipe.getRating();
            System.out.println("\nPress m to return to the main menu.");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the rating of a recipe
    private void doRecipeRating() {
        System.out.println("\nSelect the recipe you want to rate. Here's a list to help you:");
        tryGetRecipeList();
        String name = input.nextLine();
        if (collection.getRecipe(name)) {
            System.out.println("\nYou have chosen" + recipe.recipeName + ". Leave your rating as a number from 1-5.");
        }
        int rating = input.nextInt();
        try {
            recipe.setRating(rating);
        } catch (IllegalRateException e) {
            System.out.println("\nSorry, that's not a valid rating. A number between 1 to 5 please.");
        }
        System.out.println("\nYou have set the rating for " + name + " to be: " + rating + "!");
    }

    // EFFECTS: prints name of recipe to the screen
    private void printRecipeName(String selected) {
        System.out.println("\nYour new recipe name: " + selected);
    }

    // EFFECTS: tries and catches EmptyRecipeListException
    public void tryGetRecipeList() {
        try {
            collection.getRecipeList();
        } catch (EmptyRecipeListException e) {
            System.out.println("\nSorry! It seems there are no recipes in the system currently...");
        }
    }
}
