package ui;

import exceptions.*;
import model.Collection;
import model.Recipe;

import java.util.Scanner;

// recipe manager application
public class RecipeManager {
    private Scanner input;
    private Collection collection = new Collection();

    // Source: TellerApp
    // EFFECTS: runs recipe manager application
    public RecipeManager() {
        runRecipeManager();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runRecipeManager() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
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
        System.out.println("\tt -> set recipe time for existing recipe");
        System.out.println("\ti -> adds ingredients to a recipe");
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
        } else if (command.equals("t")) {
            doRecipeTime();
        } else if (command.equals("i")) {
            doAddIngredients();
        } else if (command.equals("m")) {
            displayMenu();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the adding of a recipe
    private void doAddRecipe() {
        System.out.print("Enter the name of your new recipe:\n");
        String name = input.nextLine();
        Recipe newRecipe = new Recipe(name);
        if (name.length() > 0) {
            collection.addRecipe(newRecipe);
            printRecipeName(name);
        } else {
            System.out.println("Sorry, that's not a valid recipe name.");
        }
        System.out.println("Taking you back out to the main menu now...");
    }

    // MODIFIES: this
    // EFFECTS: conducts the removal of a recipe
    private void doRemoveRecipe() {
        System.out.println("Enter the name of the recipe you want to remove. Here's a list of recipes to help you:");
        System.out.println(tryGetRecipeList());
        String name = input.nextLine();
        try {
            collection.removeRecipe(name);
            System.out.println("Recipe " + name + " has been successfully removed!");
        } catch (NoRecipeFoundException e) {
            System.out.println("Recipe not found... sorry");
        }
        System.out.println("Taking you back to the main menu now...");
    }


    // MODIFIES: this
    // EFFECTS: conducts the viewing of a recipe
    private void doViewRecipes() {
        System.out.println("Here are the recipes present in the system:");
        try {
            System.out.println(collection.getRecipeList());
        } catch (EmptyRecipeListException e) {
            System.out.println("Sorry! It seems there are no recipes in the system currently...");
        }
        System.out.println("Taking you back to the main menu now...");
    }

    // MODIFIES: this
    // EFFECTS: conducts the locating of a recipe
    private void doLocateRecipe() {
        System.out.println("Input the name of the recipe you want: here's a list of known recipes:");
        System.out.println(tryGetRecipeList());
        String name = input.nextLine();
        Recipe recipe = collection.getRecipe(name);
        if (recipe != null) {
            System.out.println("Yes! Recipe " + recipe.recipeName + " has been located. Here are the details:");
            System.out.println("Recipe name: " + recipe.getName());
            System.out.println("Ingredients: " + recipe.getIngredientList());
            System.out.println("Preparation time: " + recipe.getCookingTime() + " minute"
                    + makePlural(recipe.getCookingTime()));
            System.out.println("Rating: " + recipe.getRating() + " star" + makePlural(recipe.getRating()));
            System.out.println("Taking you back to the main menu now...");
        } else {
            System.out.println("Sorry, that recipe does not exist.");
            System.out.println("Taking you back to the main menu now...");
        }
    }

    // EFFECTS: checks if an s needs to be added or not
    private String makePlural(int num) {
        if (num <= 1) {
            return "";
        } else {
            return "s";
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the rating of a recipe
    private void doRecipeRating() {
        System.out.println("Select the recipe you want to rate. Here's a list to help you:");
        System.out.println(tryGetRecipeList());
        String name = input.nextLine();
        Recipe recipe = collection.getRecipe(name);
        if (recipe != null) {
            System.out.println("You have chosen " + recipe.recipeName + ". Leave your rating as a number from 1-5.");
            int rating = input.nextInt();
            try {
                recipe.setRating(rating);
                System.out.println("You have set the rating for " + name + " to be: " + rating);
            } catch (IllegalRateException e) {
                System.out.println("Sorry, that's not a valid rating.");
            }
        } else {
            System.out.println("Sorry, that recipe does not exist.");
        }

        System.out.println("Taking you back to the main menu now...");
    }

    // MODIFIES: this
    // EFFECTS: conducts the setting of a recipe's preparation time
    private void doRecipeTime() {
        System.out.println("First, input the name of the recipe whose time you want to set.");
        System.out.println("Here's a list to help you:\n" + tryGetRecipeList());
        String name = input.nextLine();
        Recipe recipe = collection.getRecipe(name);
        if (recipe != null) {
            System.out.println("You have chosen " + recipe.recipeName + ". Add a positive time in minutes.");
            int time = input.nextInt();
            try {
                recipe.setCookingTime(time);
                System.out.println("The cooking time for " + name + " has been set to: " + time
                        + " minute" + makePlural(time) + "!");
                System.out.println("Taking you back to the main menu now...");
            } catch (IllegalTimeException e) {
                System.out.println("Sorry, that's not a valid time. Taking you back to the main menu now...\n");
            }
        } else {
            System.out.println("Sorry, that recipe does not exist.");
            System.out.println("Taking you back to the main menu now...");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the adding of multiple ingredients to a recipe
    private void doAddIngredients() {
        System.out.println("First, input the name of the recipe that you want to add ingredients to.");
        System.out.println("Here's a list to help you:\n" + tryGetRecipeList());
        String name = input.nextLine();
        Recipe recipe = collection.getRecipe(name);
        if (recipe != null) {
            System.out.println("You have chosen recipe " + recipe.recipeName + ". Add an ingredient.");
            helpAddIngredients(name);
        } else {
            System.out.println("Sorry, that recipe does not exist.");
            System.out.println("Taking you back to the main menu now...");
        }

    }

    // MODIFIES: this
    // EFFECTS: adds ingredients for recipe name until user inputs "done"
    private void helpAddIngredients(String name) {
        boolean done = true;
        while (done) {
            String ingredient = input.nextLine();
            Recipe recipe = collection.getRecipe(name);
            if (ingredient.equals("done")) {
                done = false;
                System.out.println("These are the ingredients in this recipe:");
                System.out.println(recipe.getIngredientList());
                System.out.println("Taking you back to the main menu now...");
            } else {
                try {
                    recipe.addIngredient(ingredient);
                    System.out.println("You have added " + ingredient + "! Input your next ingredient, or \"done\" "
                            + "if you are finished.");
                } catch (EmptyIngredientException e) {
                    System.out.println("Please specify a valid ingredient.\n");
                }
            }
        }
    }


    // EFFECTS: prints name of recipe to the screen
    private void printRecipeName(String selected) {
        System.out.println("Your new recipe name is: " + selected);
    }

    // EFFECTS: tries and catches EmptyRecipeListException
    public String tryGetRecipeList() {
        try {
            return collection.getRecipeList();
        } catch (EmptyRecipeListException e) {
            return "Sorry! It seems there are no recipes in the system currently...\n";
        }
    }

}

