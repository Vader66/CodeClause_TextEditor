import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultEditorKit;
import javax.swing.KeyStroke;
import java.awt.Color;

public class TextEditor extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JFileChooser fileChooser;

    private JMenu themeMenu;
    private Color backgroundColor;
    private Color foregroundColor;

    private static final String DARK_THEME = "Dark";
    private static final String LIGHT_THEME = "Light";
    private static final String MOONLIGHT_THEME = "Moonlight";

    public TextEditor() {
        // Set up the window
        super("Text Editor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Create the file menu
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        // Create the "New" menu item
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(this);
        fileMenu.add(newMenuItem);

        // Create the "Open" menu item
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(this);
        fileMenu.add(openMenuItem);

        // Create the "Save" menu item
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(this);
        fileMenu.add(saveMenuItem);

        // Create the "Exit" menu item
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(this);
        fileMenu.add(exitMenuItem);

        // Create the edit menu
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        // Create the "Cut" menu item
        JMenuItem cutMenuItem = new JMenuItem(new DefaultEditorKit.CutAction());
        cutMenuItem.setText("Cut");
     //   cutMenuItem.setAccelerator(KeyStroke.getKeyStroke("control X"));
        editMenu.add(cutMenuItem);

        // Create the "Copy" menu item
        JMenuItem copyMenuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
        copyMenuItem.setText("Copy");
    //    copyMenuItem.setAccelerator(KeyStroke.getKeyStroke("control C"));
        editMenu.add(copyMenuItem);

        // Create the "Paste" menu item
        JMenuItem pasteMenuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
        pasteMenuItem.setText("Paste");
      //  pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke("control V"));
        editMenu.add(pasteMenuItem);

        // Create the theme menu
        themeMenu = new JMenu("Theme");
        menuBar.add(themeMenu);

        // Create the "Dark" theme menu item
        JMenuItem darkMenuItem = new JMenuItem("Dark");
        darkMenuItem.addActionListener(this);
        themeMenu.add(darkMenuItem);

        // Create the "Light" theme menu item
        JMenuItem lightMenuItem = new JMenuItem("Light");
        lightMenuItem.addActionListener(this);
        themeMenu.add(lightMenuItem);

        // Create the "Moonlight" theme menu item
        JMenuItem moonlightMenuItem = new JMenuItem("Moonlight");
        moonlightMenuItem.addActionListener(this);
        themeMenu.add(moonlightMenuItem);


        // Create the text area
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Create the file chooser
        fileChooser = new JFileChooser();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        if (actionCommand.equals("New")) {
            // Clear the text area
            textArea.setText("");
        } else if (actionCommand.equals("Open")) {
            // Show the open file dialog
            int returnValue = fileChooser.showOpenDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                // Read the selected file and display its contents in the text area
                File selectedFile = fileChooser.getSelectedFile();

                try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                    }

                    textArea.setText(sb.toString());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (actionCommand.equals("Save")) {
            // Show the save file dialog
            int returnValue = fileChooser.showSaveDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                try (FileWriter fw = new FileWriter(selectedFile)) {
                    fw.write(textArea.getText());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error writing file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (actionCommand.equals("Exit")) {
            // Ask the user if they are sure they want to exit
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                // Exit the program
                System.exit(0);
            }
        } else if (actionCommand.equals(DARK_THEME)) {
            // Set the dark theme
            backgroundColor = Color.DARK_GRAY;
            foregroundColor = Color.WHITE;
            textArea.setBackground(backgroundColor);
            textArea.setForeground(foregroundColor);
        } else if (actionCommand.equals(LIGHT_THEME)) {
            // Set the light theme
            backgroundColor = Color.WHITE;
            foregroundColor = Color.BLACK;
            textArea.setBackground(backgroundColor);
            textArea.setForeground(foregroundColor);
        } else if (actionCommand.equals(MOONLIGHT_THEME)) {
            // Set the moonlight theme
            backgroundColor = Color.BLUE;
            foregroundColor = Color.WHITE;
            textArea.setBackground(backgroundColor);
            textArea.setForeground(foregroundColor);
        }
    }


    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        editor.setVisible(true);
    }
}