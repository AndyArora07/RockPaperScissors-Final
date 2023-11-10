import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Creating the main window for the Rock Paper Scissors game, extending JFrame.
public class MainWindow extends JFrame {
    private JButton playButton, helpButton, quitButton;
    private JLabel introLabel;
    private JLabel descriptionLabel;
    private ImageIcon introImg;
    private Image backgroundImg;

    // Configuring the main window upon instantiation.
    public MainWindow() {
        initializeComponents();
        addListeners();
        setTitle("Rock Paper Scissors Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350); // Setting the window size.
        setLocationRelativeTo(null); // Centering the window.
        setVisible(true); // Making the window visible.
    }

    // Initializing UI components for the main window.
    private void initializeComponents() {
        backgroundImg = new ImageIcon("Resources/BGImg.jpg").getImage();

        // Creating a panel that draws the background image.
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // Initializing the buttons for user interaction.
        playButton = new JButton("PLAY");
        helpButton = new JButton("HELP");
        quitButton = new JButton("QUIT");
        // Applying styles to the buttons.
        styleButton(playButton);
        styleButton(helpButton);
        styleButton(quitButton);

        // Loading and scaling the background image.
        introImg = new ImageIcon("Resources/RPSMain.jpg");
        Image introImageScaled = introImg.getImage().getScaledInstance(250, 165, Image.SCALE_SMOOTH);
        introImg = new ImageIcon(introImageScaled);
        // Creating a label for the intro image.
        introLabel = new JLabel(introImg);

        // Setting up the description label with HTML for formatting. I was having trouble with Java styling, Mr. Bhinder Approved.
        descriptionLabel = new JLabel("<html><div style='text-align: center;'>This is the main window for the Rock Paper Scissors game. " +
                "Use the PLAY button to start the game, the HELP button to read the rules, and the QUIT button to exit the application.</div></html>", SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        descriptionLabel.setForeground(Color.WHITE); // Setting text color to white.

        // Organizing components on the panel.
        organizeComponents(backgroundPanel);

        // Using the custom panel as the content pane.
        setContentPane(backgroundPanel);
    }

    // Styling buttons with a consistent font, padding, and background color.
    private void styleButton(JButton button) {
        button.setFont(new Font("Times New Roman", Font.BOLD, 18));
        button.setMargin(new Insets(10, 20, 10, 20)); // Adding padding.
        button.setBackground(Color.WHITE); // Setting background color.
        button.setOpaque(true);
        button.setBorderPainted(false); // Removing the border for aesthetics.
    }

    // Adding action listeners to buttons for handling user actions.
    private void addListeners() {
        playButton.addActionListener(e -> {
            new PlayWindow(); // Opening the play window.
            MainWindow.this.setVisible(false); // Hiding the main window.
        });

        helpButton.addActionListener(e -> {
            new HelpWindow(); // Opening the help window.
            MainWindow.this.setVisible(false); // Hiding the main window.
        });

        quitButton.addActionListener(e -> System.exit(0)); // Exiting the application.
    }

    // Organizing components within the background panel using layout managers.
    private void organizeComponents(JPanel backgroundPanel) {
        // Creating a panel for the buttons with horizontal glue for spacing.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue()); // Adding space on either side of the buttons.
        buttonPanel.add(playButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); // Adding space between buttons.
        buttonPanel.add(helpButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); // Adding space between buttons.
        buttonPanel.add(quitButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.setOpaque(false); // Making the button panel transparent.

        // Padding the button panel vertically.
        int verticalPadding = 20;
        JPanel paddedButtonPanel = new JPanel(new BorderLayout());
        paddedButtonPanel.add(buttonPanel, BorderLayout.SOUTH);
        paddedButtonPanel.add(Box.createVerticalStrut(verticalPadding), BorderLayout.NORTH);
        paddedButtonPanel.setOpaque(false); // Making the panel transparent.

        // Centering the intro label and description label.
        introLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adding the panels to the background panel.
        backgroundPanel.add(paddedButtonPanel, BorderLayout.SOUTH);
        backgroundPanel.add(introLabel, BorderLayout.CENTER);
        backgroundPanel.add(descriptionLabel, BorderLayout.NORTH);
        backgroundPanel.setOpaque(false); // Making the background panel transparent.
    }

    // Main entry point for the application.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow()); // Launching the main window.
    }
}