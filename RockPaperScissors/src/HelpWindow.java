import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Extending JFrame to create a custom window for help information.
public class HelpWindow extends JFrame {
    private JButton backButton;
    private JButton quitButton;
    private JLabel helpContentLabel;
    private JLabel titleLabel;
    private JPanel contentPanel;

    // Setting up the help window upon instantiation.
    public HelpWindow() {
        initializeComponents();
        addListeners();
        setupLayout();
        setTitle("Help - Rock Paper Scissors Game");
        setSize(400, 300); // Setting the window size.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centering the window on screen.
        setVisible(true); // Making the window visible.
    }

    // Initializing UI components for the window.
    private void initializeComponents() {
        backButton = new JButton("BACK");
        quitButton = new JButton("QUIT");

        // Creating and styling the title label.
        titleLabel = new JLabel("Rock Paper Scissors Rules", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        // Creating and setting the help content label with HTML for formatting.
        helpContentLabel = new JLabel("<html>" +
                "<ul>" +
                "<li>Rock crushes Scissors, Scissors cuts Paper, Paper wraps Rock</li>" +
                "<li>If both choices are the same , it is a tie!</li>" +
                "<li>Try Again and Play Again button let you start again resetting both scores</li>" +
                "<li>The goal is to beat the computer five times</li>" +
                "</ul>" +
                "</html>");
        helpContentLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        helpContentLabel.setForeground(Color.WHITE);

        // Setting a background image using a JLabel.
        ImageIcon backgroundImage = new ImageIcon("Resources/helpBgImg.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        setContentPane(backgroundLabel);
        getLayeredPane().setLayout(new BorderLayout()); // Applying BorderLayout for layering components.
    }

    // Adding action listeners to buttons.
    private void addListeners() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainWindow().setVisible(true); // Showing the main window.
                HelpWindow.this.dispose(); // Closing the help window.
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exiting the application.
            }
        });
    }

    // Arranging components on the window using a layout manager.
    private void setupLayout() {
        // Using BorderLayout for the content pane.
        setLayout(new BorderLayout());

        // Creating and configuring a panel for the title and help content.
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Adding padding around the content panel.

        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Adding bottom margin to the title label.

        // Adding the title and content labels to the content panel.
        contentPanel.add(titleLabel);
        contentPanel.add(helpContentLabel);

        // Creating a panel for the back and quit buttons.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false); // Making the button panel transparent.
        buttonPanel.add(backButton);
        buttonPanel.add(quitButton);

        // Adding the content and button panels to the content pane.
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }
}