package ui;

import exceptions.NoRecipeFoundException;
import model.Collection;
import model.Recipe;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

// source: Lab 1 - Photoviewer
// source: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial
// /uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
// represents the recipe manager GUI
public class RecipeManagerGUI implements ActionListener {
    private JFrame frame;
    private JLabel welcomeText;
    private JLabel removeText;
    private JLabel activity;
    private JLabel removeActivity;
    private JList<Recipe> recipeList;
    private DefaultListModel recipeModel;
    private JButton saveButton;
    private JButton loadButton;
    private JButton addRecipeButton;
    private JButton manageRecipeButton;
    private JButton removeRecipeButton;
    private JPanel homePanel;
    private JPanel manageRecipePanel;
    private JTextField recipeNameField;
    private Collection collection;
    private static final String RECIPES_GUIFILE = "./data/UIrecipes.txt";
    private static final String emptyString = "";
    private JButton recipeButton;
    private JButton mainMenuButton;
    private RecipeListener recipeListener;

    // EFFECTS: sets up window in which Recipe Manager application will be played
    public RecipeManagerGUI() {
        init();
        manageButtons();
        managePanel();
        manageFrame();
        manageField();
        recipeList.setBounds(100, 100, 75, 75);
    }

    // EFFECTS: instantiates required fields
    public void init() {
        saveButton = new JButton("Save recipes");
        loadButton = new JButton("Load recipes");
        addRecipeButton = new JButton("Add recipes");
        manageRecipeButton = new JButton("Manage recipes");
        recipeModel = new DefaultListModel();
        recipeList = new JList<>(recipeModel);
        homePanel = new JPanel(new BorderLayout());
        collection = new Collection();
        manageRecipePanel = new JPanel(new BorderLayout());
        removeRecipeButton = new JButton("Remove");
        frame = new JFrame();
        recipeNameField = new JTextField(10);
        welcomeText = new JLabel("Welcome to Recipe Manager!", SwingConstants.CENTER);
        activity = new JLabel(emptyString, SwingConstants.CENTER);
        removeActivity = new JLabel(emptyString, SwingConstants.CENTER);
        removeText = new JLabel("Click on a recipe to remove it.", SwingConstants.CENTER);
        recipeButton = new JButton("Add");
        mainMenuButton = new JButton("Return to main menu");
        recipeListener = new RecipeListener(recipeButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up buttons
    public void manageButtons() {
        saveButton.setActionCommand("saveButton");
        saveButton.addActionListener(this);
        loadButton.setActionCommand("loadButton");
        loadButton.addActionListener(this);
        addRecipeButton.setActionCommand("addRecipeButton");
        addRecipeButton.addActionListener(this);
        manageRecipeButton.setActionCommand("manageRecipeButton");
        manageRecipeButton.addActionListener(this);
        recipeButton.setActionCommand("Add recipes");
        recipeButton.addActionListener(recipeListener);
        recipeButton.setEnabled(false);
        mainMenuButton.addActionListener(this);
        mainMenuButton.setBounds(-400, -20, 100, 20);
        removeRecipeButton.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: sets up panel
    public void managePanel() {
        manageHomePanel();
        manageRemovePanel();
    }

    // MODIFIES: this
    // EFFECTS: sets up homePanel
    public void manageHomePanel() {
        homePanel.setLayout(new GridLayout(0, 1));
        homePanel.add(welcomeText);
        homePanel.add(activity);
        homePanel.add(saveButton);
        homePanel.add(loadButton);
        homePanel.add(addRecipeButton);
        homePanel.add(manageRecipeButton);
    }

    // source: https://stackoverflow.com/questions/14046837/positioning-components-in-swing-guis
    // source: https://stackoverflow.com/questions/14625091/create-a-list-of-entries-and-make-each-entry-clickable
    // MODIFIES: this
    // EFFECTS: sets up manageRecipePanel
    public void manageRemovePanel() {
        manageRecipePanel.setLayout(new GridLayout(5, 1));
        manageRecipePanel.add(removeText);
        manageRecipePanel.add(removeActivity);
        manageRecipePanel.add(recipeList);
        manageRecipePanel.add(removeRecipeButton);
        manageRecipePanel.add(mainMenuButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up frame
    public void manageFrame() {
        frame.pack();
        frame.setContentPane(homePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setTitle("Recipe Manager");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width / 2 - frame.getWidth() / 2;
        int y = screenSize.height / 2 - frame.getHeight() / 2;
        frame.setLocation(x, y);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds document listeners to recipe name field
    public void manageField() {
        recipeNameField.getDocument().addDocumentListener(recipeListener);
        recipeNameField.addActionListener(recipeListener);
    }

    // MODIFIES: this
    // EFFECTS: goes to manage recipes GUI
    public void goToManageRecipes() {
        homePanel.setVisible(false);
        removeActivity.setText(emptyString);
        frame.setContentPane(manageRecipePanel);
        manageRecipePanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: goes back to main menu GUI
    public void goToMainMenu() {
        manageRecipePanel.setVisible(false);
        activity.setText(emptyString);
        homePanel.remove(recipeNameField);
        homePanel.remove(recipeButton);
        frame.setContentPane(homePanel);
        homePanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: changes action text and GUI depending on action performed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            saveRecipes();
        } else if (e.getSource() == loadButton) {
            loadRecipes();
        } else if (e.getSource() == addRecipeButton) {
            helpAddRecipe();
        } else if (e.getSource() == manageRecipeButton) {
            if (collection.recipeList.size() != 0) {
                goToManageRecipes();
            } else {
                activity.setText("Sorry, there are no recipes in the list right now.");
            }
        } else if (e.getSource() == mainMenuButton) {
            goToMainMenu();
        } else if (e.getSource() == removeRecipeButton) {
            try {
                tryRemoveRecipe();
            } catch (NoRecipeFoundException ex) {
                removeActivity.setText("Error with removing recipe.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: helps conduct the adding of a new recipe
    private void helpAddRecipe() {
        activity.setText("Input recipe name.");
        homePanel.add(recipeNameField);
        homePanel.add(recipeButton);
    }

    // MODIFIES: this
    // EFFECTS: check if recipe has been selected, remove if so
    private void tryRemoveRecipe() throws NoRecipeFoundException {
        try {
            int index = recipeList.getSelectedIndex();
            String name = recipeModel.getElementAt(index).toString();
            recipeModel.remove(index);
            collection.removeRecipe(name);
            removeActivity.setText("Recipe " + name + " has been successfully removed.");
            playSound("cheeringKidsSoundEffect.wav");
        } catch (IndexOutOfBoundsException e) {
            removeActivity.setText("Please select a recipe to remove.");
        }
    }

    // source: ListDemo.java
    //This listener is shared by the text field and the add recipe button
    // Checks for actions to add recipe
    public class RecipeListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        // EFFECTS: sets this button to be button
        public RecipeListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        // MODIFIES: this
        // EFFECTS: checks for inputs to recipe name field, creates and adds recipe if input detected
        public void actionPerformed(ActionEvent e) {
            String input = recipeNameField.getText();
            if (alreadyInList(input) || input.equals(emptyString)) {
                activity.setText("Sorry, that recipe name is invalid or already in use.");
                recipeNameField.requestFocusInWindow();
                recipeNameField.selectAll();
            } else {
                Recipe recipe = new Recipe(input);
                collection.addRecipe(recipe);
                recipeModel.addElement(input);
                activity.setText("Successfully added recipe " + input + "!");
                playSound("cheeringKidsSoundEffect.wav");
            }
            recipeNameField.requestFocusInWindow();
            recipeNameField.setText(emptyString);
        }

        // EFFECTS: ensures that recipe is not already in collection
        protected boolean alreadyInList(String name) {
            return collection.getRecipe(name) != null;
        }

        //Required by DocumentListener.
        // EFFECTS: enables button when text field contains text
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        // EFFECTS: updates text field when empty
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        // EFFECTS: makes button clickable if user has inputted something into text field
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // EFFECTS: makes button clickable
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: this
        // EFFECTS: makes button unenabled when text field is empty
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // source: http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
    // EFFECTS: plays yay sound
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("data/" + soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    // EFFECTS: saves state of collection to RECIPES_GUIFILE
    private void saveRecipes() {
        try {
            Writer writer = new Writer(new File(RECIPES_GUIFILE));
            writer.write(collection);
            writer.close();
            if (collection.recipeList.size() == 0) {
                activity.setText("Recipes saved to file... but you have no recipes "
                        + "to save, so the save file now contains no recipes.");
            } else {
                activity.setText("Recipes saved to file " + RECIPES_GUIFILE);
                playSound("cheeringKidsSoundEffect.wav");
            }
        } catch (FileNotFoundException e) {
            activity.setText("Unable to save recipes to " + RECIPES_GUIFILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    // MODIFIES: this
    // EFFECTS: loads recipes into collection from RECIPES_GUIFILE, if that file exists, otherwise do nothing
    private void loadRecipes() {
        try {
            collection = Reader.readRecipes(new File(RECIPES_GUIFILE));
            loadToRecipesList();
            activity.setText("Your recipes have been loaded!");
            playSound("cheeringKidsSoundEffect.wav");
        } catch (IOException e) {
            // do nothing
        }
    }

    // MODIFIES: this
    // EFFECTS: loads recipes to recipes list to be displayed on manage recipes
    private void loadToRecipesList() {
        for (Recipe recipe: collection.recipeList) {
            recipeModel.addElement(recipe.recipeName);
        }
    }
}



