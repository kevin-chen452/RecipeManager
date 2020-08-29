package model;

import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionTest {
    public Collection testCollection = new Collection();
    public Recipe testRecipe1;
    public Recipe testRecipe2;
    public Recipe testRecipe3;
    public Recipe testRecipe4;
    private static final String TEST_FILE = "./data/testCollection.txt";
    private PrintWriter testWriter;

    @BeforeEach
    public void runBefore() throws FileNotFoundException {
        testCollection = new Collection();
        testRecipe1 = new Recipe("Soju Ice Cream");
        testRecipe2 = new Recipe("In-n-Out Burger");
        testRecipe3 = new Recipe("Injeolmi Bingsu");
        testRecipe4 = new Recipe("Orchard Commons Fried Chicken");
        testWriter = new PrintWriter(new File(TEST_FILE));
    }

    @Test
    void testGetRecipeSuccess() {
        try {
            testCollection.addRecipe(testRecipe1);
            assertEquals(testRecipe1, testCollection.get(0));
        } catch (IndexOutOfBoundsException e) {
            fail("IndexOutOfBounds is not supposed to be thrown.");
        }
    }

    @Test
    void testGetRecipeNull() {
        try {
            testCollection.get(0);
            fail("Should have been thrown.");
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Test
    void testGetRecipeOutOfBoundsFail() {
        try {
            testCollection.addRecipe(testRecipe1);
            testCollection.get(10);
            fail("Should have been thrown.");
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Test
    void testAddRecipe() {
        testCollection.addRecipe(testRecipe1);
        assertTrue(testCollection.recipeList.contains(testRecipe1));
    }

    @Test
    void testAddMultipleRecipe() {
        testCollection.addRecipe(testRecipe1);
        testCollection.addRecipe(testRecipe2);
        testCollection.addRecipe(testRecipe4);
        assertTrue(testCollection.recipeList.contains(testRecipe1));
        assertTrue(testCollection.recipeList.contains(testRecipe2));
        assertFalse(testCollection.recipeList.contains(testRecipe3));
        assertTrue(testCollection.recipeList.contains(testRecipe4));
    }

    @Test
    void testGetEmptyRecipeList() {
        try {
            testCollection.getRecipeList();
            fail("Should have been thrown.");
        } catch (EmptyRecipeListException e) {
        }
    }

    @Test
    void testGetRecipeListSuccess() throws EmptyRecipeListException {
        testCollection.addRecipe(testRecipe1);
        assertEquals("Soju Ice Cream", testCollection.getRecipeList());
    }

    @Test
    void testGetBiggerRecipeListSuccess() throws EmptyRecipeListException {
        testCollection.addRecipe(testRecipe1);
        testCollection.addRecipe(testRecipe2);
        testCollection.addRecipe(testRecipe3);
        testCollection.addRecipe(testRecipe4);
        assertEquals("Soju Ice Cream, In-n-Out Burger, Injeolmi Bingsu, Orchard Commons Fried Chicken",
                testCollection.getRecipeList());
    }

    @Test
    void testRemoveRecipeFailEmpty() {
        try {
            testCollection.removeRecipe(testRecipe1.getName());
            fail("Should have been thrown.");
        } catch (NoRecipeFoundException e) {
        }
    }

    @Test
    void testRemoveRecipeFailWrongRecipe() {
        testCollection.addRecipe(testRecipe1);
        try {
            testCollection.removeRecipe(testRecipe2.getName());
            fail("Should have been thrown.");
        } catch (NoRecipeFoundException e) {
        }
    }

    @Test
    void testRemoveRecipeFailWrongRecipes() {
        testCollection.addRecipe(testRecipe1);
        testCollection.addRecipe(testRecipe2);
        testCollection.addRecipe(testRecipe4);
        try {
            testCollection.removeRecipe(testRecipe3.getName());
            fail("Should have been thrown.");
        } catch (NoRecipeFoundException e) {
        }
    }

    @Test
    void testRemoveOneRecipeSuccess() {
        testCollection.addRecipe(testRecipe3);
        try {
            testCollection.removeRecipe(testRecipe3.getName());
        } catch (NoRecipeFoundException e) {
            fail();
        }
    }

    @Test
    void testRemoveManyRecipeSuccess() {
        testCollection.addRecipe(testRecipe3);
        testCollection.addRecipe(testRecipe1);
        testCollection.addRecipe(testRecipe2);
        testCollection.addRecipe(testRecipe4);
        testCollection.addRecipe(testRecipe4);
        testCollection.addRecipe(testRecipe4);
        try {
            testCollection.removeRecipe(testRecipe3.getName());
            testCollection.removeRecipe(testRecipe2.getName());
            testCollection.removeRecipe(testRecipe4.getName());
        } catch (NoRecipeFoundException e) {
            fail();
        }
    }

    @Test
    void testGetEmptyRecipeFail() {
        assertEquals(null, testCollection.getRecipe(""));
    }

    @Test
    void testGetNonEmptyRecipeFail() {
        testCollection.addRecipe(testRecipe1);
        assertEquals(null, testCollection.getRecipe(""));
    }

    @Test
    void testGetRecipeFail() {
        testCollection.addRecipe(testRecipe1);
        assertEquals(null, testCollection.getRecipe("In-n-Out Burger"));
    }

    @Test
    void testGetNonEmptyRecipeSuccess() {
        testCollection.addRecipe(testRecipe1);
        assertEquals(testRecipe1, testCollection.getRecipe("Soju Ice Cream"));
    }

    @Test
    void testGetLongRecipeSuccess() {
        testCollection.addRecipe(testRecipe1);
        testCollection.addRecipe(testRecipe2);
        testCollection.addRecipe(testRecipe3);
        assertEquals(testRecipe2, testCollection.getRecipe("In-n-Out Burger"));
    }

    @Test
    void testGetLongRecipeFail() {
        testCollection.addRecipe(testRecipe1);
        testCollection.addRecipe(testRecipe2);
        testCollection.addRecipe(testRecipe3);
        assertEquals(null, testCollection.getRecipe("Orchard Commons Fried Chicken"));
    }

    @Test
    void testSaveSuccessNoExceptions() {
        try {
            testRecipe1.addIngredient("test ingredient");
            testRecipe1.setCookingTime(1);
            testRecipe1.setRating(2);
            testRecipe1.addInstructions("run the test and pray for the best");
            testCollection.addRecipe(testRecipe1);
            testCollection.save(testWriter);
            testWriter.close();
        } catch (EmptyIngredientException e) {
            fail("EmptyIngredientException should not be thrown.");
        } catch (IllegalTimeException e) {
            fail("IllegalTimeException should not be thrown.");
        } catch (IllegalRateException e) {
            fail("IllegalRateException should not be thrown.");
        } catch (EmptyInstructionsException e) {
            fail("EmptyInstructionsException should not be thrown.");
        }
    }
}
