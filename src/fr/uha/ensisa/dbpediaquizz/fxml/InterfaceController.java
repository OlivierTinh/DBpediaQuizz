package fr.uha.ensisa.dbpediaquizz.fxml;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class InterfaceController implements Initializable {

    @FXML
    private Text scoreText;

    @FXML
    private Label question;

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
        System.out.println(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

    public void setQuestion(String question) {
        this.question.setText(question);
    }

    public void setScoreText(int score) {
        this.scoreText.setText("Score  >>  " + score + "/10");
    }

}
