package persistence;

import model.Collection;
import model.Recipe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// source: TellerApp
// A reader that can read recipe data from a file
public class Reader {
    public static final String DELIMITER = " // "; // in case commas are inputted by user

    // EFFECTS: returns a list of recipes parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static Collection readRecipes(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of recipes parsed from list of strings
    // where each string contains data for one recipe
    private static Collection parseContent(List<String> fileContent) {
        Collection recipes = new Collection();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            recipes.addRecipe(parseRecipe(lineComponents));
        }

        return recipes;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has arbitrary size where element 0 represents the
    // name of the next recipe to be constructed, the next elements represent ingredients (until an element can be
    // parse int), which is the cooking time, element immediately after is the rating, and
    // the remaining elements represent instructions
    // EFFECTS: returns a recipe constructed from components
    private static Recipe parseRecipe(List<String> components) {
        String recipeName = components.get(0);
        LinkedList ingredients = helpGetIngredients(components);
        int cookingTimes = Integer.parseInt(components.get(ingredients.size()));
        int ratings = Integer.parseInt(components.get(ingredients.size() + 1));
        LinkedList instructions = helpGetInstructions(components);
        Recipe thisRecipe = new Recipe(recipeName);
        thisRecipe.cookingTime = cookingTimes;
        thisRecipe.ingredients = ingredients;
        thisRecipe.rating = ratings;
        thisRecipe.instructions = instructions;
        return thisRecipe;
    }

    // MODIFIES: this
    // EFFECTS: removes recipe name, adds ingredients until the next string can be parsed into an int
    private static LinkedList helpGetIngredients(List<String> components) {
        components.remove(0);
        LinkedList ingredientsList = new LinkedList();
        int i = 0;
        String ingredient = components.get(i);
        while (!canParseInt(ingredient)) {
            ingredientsList.add(ingredient);
            i++;
            ingredient = components.get(i);
        }
        return ingredientsList;
    }

    // EFFECTS: checks if given string can be parsed into int
    private static boolean canParseInt(String entry) {
        try {
            Integer.parseInt(entry);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds instructions from back of list until next string can be parsed into an int
    private static LinkedList helpGetInstructions(List<String> components) {
        LinkedList instructionsList = new LinkedList();
        boolean keepGoing = true;
        while (keepGoing) {
            if (!canParseInt(components.get(components.size() - 1))) {
                instructionsList.addFirst(components.get(components.size() - 1));
                components.remove(components.size() - 1);
            } else {
                keepGoing = false;
            }
        }
        return instructionsList;
    }
}