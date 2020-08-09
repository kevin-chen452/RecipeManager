package ui;

import exceptions.EmptyRecipeListException;
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
public class RecipeManagerGUI implements ActionListener {
    private JFrame frame;
    private JLabel welcomeText;
    private JLabel activity;
    private JButton saveButton;
    private JButton loadButton;
    private JButton addRecipeButton;
    private JButton manageRecipeButton;
    private JPanel homePanel;
    private JTextField recipeName;
    private Collection collection = new Collection();
    private static final String RECIPES_GUIFILE = "./data/UIrecipes.txt";
    private static final String addRecipeString = "Add recipe";
    private JButton recipeButton;
    private RecipeListener recipeListener;

    // EFFECTS: sets up window in which Recipe Manager application will be played
    public RecipeManagerGUI() {
        init();
        manageButtons();
        managePanel();
        manageFrame();
    }

    // EFFECTS: instantiates required fields
    public void init() {
        saveButton = new JButton("Save recipes");
        loadButton = new JButton("Load recipes");
        addRecipeButton = new JButton("Add recipe");
        manageRecipeButton = new JButton("Manage recipes");
        homePanel = new JPanel(new BorderLayout());
        frame = new JFrame();
        recipeName = new JTextField(10);
        welcomeText = new JLabel("Welcome to Recipe Manager!", SwingConstants.CENTER);
        activity = new JLabel("", SwingConstants.CENTER);
        recipeButton = new JButton(addRecipeString);
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
        recipeButton.setActionCommand(addRecipeString);
        recipeButton.addActionListener(recipeListener);
        recipeButton.setEnabled(false);
    }

    // MODIFIES: this
    // EFFECTS: sets up panel
    public void managePanel() {
        homePanel.setLayout(new GridLayout(0, 1));
        homePanel.add(welcomeText);
        homePanel.add(activity);
        homePanel.add(saveButton);
        homePanel.add(loadButton);
        homePanel.add(addRecipeButton);
        homePanel.add(manageRecipeButton);
    }

    // MODIFIES: this
    // EFFECTS: sets up frame
    public void manageFrame() {
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
    // EFFECTS: changes action text depending on action performed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            saveRecipes();
            playSound("cheeringKidsSoundEffect.wav");
        } else if (e.getSource() == loadButton) {
            loadRecipes();
            activity.setText("Your recipes have been loaded!");
            playSound("cheeringKidsSoundEffect.wav");
        } else if (e.getSource() == addRecipeButton) {
            helpAddRecipe();
        } else if (e.getSource() == manageRecipeButton) {
            try {
                activity.setText("List of recipes:" + collection.getRecipeList());
            } catch (EmptyRecipeListException ex) {
                activity.setText("Sorry, there are no recipes in the list right now.");
            }
        }
    }

    // source: http://suavesnippets.blogspot.com/2011/06/add-sound-on-jbutton-click-in-java.html
    // EFFECTS: plays sound
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

    // MODIFIES: this
    // EFFECTS: helps conduct the adding of a new recipe
    private void helpAddRecipe() {
        activity.setText("Input recipe name: ");
        recipeName.addActionListener(recipeListener);
        recipeName.getDocument().addDocumentListener(recipeListener);
        homePanel.add(recipeName);
        homePanel.add(recipeButton);
        String input = recipeName.getText();
        Recipe recipe = new Recipe(input);
        collection.addRecipe(recipe);
        activity.setText("Successfully added recipe " + input + "!");
        // playSound("cheeringKidsSoundEffect.wav");
    }


    // source: ListDemo.java
    //This listener is shared by the text field and the hire button.
    class RecipeListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public RecipeListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = recipeName.getText();
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                recipeName.requestFocusInWindow();
                recipeName.selectAll();
                return;
            }
            //Reset the text field.
            recipeName.requestFocusInWindow();
            recipeName.setText("");
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return name.equals(collection.getRecipe(name));
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
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
                activity.setText("Recipe" + collection.recipeList.size()
                        + " saved to file " + RECIPES_GUIFILE);
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
        } catch (IOException e) {
            // do nothing
        }
    }

    /*
     * Start the recipe manager
     */
    public static void main(String[] args) {
        new RecipeManagerGUI();
    }
}



