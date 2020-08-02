package model;

import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeTest {
    protected Recipe testRecipe;

    @BeforeEach
    public void runBefore() {
        testRecipe = new Recipe("Kimchi Fried Rice");
    }

    @Test
    void testConstructor() {
        assertEquals("Kimchi Fried Rice", testRecipe.getName());
    }

    @Test
    void testGetIngredientListOneItem() throws EmptyIngredientException {
        testRecipe.addIngredient("rice");
        assertEquals(testRecipe.ingredients, testRecipe.getIngredientList());
    }

    @Test
    void testAddInstructionsSuccess() {
        try {
            testRecipe.addInstructions("first, collect all your ingredients");
            testRecipe.addInstructions("then, preheat a pan and add a teaspoon of cooking oil");
            testRecipe.addInstructions("now, minced garlic and stir for about 10 seconds");
            testRecipe.addInstructions("add some green onions and let them fry for about 20 seconds");
            testRecipe.addInstructions("add kimchi and stir until about 80% is cooked");
            testRecipe.addInstructions("add rice and kimchi juice and mix!");
            testRecipe.addInstructions("and some sesame oil anx mix some more, then turn off the heat");
            testRecipe.addInstructions("optional: add sesame seeds and more green onions as garnish");
            testRecipe.addInstructions("enjoy!!");
        } catch (EmptyInstructionsException e) {
            fail();
        }
    }

    @Test
    void testAddIngredientsFail() {
        try {
            testRecipe.addInstructions("");
        } catch (EmptyInstructionsException e) {
        }
    }

    @Test
    void testGetFirstIngredientFail() {
        try {
            testRecipe.getStep();
        } catch (NoSuchElementException e) {
        }
    }

    @Test
    void testGetFirstIngredientSuccess() {
        try {
            testRecipe.addInstructions("first, gather all your ingredients");
            testRecipe.addInstructions("then, preheat a pan and add a teaspoon of cooking oil");
            testRecipe.addInstructions("now, minced garlic and stir for about 10 seconds");
            assertEquals("first, gather all your ingredients", testRecipe.getStep());
        } catch (EmptyInstructionsException e) {
        }
    }

    @Test
    void testGetIngredientListManyItems() throws EmptyIngredientException {
        testRecipe.addIngredient("rice");
        testRecipe.addIngredient("kimchi");
        testRecipe.addIngredient("salt & pepper");
        testRecipe.addIngredient("two eggs");
        testRecipe.addIngredient("cooking oil");
        testRecipe.addIngredient("garlic");
        testRecipe.addIngredient("green onion");
        testRecipe.addIngredient("soy sauce");
        assertEquals(testRecipe.ingredients, testRecipe.getIngredientList());
    }

    @Test
    void testAddIngredientSuccess() {
        assertFalse(testRecipe.ingredients.contains("rice"));
        try {
            testRecipe.addIngredient("rice");
        } catch (EmptyIngredientException e) {
            fail();
        }
        assertTrue(testRecipe.ingredients.contains("rice"));
    }

    @Test
    void testAddIngredientFail() {
        assertFalse(testRecipe.ingredients.contains("rice"));
        try {
            testRecipe.addIngredient("");
        } catch (EmptyIngredientException e) {
        }
        assertFalse(testRecipe.ingredients.contains("rice"));
    }

    @Test
    void testRemoveIngredientSuccess() {
        try {
            testRecipe.addIngredient("rice");
        } catch (EmptyIngredientException e) {
            fail();
        }
        try {
            testRecipe.removeIngredient("rice");
        } catch (EmptyIngredientException e) {
            fail();
        } catch (NoIngredientException e) {
            fail();
        }
        assertFalse(testRecipe.ingredients.contains("rice"));
    }

    @Test
    void testRemoveMultipleIngredientSuccess() {
        try {
            testRecipe.addIngredient("rice");
            testRecipe.addIngredient("kimchi");
            testRecipe.addIngredient("salt & pepper");
            testRecipe.addIngredient("green onion");
            testRecipe.addIngredient("soy sauce");
        } catch (EmptyIngredientException e) {
            fail();
        }
        try {
            testRecipe.removeIngredient("rice");
            testRecipe.removeIngredient("salt & pepper");
        } catch (EmptyIngredientException e) {
            fail();
        } catch (NoIngredientException e) {
            fail();
        }
        assertFalse(testRecipe.ingredients.contains("rice"));
    }

    @Test
    void testRemoveIngredientFailNoIngredient() {
        try {
            testRecipe.removeIngredient("rice");
        } catch (NoIngredientException e) {
        } catch (EmptyIngredientException e) {
            fail();
        }
        assertFalse(testRecipe.ingredients.contains("rice"));
    }

    @Test
    void testRemoveIngredientFailEmptyIngredient() {
        try {
            testRecipe.removeIngredient("");
        } catch (NoIngredientException e) {
            fail();
        } catch (EmptyIngredientException e) {
        }
        assertFalse(testRecipe.ingredients.contains("rice"));
    }

    @Test
    void testSetCookingTimeSuccess() {
        try {
            testRecipe.setCookingTime(5);
        } catch (IllegalTimeException e) {
            fail();
        }
        assertEquals(5, testRecipe.getCookingTime());
    }

    @Test
    void testSetCookingTimeSuccessBorder() {
        try {
            testRecipe.setCookingTime(1);
        } catch (IllegalTimeException e) {
            fail();
        }
        assertEquals(1, testRecipe.getCookingTime());
    }

    @Test
    void testSetCookingTimeFail() {
        try {
            testRecipe.setCookingTime(0);
        } catch (IllegalTimeException e) {
        }
    }

    @Test
    void testSetRatingSuccessLower() {
        try {
            testRecipe.setRating(1);
        } catch (IllegalRateException e) {
            fail();
        }
        assertEquals(1, testRecipe.getRating());
    }

    @Test
    void testSetRatingSuccessUpper() {
        try {
            testRecipe.setRating(5);
        } catch (IllegalRateException e) {
            fail();
        }
        assertEquals(5, testRecipe.getRating());
    }

    @Test
    void testSetRatingFailUpper() {
        try {
            testRecipe.setRating(6);
        } catch (IllegalRateException e) {
        }
    }

    @Test
    void testSetRatingFailLower() {
        try {
            testRecipe.setRating(0);
        } catch (IllegalRateException e) {
        }
    }
}
