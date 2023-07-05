import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class QuizApplication1 {
    private JFrame frame;
    private JLabel questionLabel;
    private JLabel questionid;
    private JRadioButton option1RadioButton;
    private JRadioButton option2RadioButton;
    private JRadioButton option3RadioButton;
    private JRadioButton option4RadioButton;
    
    private JButton submitButton;
    public int id;
    private int currentQuestionIndex;
    private int score;

    private Question[] questions = {
            new Question("Question 1: Which type of data can be stored in database? ",
                    new String[]{"image oriented data", "text files", "data in form of audio", "all th above ","QuestionID :vceoo1"}, 3),
            new Question("Question 2: Which of following is not a database?",
                    new String[]{"heiarchial", "network", "decentralized", "distributed","QuestionID :vceoo2"}, 2),
            new Question("Question 3: Which is not a dbms?",
                    new String[]{"My sql", "microsoft acess", "google", "ibm db2","QuestionID :vceoo3"},2 ),
            new Question("Question 4: Who painted the Mona Lisa?",
                    new String[]{"Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Michelangelo","QuestionID :vceoo4"}, 0),
            new Question("Question 5: What is the chemical symbol for gold?",
                    new String[]{"Au", "Ag", "Fe", "Cu","QuestionID :vceoo5"}, 0)
    };
    
    
    public QuizApplication1(int x) {
    	this.id=x;
        currentQuestionIndex = 0;
        score = 0;
        initialize();
    }
   

    private void initialize() {
        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
       
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        questionLabel = new JLabel();
        panel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(5, 1));

        option1RadioButton = new JRadioButton();
        optionsPanel.add(option1RadioButton);

        option2RadioButton = new JRadioButton();
        optionsPanel.add(option2RadioButton);

        option3RadioButton = new JRadioButton();
        optionsPanel.add(option3RadioButton);

        option4RadioButton = new JRadioButton();
        optionsPanel.add(option4RadioButton);
        
        questionid=new JLabel();
        optionsPanel.add(questionid);
        

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(option1RadioButton);
        buttonGroup.add(option2RadioButton);
        buttonGroup.add(option3RadioButton);
        buttonGroup.add(option4RadioButton);

       
       	
       
       
        panel.add(optionsPanel, BorderLayout.CENTER);
       

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitAnswer();
            }
        });
        panel.add(submitButton, BorderLayout.SOUTH);
        
        frame.getContentPane().add(panel);
        displayQuestion();
        frame.setVisible(true);
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.length) {
            Question currentQuestion = questions[currentQuestionIndex];
            questionLabel.setText(currentQuestion.getQuestion());
            String[] options = currentQuestion.getOptions();
            option1RadioButton.setText(options[0]);
            option2RadioButton.setText(options[1]);
            option3RadioButton.setText(options[2]);
            option4RadioButton.setText(options[3]);
            questionid.setText(options[4]);
            
        } else {
            showResult();
        }
    }

    private void submitAnswer() {
        Question currentQuestion = questions[currentQuestionIndex];
        int selectedOption = getSelectedOption();

        if (selectedOption == currentQuestion.getCorrectOption()) {
            score++;
        }

        currentQuestionIndex++;
        displayQuestion();
    }

    private int getSelectedOption() {
        if (option1RadioButton.isSelected()) {
            return 0;
        } else if (option2RadioButton.isSelected()) {
            return 1;
        } else if (option3RadioButton.isSelected()) {
            return 2;
        } else if (option4RadioButton.isSelected()) {
            return 3;
        } else {
            return -1;
        }
    }

    private void showResult() {
    	String driverClassName = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "suchith1";
        String pass = "2004";

try {
    // Load the JDBC driver
    Class.forName(driverClassName);

    // Establish a connection to the database
    Connection con = DriverManager.getConnection(url, username, pass);

    // Perform database operations using the connection
Statement stmt=con.createStatement();  
int a=stmt.executeUpdate("insert into scores values("+id+","+score+")");  
if(a>0)
JOptionPane.showMessageDialog(null, "Inserted successfully", "Error", JOptionPane.INFORMATION_MESSAGE);
else
JOptionPane.showMessageDialog(null, "Fail", "Error", JOptionPane.ERROR_MESSAGE);
    // Close the connection
    con.close();

    System.out.println("Connection closed successfully.");
} catch (ClassNotFoundException s) {
    System.err.println("Failed to load JDBC driver: " + s.getMessage());
} catch (SQLException s) {
    System.err.println("Failed to connect to the database: " + s.getMessage());
}

        JOptionPane.showMessageDialog(frame, "Quiz completed!\nScore: " + score + " out of " + questions.length);
        System.exit(0);
    }

   /* public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new QuizApplication1();
            }
        });
    }
*/
    private class Question {
        private String question;
        private String[] options;
        private int correctOption;

        public Question(String question, String[] options, int correctOption) {
            this.question = question;
            this.options = options;
            this.correctOption = correctOption;
        }

        public String getQuestion() {
            return question;
        }

        public String[] getOptions() {
            return options;
        }

        public int getCorrectOption() {
            return correctOption;
        }
    }
}
