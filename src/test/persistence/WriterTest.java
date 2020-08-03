package persistence;

import exceptions.EmptyIngredientException;
import exceptions.EmptyInstructionsException;
import exceptions.IllegalRateException;
import exceptions.IllegalTimeException;
import model.Collection;
import model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {
    private static final String TEST_FILE = "./data/testRecipes.txt";
    private Writer testWriter;
    private Collection collection;
    private Recipe testRecipe1;
    private Recipe testRecipe2;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException, EmptyIngredientException,
            IllegalTimeException, IllegalRateException, EmptyInstructionsException {
        testWriter = new Writer(new File(TEST_FILE));
        collection = new Collection();
        testRecipe1 = new Recipe("rice");
        testRecipe2 = new Recipe("instant noodles");
        collection.addRecipe(testRecipe1);
        testRecipe1.addIngredient("rice");
        testRecipe1.addIngredient("rice cooker");
        testRecipe1.addIngredient("water");
        testRecipe1.setCookingTime(45);
        testRecipe1.setRating(3);
        testRecipe1.addInstructions("rinse rice");
        testRecipe1.addInstructions("add water to approximate level");
        testRecipe1.addInstructions("place rice pot in rice cooker");
        testRecipe1.addInstructions("select \"cook\"");
        collection.addRecipe(testRecipe2);
        testRecipe2.addIngredient("instant noodles");
        testRecipe2.addIngredient("boiling water");
        testRecipe2.setCookingTime(6);
        testRecipe2.setRating(5);
        testRecipe2.addInstructions("remove lid");
        testRecipe2.addInstructions("open sauce packets and dump powder into instant noodles cup");
        testRecipe2.addInstructions("add boiling water");
    }

    @Test
    void testWriteRecipes() {
        // save recipes to file
        testWriter.write(collection);
        testWriter.close();
        // now read them back in and verify that the recipes have the expected fields
        try {
            Collection myRecipes = Reader.readRecipes(new File(TEST_FILE));
            Recipe rice = myRecipes.get(0);
            Recipe instantNoodles = myRecipes.get(1);
            assertEquals("rice", rice.getName());
            assertEquals("instant noodles", instantNoodles.getName());
            LinkedList riceIngredients = new LinkedList();
            riceIngredients.add("rice");
            riceIngredients.add("rice cooker");
            riceIngredients.add("water");
            LinkedList riceInstructions = new LinkedList();
            riceInstructions.add("rinse rice");
            riceInstructions.add("add water to approximate level");
            riceInstructions.add("place rice pot in rice cooker");
            riceInstructions.add("select \"cook\"");
            LinkedList noodsIngredients = new LinkedList();
            noodsIngredients.add("instant noodles");
            noodsIngredients.add("boiling water");
            LinkedList noodsInstructions = new LinkedList();
            noodsInstructions.add("remove lid");
            noodsInstructions.add("open sauce packets and dump powder into instant noodles cup");
            noodsInstructions.add("add boiling water");

            assertEquals(riceIngredients, testRecipe1.getIngredientList());
            assertEquals(noodsIngredients, testRecipe2.getIngredientList());

            assertEquals(45, testRecipe1.getCookingTime());
            assertEquals(6, testRecipe2.getCookingTime());

            assertEquals(3, testRecipe1.getRating());
            assertEquals(5, testRecipe2.getRating());

            assertEquals(riceInstructions, testRecipe1.getInstructions());
            assertEquals(noodsInstructions, testRecipe2.getInstructions());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        } catch (IndexOutOfBoundsException e ) {
            fail("IndexOutOfBoundsException should not have been thrown.");
        }
    }
}