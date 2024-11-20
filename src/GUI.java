import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener {

    JFrame frame;
    JPanel mainPanel, inputPanel, feedbackPanel, buttonPanel;
    JLabel firstNumLabel, feedback;
    JTextField numField;
    JButton subButton, hintButton, resetButton;
    int n;
    int attempts;
    int hints;

    public GUI() {
        frame = new JFrame("Simple Guessing Game");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 245));

        inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBackground(new Color(245, 245, 245));
        firstNumLabel = new JLabel("Your Guess:");
        firstNumLabel.setFont(new Font("Arial", Font.BOLD, 18));
        numField = new JTextField(10);
        numField.setFont(new Font("Arial", Font.PLAIN, 16));
        numField.setPreferredSize(new Dimension(150, 30));

        inputPanel.add(firstNumLabel);
        inputPanel.add(numField);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(new Color(245, 245, 245));

        subButton = createStyledButton("Go", new Color(70, 130, 180));
        hintButton = createStyledButton("Hint", new Color(255, 165, 0));
        resetButton = createStyledButton("Reset", new Color(220, 20, 60));

        buttonPanel.add(subButton);
        buttonPanel.add(hintButton);
        buttonPanel.add(resetButton);

        feedbackPanel = new JPanel();
        feedbackPanel.setBackground(new Color(245, 245, 245));
        feedback = new JLabel(" ");
        feedback.setFont(new Font("Arial", Font.ITALIC, 18));
        feedback.setForeground(new Color(34, 139, 34));
        feedbackPanel.add(feedback);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(feedbackPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        resetGame();

        frame.setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(120, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == subButton) {
            String input = numField.getText().trim();

            try {
                int guess = Integer.parseInt(input);

                if (guess < 1 || guess > 100) {
                    feedback.setText("Please enter a number between 1 and 100.");
                    numField.setText("");
                    return;
                }

                if (guess == n) {
                    feedback.setText("Congratulations! You guessed the number correctly in " + attempts + " attempts.");
                    subButton.setEnabled(false);
                    hintButton.setEnabled(false);
                } else if (guess < n) {
                    feedback.setText("Too low! Try again.");
                } else {
                    feedback.setText("Too high! Try again.");
                }

                attempts++;

            } catch (NumberFormatException ex) {
                feedback.setText("Invalid input. Please enter a valid number.");
            }

            numField.setText("");
        } else if (e.getSource() == hintButton) {
            giveHint();
        } else if (e.getSource() == resetButton) {
            feedback.setText("You have reset the game. The correct number was " + n + ". Goodbye!");
            resetGame();
        }
    }

    private void resetGame() {
        n = (int) (Math.random() * 100 + 1);
        attempts = 1;
        hints = 3;
        feedback.setText("Welcome! Guess a number between 1 and 100.");
        numField.setText("");
        subButton.setEnabled(true);
        hintButton.setEnabled(true);
        resetButton.setEnabled(true);
    }

    private void giveHint() {
        if (hints > 0) {
            switch (hints) {
                case 3:
                    if (n <= 50) {
                        feedback.setText("Hint: The number is 50 or less.");
                    } else {
                        feedback.setText("Hint: The number is greater than 50.");
                    }
                    break;

                case 2:
                    if (n % 2 == 0) {
                        feedback.setText("Hint: The number is even.");
                    } else {
                        feedback.setText("Hint: The number is odd.");
                    }
                    if (n % 3 == 0) {
                        feedback.setText(feedback.getText() + " It's divisible by 3.");
                    } else {
                        feedback.setText(feedback.getText() + " It's not divisible by 3.");
                    }
                    break;

                case 1:
                    if (n >= 10) {
                        int firstDigit = n;
                        while (firstDigit >= 10) {
                            firstDigit /= 10;
                        }
                        feedback.setText("Hint: The first digit of the number is " + firstDigit + ".");
                    } else {
                        feedback.setText("Hint: The number is under 10.");
                    }
                    break;
            }
            hints--;
        } else {
            feedback.setText("No hints left.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
