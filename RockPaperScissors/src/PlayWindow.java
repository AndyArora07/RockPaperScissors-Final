import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.*;

// Extending JPanel to create a custom panel capable of displaying a background image.
class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    // Constructing the panel and loading the background image from a file name.
    public BackgroundPanel(String fileName) {
        // Loading the image and storing it for later use.
        backgroundImage = new ImageIcon(fileName).getImage();
    }

    // Overriding the paintComponent method to draw the background image.
    @Override
    protected void paintComponent(Graphics g) {
        // Calling the superclass method for standard painting behavior. Learned from the lesson
        super.paintComponent(g);
        // Drawing the background image, scaling it to fill the panel.
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}

// Defining the main game window for Rock-Paper-Scissors, extending JFrame.
public class PlayWindow extends JFrame {
    // Declaring buttons for player choices and game controls.
    private JButton rockButton, paperButton, scissorsButton, quitButton, backButton, replayButton;
    // Declaring labels for displaying game results, choices, and scores.
    private JLabel resultLabel, userChoiceLabel, computerChoiceLabel, versusLabel, userScoreLabel, computerScoreLabel;
    // Initializing scores for the user and the computer.
    private int userScore = 0;
    private int computerScore = 0;

    // Constructing the game window and configuring its properties.
    public PlayWindow() {
        // Initializing components like buttons and labels.
        initializeComponents();
        // Arranging the components on the window using layout managers.
        setupLayout();
        // Setting the window title.
        setTitle("Play - Rock Paper Scissors");
        // Sizing the window.
        setSize(625, 450);
        // Specifying the window's close operation.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Centering the window relative to the screen.
        setLocationRelativeTo(null);
        // Making the window visible.
        setVisible(true);
    }

    // Initializing the UI components for the game.
    private void initializeComponents() {
        // Creating buttons with icons for the rock, paper, and scissors choices.
        rockButton = new JButton("Rock", createIcon("Resources/rock.png", 96, 86));
        paperButton = new JButton("Paper", createIcon("Resources/paper.png", 96, 86));
        scissorsButton = new JButton("Scissors", createIcon("Resources/scissors.png", 96, 72));

        // Creating buttons for game control actions.
        backButton = new JButton("Back");
        quitButton = new JButton("Quit");
        replayButton = new JButton("Try Again");

        // Creating labels to show the current game status and scores.
        resultLabel = new JLabel("Choose your move", SwingConstants.CENTER);
        userChoiceLabel = new JLabel();
        computerChoiceLabel = new JLabel();
        versusLabel = new JLabel(createIcon("Resources/versus.png", 108, 96));
        userScoreLabel = new JLabel("Player Score: 0", SwingConstants.CENTER);
        computerScoreLabel = new JLabel("Machine Score: 0", SwingConstants.CENTER);

        // Applying styling to the labels.
        setLabelStyle(resultLabel, 30);
        setLabelStyle(userChoiceLabel, 20);
        setLabelStyle(computerChoiceLabel, 20);
        setLabelStyle(versusLabel, 20);
        setLabelStyle(userScoreLabel, 20);
        setLabelStyle(computerScoreLabel, 20);

        // Making the choice and versus labels initially invisible until the game starts.
        userChoiceLabel.setVisible(false);
        computerChoiceLabel.setVisible(false);
        versusLabel.setVisible(false);

        // Attaching actions to the buttons to handle user interactions during the game.
        rockButton.addActionListener(e -> playGame("rock"));
        paperButton.addActionListener(e -> playGame("paper"));
        scissorsButton.addActionListener(e -> playGame("scissors"));
        quitButton.addActionListener(e -> dispose());
        backButton.addActionListener(e -> {
            new MainWindow().setVisible(true);
            dispose();
        });
        replayButton.addActionListener(e -> resetGame());
    }

    // Arranging the UI components using layout managers for the main game panel.
    private void setupLayout() {
        // Creating a custom background panel with an image.
        BackgroundPanel mainPanel = new BackgroundPanel("Resources/PlayBgImg.jpg");
        // Setting the layout for the main panel to BorderLayout.
        mainPanel.setLayout(new BorderLayout(10, 10));

        // Configuring a panel with a grid layout for the game choice buttons.
        JPanel gameButtonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        // Setting the game button panel to be transparent.
        gameButtonPanel.setOpaque(false);
        // Adding borders for padding around the game choice buttons.
        gameButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Adding the choice buttons to the panel.
        gameButtonPanel.add(rockButton);
        gameButtonPanel.add(paperButton);
        gameButtonPanel.add(scissorsButton);

        // Setting up a flow layout panel for the status labels.
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // Making the status panel transparent.
        statusPanel.setOpaque(false);
        // Adding score and choice labels to the status panel.
        statusPanel.add(userScoreLabel);
        statusPanel.add(userChoiceLabel);
        statusPanel.add(versusLabel);
        statusPanel.add(computerChoiceLabel);
        statusPanel.add(computerScoreLabel);

        // Preparing a flow layout panel for the bottom control buttons.
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // Making the bottom panel transparent.
        bottomPanel.setOpaque(false);
        // Adding the back, quit, and replay buttons to the bottom panel.
        bottomPanel.add(backButton);
        bottomPanel.add(quitButton);
        bottomPanel.add(replayButton);

        // Creating a container panel for the bottom section of the window.
        JPanel bottomContainer = new JPanel(new BorderLayout());
        // Making the bottom container transparent.
        bottomContainer.setOpaque(false);
        // Placing the result label and bottom panel in the bottom container.
        bottomContainer.add(resultLabel, BorderLayout.CENTER);
        bottomContainer.add(bottomPanel, BorderLayout.PAGE_END);

        // Adding all panels to the main background panel according to their layout positions.
        mainPanel.add(gameButtonPanel, BorderLayout.NORTH);
        mainPanel.add(statusPanel, BorderLayout.CENTER);
        mainPanel.add(bottomContainer, BorderLayout.SOUTH);

        // Setting the main panel as the content pane of the frame.
        setContentPane(mainPanel);
    }

