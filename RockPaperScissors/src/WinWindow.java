import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Defines a window for celebrating a user's victory.
public class WinWindow extends JFrame {

    // Constructs a window with specified actions for playing again or quitting.
    public WinWindow(Runnable playAgainAction, Runnable quitAction) {
        // Setting the title of the window to "Congratulations!".
        setTitle("Congratulations!");
        // Sizing the window to 400 pixels wide and 300 pixels tall.
        setSize(400, 300);
        // Ensuring the window closes and disposes of itself upon user-initiated close.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Centering the window relative to the screen.
        setLocationRelativeTo(null);

        // Initializing the background panel to display an image.
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        // Applying a BorderLayout manager to arrange components in five regions.
        setLayout(new BorderLayout());
        // Adding the background panel to the frame's center region.
        add(backgroundPanel);

        // Creating a label with the winning message.
        JLabel messageLabel = new JLabel("Congratulations, you won!", SwingConstants.CENTER);
        // Applying a bold Arial font at size 16 to the message label.
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        // Placing the message label in the center of the background panel.
        backgroundPanel.add(messageLabel, BorderLayout.CENTER);

        // Preparing a button for starting a new game.
        JButton playAgainButton = new JButton("Play Again");
        // Hooking up an action to the play again button that triggers playAgainAction when activated.
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAgainAction.run();
                WinWindow.this.dispose();
            }
        });

        // Setting up a button to exit the game.
        JButton quitButton = new JButton("Quit");
        // Connecting an action to the quit button that triggers quitAction when activated.
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitAction.run();
            }
        });

        // Composing a panel for containing buttons.
        JPanel buttonPanel = new JPanel();
        // Making the button panel transparent.
        buttonPanel.setOpaque(false);
        // Adding the play again button to the button panel.
        buttonPanel.add(playAgainButton);
        // Including the quit button in the button panel.
        buttonPanel.add(quitButton);
        // Placing the button panel at the south region of the background panel.
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Making the window visible to the user.
        setVisible(true);
    }

    // Defining a custom panel for the window background.
    private class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        // Constructing the panel and loading the background image.
        public BackgroundPanel() {
            //Loading the "Resources/WinBg.png" as the background image.
            backgroundImage = new ImageIcon("Resources/WinBg.png").getImage();
            // Setting the panel layout to BorderLayout to match the parent frame.
            setLayout(new BorderLayout());
        }

        // Customizing the painting of the panel to include the background image.
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Drawing the background image to fit the entire panel.
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}