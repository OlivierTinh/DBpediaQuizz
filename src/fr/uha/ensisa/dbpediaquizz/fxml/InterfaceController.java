package fr.uha.ensisa.dbpediaquizz.fxml;

import com.jfoenix.controls.JFXButton;
import fr.uha.ensisa.dbpediaquizz.questions.Question;
import fr.uha.ensisa.dbpediaquizz.questions.QuestionFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class InterfaceController implements Initializable {

    private Question question;
    private int score = 0;
    private int questionNumber = 0;

    @FXML
    private Text scoreText;

    @FXML
    private Label questionNumberLabel;

    @FXML
    private Label fieldLabel;

    @FXML
    private Label questionLabel;

    @FXML
    private JFXButton answer1;

    @FXML
    private JFXButton answer2;

    @FXML
    private JFXButton answer3;

    @FXML
    private JFXButton answer4;

    @FXML
    void handleAnswerButton(ActionEvent event) {
        // checking the answer
        String answer = event.getSource().toString().replaceAll(".*]'(.*)'", "$1");
        System.out.println("\n+ " + answer);
        if (question.isCorrect(answer))
            handleCorrectAnswer();
        else
            handleFalseAnswer();

        // checking the nb of questions asked
        if (questionNumber == 10)
            System.out.println("TODO: AFFICHER LE SCORE ICI LOL MDR");
        else
            setQuestionNumberLabel(++questionNumber);

        // generating a new question
        // TODO: mettre une légère pause
        setQuestion(QuestionFactory.createQuestion());
        question.display(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setQuestion(QuestionFactory.createQuestion());
        question.display(this);
        setScoreText(score);
        setQuestionNumberLabel(questionNumber);
    }

    private void handleCorrectAnswer() {
        setScoreText(++score);
    }

    private void handleFalseAnswer() {

    }

    private void reset() {
        score = 0;
        questionNumber = 0;
    }

    private void setQuestion(Question question) {
        this.question = question;
    }

    public void setAnswer(int index, String answer) {
        switch (index) {
            case 1:
                answer1.setText(answer);
                break;
            case 2:
                answer2.setText(answer);
                break;
            case 3:
                answer3.setText(answer);
                break;
            case 4:
                answer4.setText(answer);
                break;
            default:
                System.err.println("Invalid index number for setAnswer()");
        }
    }

    public void setMatiere(String matiere) {
        this.fieldLabel.setText(matiere);
    }

    public void setQuestionLabel(String question) {
        this.questionLabel.setText(question);
    }

    private void setQuestionNumberLabel(int nb) {
        this.questionNumberLabel.setText("Question " + nb + "/10");
    }

    private void setScoreText(int score) {
        this.scoreText.setText("Score : " + score);
    }

}
