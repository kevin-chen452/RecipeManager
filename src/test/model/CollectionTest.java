package model;

import exceptions.EmptyRecipeListException;
import exceptions.NoRecipeFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionTest {
    public Collection testCollection = new Collection();
    public Recipe testRecipe1;
    public Recipe testRecipe2;
    public Recipe testRecipe3;
    public Recipe testRecipe4;

    @BeforeEach
    public void runBefore() {
        testCollection = new Collection();
        testRecipe1 = new Recipe("Soju Ice Cream");
        testRecipe2 = new Recipe("In-n-Out Burger");
        testRecipe3 = new Recipe("Injeolmi Bingsu");
        testRecipe4 = new Recipe("Orchard Commons Fried Chicken");
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
        } catch (NoRecipeFoundException e) {
        }
    }

    @Test
    void testRemoveRecipeFailWrongRecipe() {
        testCollection.addRecipe(testRecipe1);
        try {
            testCollection.removeRecipe(testRecipe2.getName());
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
}
