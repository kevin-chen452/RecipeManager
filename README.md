# Recipe Manager

## Kevin Chen

This application will launch an application that allows the user to keep track of their recipes. They will be able to 
do things such as adding and removing recipes, viewing their current recipes, and much more. 

There are many audiences in mind, but generally. I think that these people will use it:
- people who enjoy cooking
- people who have friends or family that enjoy cooking
- **myself**

This project is of interest to me, because I personally fit all of the criteria mentioned above. In addition
to that, I think it will be really nice to have a recipe manager right here on my desktop. As well, by making this
application myself, this means I can customize it to my needs, and potentially add new features in the long term,
beyond these two months.

## User Stories

- As a user, I want to be able to add and remove recipes from my collection
- As a user, I want to be able to add the estimated preparation time for a recipe
- As a user, I want to be able to select a recipe and view it in detail
- As a user, I want to be able to rate a recipe on a scale of one to five stars
- As a user, I want to be able to save my current recipes to file
- As a user, i want to be able to optionally load my recipes from file when starting the program

# Instructions for Grader

- You can generate the first required event (add recipe) by clicking on "Add recipes", then typing a recipe name, and
 hitting the "Enter" key or pressing the "Add" button 
- You can generate the second required event (remove recipe) by clicking on "Manage recipes" when you have at least one
recipe in the system, and then clicking on a recipe in the list and pressing the "Remove" button
- You can trigger my audio component by adding a recipe, removing a recipe, saving recipes when you have at least one
recipe in the system, or loading recipes
- You can save the state of my application by pressing the "Save recipes" button
- You can reload the state of my application by pressing the "Load recipes" button

## Phase 4: Task 2

I chose to make a class robust. The class is the Recipe class, and the following methods have a robust design:
- addIngredient
- removeIngredient
- setCookingTime
- setRating
- addInstructions

## Phase 4: Task 3

There is too much coupling in my RecipeManager class. What happens is that many features, such as
doRemoveRecipe, doLocateRecipe, and doRecipeRating, all check that the size of recipeList is not 0 before continuing.
If it is equal to 0, it prints the same message "Sorry, there are no recipes in the list right now." I refactored it
to make that message a constant, called NO_RECIPES_MESSAGE.

Since many methods check that the size of recipeList is not 0 before continuing, I also made a new helper method
noRecipes that checks if recipeList size is 0. This reduces the coupling between all these methods, improves
readability, and allows me to reinforce the idea of single point of control.

In RecipeManager, I also have a message that says "Taking you back to the main menu now..." that is printed at the 
end of many features. I also made the message a constant, called MAIN_MENU_MESSAGE.

In RecipeManager, many features involve the user modifying some aspect of one recipe, such as its instructions, its
ingredients, its rating, etc. This involves the user typing the name of the recipe they want to do such modifications
to. However, if the user inputs a recipe name that is not in the system, it will print a statement saying it does not
exist. I made this message a constant, called RECIPE_DNE_MESSAGE.

I use the keyword "done" to allow the user to indicate that they wish to stop the process they are currently doing,
such as adding/removing ingredients and instructions. I made this string a constant called STOP_OPERATION_MESSAGE.

In RecipeManagerGUI, several of the methods play the (probably annoying) sound effect whenever an operation was 
successful. I refactored this sound name into a constant, called YAY_SOUND_EFFECT_NAME.

In RecipeManagerGUI, I made a helper method thisButtonPressed that reduces the high coupling in the actionPerformed
method and improves readability.

I did something similar in RecipeManager, where I made a helper method commandEqualTo. This method reduces the coupling
and repetition into a single method, so I can modify every single case that the user inputs by modifying only the 
helper method (single point of control). 