    // Handling the game logic when the player makes a choice.
    private void playGame(String userChoice) {
        // Creating an array of possible choices for the computer.
        String[] choices = {"rock", "paper", "scissors"};
        // Randomly selecting a choice for the computer.
        String computerChoice = choices[new Random().nextInt(choices.length)];
        // Calculating the result of the game based on the player's and computer's choices.
        String result = calculateResult(userChoice, computerChoice);
        // Updating the UI with the game's outcome.
        updateUI(userChoice, computerChoice, result);
    }

    // Determining the result of the game and updating the scores accordingly.
    private String calculateResult(String userChoice, String computerChoice) {
        // Declaring a variable to store the result of the game.
        String result;
        // Determining if the user wins using boolean logic.
        boolean userWins = (userChoice.equals("rock") && computerChoice.equals("scissors")) ||
                (userChoice.equals("paper") && computerChoice.equals("rock")) ||
                (userChoice.equals("scissors") && computerChoice.equals("paper"));

        // Comparing choices to determine the game outcome and updating the result message.
        if (userChoice.equals(computerChoice)) {
            // Indicating that the game is a tie.
            result = "It's a tie!";
        } else if (userWins) {
            // Incrementing the user score and updating the user score label for a win.
            userScore++;
            userScoreLabel.setText("Player Score: " + userScore);
            result = "You win!";
            // Playing the winning sound effect.
            playSound("win.wav");
        } else {
            // Incrementing the computer score and updating the computer score label for a loss.
            computerScore++;
            computerScoreLabel.setText("Machine Score: " + computerScore);
            result = "You lose!";
            // Playing the losing sound effect.
            playSound("lose.wav");
        }

        // Checking if the user has won the game and displaying a WinWindow if they have.
        if (userScore == 3) {
            SwingUtilities.invokeLater(() -> {
                // Creating a WinWindow with specified actions for playing again or quitting.
                WinWindow winWindow = new WinWindow(() -> {
                    new PlayWindow().setVisible(true); // Opening a new PlayWindow.
                }, () -> System.exit(0)); // Exiting the application.

                // Making the WinWindow visible to the user.
                winWindow.setVisible(true);
                // Disposing of the current PlayWindow.
                PlayWindow.this.dispose();
            });
        }
        // Returning the result string.
        return result;
    }

    // Updating the user interface with the player's and computer's choices, as well as the game result.
    private void updateUI(String userChoice, String computerChoice, String result) {
        // Setting the icon for the user's choice label based on the user's selection.
        userChoiceLabel.setIcon(createIcon("Resources/" + userChoice + ".png", 64, 60));
        // Making the user choice label visible.
        userChoiceLabel.setVisible(true);
        // Setting the icon for the computer's choice label based on the computer's selection.
        computerChoiceLabel.setIcon(createIcon("Resources/" + computerChoice + ".png", 64, 60));
        // Making the computer choice label visible.
        computerChoiceLabel.setVisible(true);
        // Displaying the versus label.
        versusLabel.setVisible(true);
        // Updating the result label with an HTML formatted string showing the choices and result. For same reason as earlier
        resultLabel.setText("<html><div style='text-align: center;'>You chose " + userChoice +
                "<br>Computer chose " + computerChoice + "<br>" + result + "</div></html>");
        // Making the result label visible.
        resultLabel.setVisible(true);
    }

    // Resetting the game to its initial state.
    private void resetGame() {
        // Resetting user score to zero.
        userScore = 0;
        // Updating the user score label to reflect the reset.
        userScoreLabel.setText("Player Score: 0");
        // Hiding the computer choice label.
        computerChoiceLabel.setVisible(false);
        // Hiding the user choice label.
        userChoiceLabel.setVisible(false);
        // Hiding the versus label.
        versusLabel.setVisible(false);
        // Hiding the result label.
        resultLabel.setVisible(false);
    }

    // Playing a sound file given its file name.
    private void playSound(String soundFileName) {
        try {
            // Findinf the sound file within the Resources directory.
            File soundFile = new File("./Resources/" + soundFileName);
            // Throwing an exception if the sound file does not exist.
            if (!soundFile.exists()) {
                throw new IllegalArgumentException("Could not find file: " + soundFile.getAbsolutePath());
            }
            // Opening an audio stream for the sound file.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile.toURI().toURL());
            // Getting a sound clip resource.
            Clip clip = AudioSystem.getClip();
            // Opening the clip with the audio stream.
            clip.open(audioIn);
            // Starting the sound clip, causing it to play.
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // Printing the stack trace if an error occurs during sound playback.
            e.printStackTrace();
        }
    }

    // Creating an ImageIcon from a file path with specified width and height.
    private ImageIcon createIcon(String path, int width, int height) {
        // Scaling the image icon to the specified width and height smoothly.
        return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    // Setting the style of a JLabel with a specified font size.
    private void setLabelStyle(JLabel label, int fontSize) {
        // Changing the text color of the label to white.
        label.setForeground(Color.WHITE);
        // Applying a bold Times New Roman font at the specified size to the label.
        label.setFont(new Font("Times New Roman", Font.BOLD, fontSize));
    }

    // The entry point of the program.
    public static void main(String[] args) {
        // Invoking the creation and display of the PlayWindow on the Event Dispatch Thread.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Instantiating and displaying the main game window.
                new PlayWindow();
            }
        });
    }
}