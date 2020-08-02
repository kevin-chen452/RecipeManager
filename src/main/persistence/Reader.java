package persistence;

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
    public static final String DELIMITER = ",";

    // EFFECTS: returns a list of recipes parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static List<Recipe> readRecipes(File file) throws IOException {
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
    private static List<Recipe> parseContent(List<String> fileContent) {
        List<Recipe> recipes = new ArrayList<>();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            recipes.add(parseRecipe(lineComponents));
        }

        return recipes;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 4 where element 0 represents the
    // name of the next recipe to be constructed, element 1 represents
    // the cooking time, elements 2 represents the list of ingredients, element 3 represents the rating, and
    // element 4 represents instructions
    // EFFECTS: returns a recipe constructed from components
    private static Recipe parseRecipe(List<String> components) {
        String recipeName = components.get(0);
        String ingredients = components.get(1);
        int cookingTimes = Integer.parseInt(components.get(2));
        int ratings = Integer.parseInt(components.get(3));
        String instructions = components.get(4);
        Recipe thisRecipe = new Recipe(recipeName);
        thisRecipe.cookingTime = cookingTimes;
        thisRecipe.ingredients = (LinkedList) ingredients;
        thisRecipe.rating = ratings;
        thisRecipe.instructions = instructions;
        return thisRecipe;
    }
}