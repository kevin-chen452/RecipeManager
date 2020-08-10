package ui;

import exceptions.EmptyRecipeListException;
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
import java.util.Arrays;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// source: Lab 1 - Photoviewer
// source: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial
// /uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
public class RecipeManagerGUI implements ActionListener {
    private JFrame frame;
    private JLabel welcomeText;
    private JLabel removeText;
    private JLabel activity;
    private JList recipesList;
    private JButton saveButton;
    private JButton loadButton;
    private JButton addRecipeButton;
    private JButton manageRecipeButton;
    private JPanel homePanel;
    private JPanel removeRecipePanel;
    private JPanel buttonsRemovePanel;
    private JTextField recipeNameField;
    private Collection collection = new Collection();
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
    }

    // EFFECTS: instantiates required fields
    public void init() {
        saveButton = new JButton("Save recipes");
        loadButton = new JButton("Load recipes");
        addRecipeButton = new JButton("Add recipes");
        manageRecipeButton = new JButton("Manage recipes");
        recipesList = new JList();
        homePanel = new JPanel(new BorderLayout());
        removeRecipePanel = new JPanel(new BorderLayout());
        buttonsRemovePanel = new JPanel(new BorderLayout());
        frame = new JFrame();
        recipeNameField = new JTextField(10);
        welcomeText = new JLabel("Welcome to Recipe Manager!", SwingConstants.CENTER);
        activity = new JLabel(emptyString, SwingConstants.CENTER);
        removeText = new JLabel("Click on a recipe to remove it.");
        recipeButton = new JButton("Add recipes");
        recipeListener = new RecipeListener(recipeButton);
        mainMenuButton = new JButton("Return to main menu");
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
    // source: https://www.geeksforgeeks.org/how-to-convert-linkedlist-to-array-in-java/
    // source: https://stackoverflow.com/questions/14625091/create-a-list-of-entries-and-make-each-entry-clickable
    // MODIFIES: this
    // EFFECTS: sets up removeRecipePanel
    public void manageRemovePanel() {
        removeRecipePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
         /*
        recipesList.setModel(new AbstractListModel() {
            Object[] recipesObjectArray = collection.recipeList.toArray();
            String[] recipes = Arrays.copyOf(recipesObjectArray, recipesObjectArray.length, String[].class);
            @Override
            public int getSize() {
                return collection.recipeList.size();
            }

            @Override
            public Object getElementAt(int i) {
                return recipes[i];
            }
        });
        recipesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                recipesListValueChanged(e);
            }
        });
          */
        removeRecipePanel.add(removeText);
        removeRecipePanel.add(recipesList);
        manageButtonsPanel();
        removeRecipePanel.add(buttonsRemovePanel);
    }

    // MODIFIES: this
    // EFFECTS: removes recipe
    private void recipesListValueChanged(javax.swing.event.ListSelectionEvent evt) {
        //set text on right here
        String s = (String) recipesList.getSelectedValue();
        if (s.equals(collection.getRecipe(s))) {
            try {
                collection.removeRecipe(s);
            } catch (NoRecipeFoundException e) {
                //do nothing
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up non-grid components of remove recipe panel
    public void manageButtonsPanel() {
        buttonsRemovePanel.setLayout(new GridLayout(10, 10));
        buttonsRemovePanel.add(mainMenuButton, SwingConstants.CENTER);
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
                setUpManageRecipes();
            } else {
                activity.setText("Sorry, there are no recipes in the list right now.");
            }
        } else if (e.getSource() == mainMenuButton) {
            goToMainMenu();
        }
    }

    // EFFECTS: creates manage recipes GUI
    public void setUpManageRecipes() {
        homePanel.setVisible(false);
        frame.setContentPane(removeRecipePanel);
        removeRecipePanel.setVisible(true);
    }

    // EFFECTS: goes back to main menu GUI
    public void goToMainMenu() {
        removeRecipePanel.setVisible(false);
        activity.setText(emptyString);
        homePanel.remove(recipeNameField);
        homePanel.remove(recipeButton);
        frame.setContentPane(homePanel);
        homePanel.setVisible(true);
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
        activity.setText("Input recipe name.");
        recipeNameField.getDocument().addDocumentListener(recipeListener);
        recipeNameField.addActionListener(recipeListener);
        homePanel.add(recipeNameField);
        homePanel.add(recipeButton);
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
            String input = recipeNameField.getText();
            if (alreadyInList(input) || input.equals("")) {
                activity.setText("Sorry, that name is already in use.");
                //Toolkit.getDefaultToolkit().beep();
                recipeNameField.requestFocusInWindow();
                recipeNameField.selectAll();
            } else {
                Recipe recipe = new Recipe(input);
                collection.addRecipe(recipe);
                activity.setText("Successfully added recipe " + input + "!");
                //playSound("cheeringKidsSoundEffect.wav");
            }
            //Reset the text field.
            recipeNameField.requestFocusInWindow();
            recipeNameField.setText("");
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return collection.getRecipe(name) != null;
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
                activity.setText("Recipes saved to file " + RECIPES_GUIFILE);
                //playSound("cheeringKidsSoundEffect.wav");
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
            activity.setText("Your recipes have been loaded!");
            //playSound("cheeringKidsSoundEffect.wav");
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



