package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Quiz extends JFrame implements ActionListener {

    JLabel questionLabel, timerLabel;
    JRadioButton[] options = new JRadioButton[4];
    ButtonGroup group;
    JButton next, submit;

    ArrayList<Question> questionsList = new ArrayList<>();
    int currentQuestionIndex = 0;
    int score = 0;

    Timer timer;
    int timeLeft = 60;

    String category;

    Quiz(String category) {
        this.category = category;

        getContentPane().setBackground(Color.white);
        setLayout(null);

        questionLabel = new JLabel("");
        questionLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        questionLabel.setBounds(50, 50, 900, 50);
        add(questionLabel);

       timerLabel = new JLabel("Time left: " + timeLeft + " seconds", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        timerLabel.setBounds(350, 10, 300, 30); // Centered at the top
        timerLabel.setForeground(Color.RED);
        add(timerLabel);

        group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setBounds(100, 120 + (i * 40), 800, 30);
            options[i].setFont(new Font("Dialog", Font.PLAIN, 16));
            options[i].setBackground(Color.white);
            group.add(options[i]);
            add(options[i]);
        }

        next = new JButton("Next");
        next.setBounds(300, 300, 100, 30);
        next.setBackground(new Color(30, 144, 254));
        next.setForeground(Color.WHITE);
        next.addActionListener(this);
        add(next);

        submit = new JButton("Submit");
        submit.setBounds(450, 300, 100, 30);
        submit.setBackground(new Color(30, 144, 254));
        submit.setForeground(Color.WHITE);
        submit.addActionListener(this);
        submit.setEnabled(false);
        add(submit);

        fetchQuestionsFromDatabase();

        if (!questionsList.isEmpty()) {
            displayQuestion();
            startTimer();
        } else {
            JOptionPane.showMessageDialog(this, "No questions found for this category.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        setSize(1000, 500);
        setLocation(200, 100);
        setVisible(true);
    }

private void fetchQuestionsFromDatabase() {
    try {
        conn c = new conn();
        String query = "SELECT * FROM " + category;
        ResultSet rs = c.s.executeQuery(query);

        while (rs.next()) {
            String correctOption = rs.getString("correct_option");
            String correctAnswerText = "";

            // Map the correct option key to the actual answer text
            switch (correctOption) {
                case "option1":
                    correctAnswerText = rs.getString("option1");
                    break;
                case "option2":
                    correctAnswerText = rs.getString("option2");
                    break;
                case "option3":
                    correctAnswerText = rs.getString("option3");
                    break;
                case "option4":
                    correctAnswerText = rs.getString("option4");
                    break;
            }

            questionsList.add(new Question(
                rs.getInt("id"),
                rs.getString("question"),
                rs.getString("option1"),
                rs.getString("option2"),
                rs.getString("option3"),
                rs.getString("option4"),
                correctAnswerText // Store the actual answer text
            ));
        }

        c.s.close();
        c.c.close();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error fetching questions from the database.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private boolean isAnswerCorrect() {
    Question currentQuestion = questionsList.get(currentQuestionIndex);
    for (int i = 0; i < 4; i++) {
        if (options[i].isSelected() && options[i].getText().equals(currentQuestion.getCorrectOption())) {
            return true;
        }
    }
    return false;
}

    private void displayQuestion() {
        Question currentQuestion = questionsList.get(currentQuestionIndex);
        questionLabel.setText(currentQuestion.getId() + ". " + currentQuestion.getQuestion());
        options[0].setText(currentQuestion.getOption1());
        options[1].setText(currentQuestion.getOption2());
        options[2].setText(currentQuestion.getOption3());
        options[3].setText(currentQuestion.getOption4());

        group.clearSelection();
        timeLeft = 60;
        timerLabel.setText("Time left: " + timeLeft + " seconds");
    }

   
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--;
                    timerLabel.setText("Time left: " + timeLeft + " seconds");
                } else {
                    timer.cancel();
                    handleTimeOut();
                }
            }
        }, 0, 1000);
    }

    private void handleTimeOut() {
        if (currentQuestionIndex < questionsList.size() - 1) {
            if (isAnswerCorrect()) {
                score++;
            }
            currentQuestionIndex++;
            displayQuestion();
            startTimer();
        } else {
            if (isAnswerCorrect()) {
                score++;
            }
            endQuiz();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            if (isAnswerCorrect()) {
                score++;
            }

            currentQuestionIndex++;

            if (currentQuestionIndex == questionsList.size() - 1) {
                next.setEnabled(false);
                submit.setEnabled(true);
            }

            timer.cancel();
            displayQuestion();
            startTimer();
        } else if (ae.getSource() == submit) {
            if (isAnswerCorrect()) {
                score++;
            }
            timer.cancel();
            endQuiz();
        }
    }

    private void endQuiz() {
        JOptionPane.showMessageDialog(this, "You scored " + score + " out of " + questionsList.size(), "Quiz Completed", JOptionPane.INFORMATION_MESSAGE);
        setVisible(false);
        new type().setVisible(true);
    }

    public static void main(String[] args) {
        String selectedCategory = "math";
        new Quiz(selectedCategory);
    }
}

class Question {
    private int id;
    private String question, option1, option2, option3, option4, correct_option;

    public Question(int id, String question, String option1, String option2, String option3, String option4, String correct_option) {
        this.id = id;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correct_option = correct_option;
    }

    public int getId() { return id; }
    public String getQuestion() { return question; }
    public String getOption1() { return option1; }
    public String getOption2() { return option2; }
    public String getOption3() { return option3; }
    public String getOption4() { return option4; }
    public String getCorrectOption() { return correct_option; }
} 