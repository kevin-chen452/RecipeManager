package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecipeManagerGUI implements ActionListener {
    JFrame frame;
    String actionText;
    JLabel welcomeText;
    JLabel activity;
    JButton saveButton;
    JButton loadButton;
    JButton addRecipeButton;
    JPanel panel;

    // EFFECTS: sets up window in which Recipe Manager application will be played
    public RecipeManagerGUI() {
        saveButton = new JButton("Save recipes");
        loadButton = new JButton("Load recipes");
        addRecipeButton = new JButton("Add recipe");
        panel = new JPanel();
        frame = new JFrame();
        manageButtons();
        manageLabel();
        managePanel();
        manageFrame();
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
    }

    // MODIFIES: this
    // EFFECTS: sets up labels
    public void manageLabel() {
        welcomeText = new JLabel("Welcome to Recipe Manager!");
        activity = new JLabel(actionText);
    }

    // MODIFIES: this
    // EFFECTS: sets up panel
    public void managePanel() {
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 200, 50));
        panel.setLayout(new GridLayout(1, 1));
        panel.add(saveButton);
        panel.add(loadButton);
        panel.add(addRecipeButton);
        panel.add(welcomeText);
        panel.add(activity);
    }

    // MODIFIES: this
    // EFFECTS: sets up frame
    public void manageFrame() {
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(100, 100);
        frame.setTitle("Recipe Manager");
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: changes action text depending on action performed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("saveButton")) {
            actionText = "Your recipes have been saved!";
        } else if (e.getActionCommand().equals("loadButton")) {
            actionText = "Your recipes have been loaded!";
        } else {
            actionText = "Input recipe name:";
        }
    }

    /*
     * Start the recipe manager
     */
    public static void main(String[] args) {
        new RecipeManagerGUI();
    }
}



