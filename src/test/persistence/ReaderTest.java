package persistence;

import model.Collection;
import model.Recipe;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {
    @Test
    void testParseRecipeFile1() {
        try {
            Collection recipes = Reader.readRecipes(new File("./data/testRecipeFile1.txt"));
            Recipe recipe = recipes.get(0);
            LinkedList ingredients = new LinkedList();
            LinkedList instructions = new LinkedList();
            ingredients.add("kimchi");
            ingredients.add("rice");
            ingredients.add("salt");
            ingredients.add("pepper");
            ingredients.add("cooking oil");
            ingredients.add("garlic");
            ingredients.add("green onions");
            ingredients.add("beef");
            instructions.add("preheat a stove");
            instructions.add("then preheat a pan and add a teaspoon of cooking oil");
            instructions.add("minced garlic and stir for about 10 seconds");
            assertEquals("kimchi fried rice", recipe.getName());
            assertEquals(ingredients, recipe.getIngredientList());
            assertEquals(30, recipe.getCookingTime());
            assertEquals(5, recipe.getRating());
            assertEquals(instructions, recipe.getInstructions());
        } catch (IOException e) {
            fail("Should not have been thrown.");
        }
    }

    @Test
    void testParseRecipeFile2() {
        try {
            Collection recipes = Reader.readRecipes(new File("./data/testRecipeFile2.txt"));
            Recipe instantNoodles = recipes.get(0);
            Recipe rice = recipes.get(1);
            LinkedList noodsIngredients = new LinkedList();
            LinkedList noodsInstructions = new LinkedList();
            noodsIngredients.add("instant noodles");
            noodsIngredients.add("boiling water");
            noodsInstructions.add("remove lid");
            noodsInstructions.add("add sauce powders");
            noodsInstructions.add("add boiling water");
            LinkedList riceIngredients = new LinkedList();
            LinkedList riceInstructions = new LinkedList();
            riceIngredients.add("rice");
            riceIngredients.add("rice cooker");
            riceInstructions.add("rinse rice");
            riceInstructions.add("add water to approximate level");
            riceInstructions.add("place in rice cooker");
            riceInstructions.add("select \"cook\"");
            assertEquals("instant noodles", instantNoodles.getName());
            assertEquals(noodsIngredients, instantNoodles.getIngredientList());
            assertEquals(5, instantNoodles.getCookingTime());
            assertEquals(4, instantNoodles.getRating());
            assertEquals(noodsInstructions, instantNoodles.getInstructions());
            assertEquals("rice", rice.getName());
            assertEquals(riceIngredients, rice.getIngredientList());
            assertEquals(45, rice.getCookingTime());
            assertEquals(2, rice.getRating());
            assertEquals(riceInstructions, rice.getInstructions());
        } catch (IOException e) {
            fail("Should not have been thrown.");
        }
    }

    @Test
    void testIOException() {
        try {
            Reader.readRecipes(new File("./nonexistent/path.txt"));
        } catch (IOException E) {
        }
    }

    @Test
    // dummy test for Reader, so no assert statements present
    void testReader() {
        Reader testReader = new Reader();
    }
}
