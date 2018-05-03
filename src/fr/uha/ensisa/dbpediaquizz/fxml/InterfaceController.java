package fr.uha.ensisa.dbpediaquizz.fxml;

import com.jfoenix.controls.JFXButton;
import fr.uha.ensisa.dbpediaquizz.questions.Question;
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
    private Label questionLabel;

    @FXML
    private JFXButton fieldText;

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
        String answer = event.getSource().toString().replaceAll(".*'(.*?)'", "$1");
        setQuestionNumberLabel(++questionNumber);

        if (question.isCorrect(answer))
            handleCorrectAnswer();
        else
            handleFalseAnswer();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    private void handleCorrectAnswer() {
        setScoreText(++score);
    }

    private void handleFalseAnswer() {

    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
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
        this.fieldText.setText(matiere);
    }

    public void setQuestionLabel(String question) {
        this.questionLabel.setText(question);
    }

    public void setQuestionNumberLabel(int nb) {
        this.questionNumberLabel.setText("Question " + nb + "/10");
    }

    public void setScoreText(int score) {
        this.scoreText.setText("Score : " + score);
    }

}
