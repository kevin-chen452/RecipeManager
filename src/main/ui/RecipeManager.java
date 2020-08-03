package ui;

import exceptions.*;
import model.Collection;
import model.Recipe;
import persistence.Reader;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

// recipe manager application
public class RecipeManager {
    private static final String RECIPES_FILE = "./data/recipes.txt";
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
        String command;
        input = new Scanner(System.in);
        loadRecipes();
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
        System.out.println("\nGoodbye! Hope you will get cooking soon :)");
    }

    // MODIFIES: this
    // EFFECTS: loads recipes into collection from RECIPES_FILE, if that file exists, otherwise do nothing
    private void loadRecipes() {
        try {
            collection = Reader.readRecipes(new File(RECIPES_FILE));
        } catch (IOException e) {
            // do nothing
        }
    }

    // EFFECTS: saves state of collection to RECIPES_FILE
    private void saveRecipes() {
        if (collection.recipeList.size() == 0) {
            System.out.println("Sorry, there are no recipes in the list to save right now.");
        } else {
            try {
                Writer writer = new Writer(new File(RECIPES_FILE));
                writer.write(collection);
                writer.close();
                System.out.println("Recipes saved to file " + RECIPES_FILE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to save recipes to " + RECIPES_FILE);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                // this is due to a programming error
            }
        }
        System.out.println("Taking you back to the main menu now...");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to Recipe Manager! What would you like to do today?");
        System.out.println("\ta -> add recipe");
        System.out.println("\tr -> remove recipe");
        System.out.println("\tl -> locate recipe");
        System.out.println("\tra -> rate existing recipe");
        System.out.println("\tin -> add instructions to existing recipe");
        System.out.println("\tc -> clear instructions for existing recipe");
        System.out.println("\tt -> set recipe time for existing recipe");
        System.out.println("\ti -> adds and removes ingredients from an existing recipe");
        System.out.println("\ts -> save recipes to file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddRecipe();
        } else if (command.equals("r")) {
            doRemoveRecipe();
        } else if (command.equals("l")) {
            doLocateRecipe();
        } else if (command.equals("ra")) {
            doRecipeRating();
        } else if (command.equals("in")) {
            doAddInstructions();
        } else if (command.equals("c")) {
            doClearInstructions();
        } else if (command.equals("t")) {
            doRecipeTime();
        } else if (command.equals("i")) {
            doModifyIngredients();
        } else if (command.equals("s")) {
            saveRecipes();
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
        System.out.println("Taking you back to the main menu now...");
    }

    // MODIFIES: this
    // EFFECTS: conducts the removal of a recipe
    private void doRemoveRecipe() {
        if (collection.recipeList.size() == 0) {
            System.out.println("Sorry, there are no recipes in the list right now.");
        } else {
            System.out.println("Enter the name of the recipe you want to remove. Here's a list of known recipes:");
            System.out.println(tryGetRecipeList());
            String name = input.nextLine();
            try {
                collection.removeRecipe(name);
                System.out.println("Recipe " + name + " has been successfully removed!");
            } catch (NoRecipeFoundException e) {
                System.out.println("Recipe not found... sorry");
            }
        }
        System.out.println("Taking you back to the main menu now...");
    }

    // MODIFIES: this
    // EFFECTS: conducts the locating of a recipe
    private void doLocateRecipe() {
        if (collection.recipeList.size() == 0) {
            System.out.println("Sorry, there are no recipes in the list right now.");
        } else {
            System.out.println("Input the name of the recipe you want. Here's a list of known recipes:");
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
                System.out.println("Instructions (" + recipe.instructions.size() + " step"
                        + makePlural(recipe.instructions.size()) + "):\n" + recipe.getInstructionsList());
            } else {
                System.out.println("Sorry, that recipe does not exist.");
            }
        }
        System.out.println("Taking you back to the main menu now...");
    }

    // EFFECTS: checks if an s needs to be added or not
    private String makePlural(int num) {
        if (num == 1) {
            return "";
        } else {
            return "s";
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the rating of a recipe
    private void doRecipeRating() {
        if (collection.recipeList.size() == 0) {
            System.out.println("Sorry, there are no recipes in the list right now.");
        } else {
            System.out.println("Select the recipe you want to rate. Here's a list of known recipes:");
            System.out.println(tryGetRecipeList());
            helpDoRecipeRating();
        }
        System.out.println("Taking you back to the main menu now...");
    }

    // MODIFIES: this
    // EFFECTS: helps perform recipe rating
    private void helpDoRecipeRating() {
        String name = input.nextLine();
        Recipe recipe = collection.getRecipe(name);
        if (recipe != null) {
            System.out.println("You have chosen " + recipe.recipeName + ". Leave rating as a number from 1-5.");
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
    }

    // MODIFIES: this
    // EFFECTS: conducts the adding of instructions
    private void doAddInstructions() {
        if (collection.recipeList.size() == 0) {
            System.out.println("Sorry, there are no recipes in the list right now.");
        } else {
            System.out.println("First, input the name of the recipe that you want to add instructions for.");
            System.out.println("Here's a list of known recipes:\n" + tryGetRecipeList());
            String name = input.nextLine();
            Recipe recipe = collection.getRecipe(name);
            if (recipe != null) {
                System.out.println("You have chosen recipe " + recipe.recipeName + ". Add a preparation step, or enter"
                        + " \"done\" if you accidentally clicked this and do not actually want to add instructions.");
                helpDoAddInstructions(name);
            } else {
                System.out.println("Sorry, that recipe does not exist.");
            }
        }
        System.out.println("Taking you back to the main menu now...");
    }

    // MODIFIES: this
    // EFFECTS: does the actual instructions adding for a given recipe
    private void helpDoAddInstructions(String name) {
        boolean done = true;
        while (done) {
            String step = input.nextLine();
            Recipe recipe = collection.getRecipe(name);
            if (step.equals("done")) {
                done = false;
                System.out.println("These are the instructions for this recipe:");
                System.out.println(recipe.getInstructionsList());
            } else {
                try {
                    recipe.addInstructions(step);
                    System.out.println("Preparation step added successfully! Input your next step, or \"done\" "
                            + "if you are finished.");
                } catch (EmptyInstructionsException e) {
                    System.out.println("Please specify a valid preparation step.\n");
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: does the clearing of instructions for a given recipe
    private void doClearInstructions() {
        if (collection.recipeList.size() == 0) {
            System.out.println("Sorry, there are no recipes in the list right now.");
        } else {
            System.out.println("First, input the name of the recipe that you want to remove instructions for.");
            System.out.println("Here's a list of known recipes:\n" + tryGetRecipeList());
            String name = input.nextLine();
            Recipe recipe = collection.getRecipe(name);
            if (recipe != null) {
                System.out.println("You have chosen recipe " + recipe.recipeName + ". Are you sure you want to "
                        + "clear all instructions? (input \"yes\" or \"no\")");
                String decision = input.nextLine();
                if (decision.equals("yes")) {
                    recipe.clearInstructions();
                    System.out.println("Instructions for recipe " + recipe.recipeName + " have been successfully "
                            + "cleared.");
                } else if (decision.equals("no")) {
                    System.out.println("Instructions for recipe " + recipe.recipeName + " were not cleared.");
                }
            } else {
                System.out.println("Sorry, that recipe does not exist.");
            }
        }
        System.out.println("Taking you back to the main menu now...");
    }

    // MODIFIES: this
    // EFFECTS: conducts the setting of a recipe's preparation time
    private void doRecipeTime() {
        if (collection.recipeList.size() == 0) {
            System.out.println("Sorry, there are no recipes in the list right now.");
        } else {
            System.out.println("First, input the name of the recipe whose time you want to set.");
            System.out.println("Here's a list of known recipes:\n" + tryGetRecipeList());
            String name = input.nextLine();
            helpDoRecipeTime(name);
        }
        System.out.println("Taking you back to the main menu now...");
    }

    // MODIFIES: this
    // EFFECTS: runs the actual setting for a given recipe
    private void helpDoRecipeTime(String name) {
        Recipe recipe = collection.getRecipe(name);
        if (recipe != null) {
            System.out.println("You have chosen " + recipe.recipeName + ". Add a positive time in minutes.");
            int time = input.nextInt();
            try {
                recipe.setCookingTime(time);
                System.out.println("The cooking time for " + name + " has been set to: " + time
                        + " minute" + makePlural(time) + "!");
            } catch (IllegalTimeException e) {
                System.out.println("Sorry, that's not a valid time.");
            }
        } else {
            System.out.println("Sorry, that recipe does not exist.");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts the adding of multiple ingredients to a recipe
    private void doModifyIngredients() {
        if (collection.recipeList.size() == 0) {
            System.out.println("Sorry, there are no recipes in the list right now.");
        } else {
            System.out.println("First, input the name of the recipe that you want to add or remove ingredients from.");
            System.out.println("Here's a list of known recipes:\n" + tryGetRecipeList());
            helpChooseIngredientMode();
        }
        System.out.println("Taking you back to the main menu now...");
    }

    // EFFECTS: helps conduct the user's choice of adding or removing ingredients
    private void helpChooseIngredientMode() {
        String name = input.nextLine();
        Recipe recipe = collection.getRecipe(name);
        if (recipe != null) {
            System.out.println("You have chosen recipe " + recipe.recipeName + ".");
            System.out.println("Enter \"add\" to add ingredients, or \"remove\" to remove ingredients,");
            System.out.println("or \"done\" if you would like to go back to the main menu.");
            String choice = input.nextLine();
            recipe = collection.getRecipe(name);
            if (choice.equals("add")) {
                System.out.println("You have chosen to add ingredients. Add an ingredient.");
                helpAddIngredients(name);
            } else if (choice.equals("remove")) {
                System.out.println("You have chosen to remove ingredients. Remove an ingredient.");
                System.out.println("Here's a list of known ingredients: " + recipe.getIngredientList());
                helpRemoveIngredients(name);
            } else if (!choice.equals("done")) {
                System.out.println("Invalid input.");
            }
        } else {
            System.out.println("Sorry, that recipe does not exist.");
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

    // MODIFIES: this
    // EFFECTS: removes ingredients from recipe until user inputs "done"
    private void helpRemoveIngredients(String name) {
        boolean done = true;
        while (done) {
            String ingredient = input.nextLine();
            Recipe recipe = collection.getRecipe(name);
            if (ingredient.equals("done")) {
                done = false;
                System.out.println("These are the ingredients in this recipe:");
                System.out.println(recipe.getIngredientList());
            } else {
                try {
                    recipe.removeIngredient(ingredient);
                    System.out.println("You have removed " + ingredient + "! Input your next ingredient, or \"done\" "
                            + "if you are finished.");
                    System.out.println("These are the remaining ingredients: " + recipe.getIngredientList());
                } catch (EmptyIngredientException e) {
                    System.out.println("Sorry, there are no more ingredients in this recipe.");
                    done = false;
                } catch (NoIngredientFoundException e) {
                    System.out.println("Ingredient not found.");
                }
            }
        }
    }

    // EFFECTS: prints name of recipe to the screen
    private void printRecipeName(String selected) {
        System.out.println("Your new recipe's name is: " + selected);
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

