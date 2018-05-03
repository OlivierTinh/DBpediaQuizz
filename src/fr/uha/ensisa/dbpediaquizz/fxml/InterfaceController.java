package fr.uha.ensisa.dbpediaquizz.fxml;

import com.jfoenix.controls.JFXButton;
import fr.uha.ensisa.dbpediaquizz.questions.Question;
import fr.uha.ensisa.dbpediaquizz.questions.QuestionFactory;
import fr.uha.ensisa.dbpediaquizz.util.Constantes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class InterfaceController implements Initializable {

    private ActionEvent event;

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
        this.event = event;
        handleAnswer();
        handleQuestionNumber();
        generateNewQuestion();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNewQuestion();
        setScoreText(score);
        setQuestionNumberLabel(questionNumber);
    }

    /**
     * Vérifie si la réponse est correcte. Si c'est le cas, on incrémente le score de 1.
     */
    private void handleAnswer() {
        String answer = parseAnswer();
        if (question.isCorrect(answer)) score++;
    }

    /**
     * Incrémente le nombre de questions posées et le met à jour.
     * Si on atteint le maximum, on finit la partie.
     */
    private void handleQuestionNumber() {
        if (questionNumber == Constantes.NB_QUESTIONS)
            System.out.println("TODO: AFFICHER LE SCORE ICI LOL MDR");
        else
            setQuestionNumberLabel(++questionNumber);
    }

    /**
     * On génère une nouvelle question qu'on affiche sur l'interface graphique.
     */
    private void generateNewQuestion() {
        setQuestion(QuestionFactory.createQuestion());
        question.display(this);
    }

    private void reset() {
        score = 0;
        questionNumber = 0;
    }

    /**
     * Retourne le texte contenu dans un bouton lors du clic.
     * Pour cela, on effectue un remplacement de String, ce que l'on a étant de base sous la forme :
     * JFXButton[id=answer3, styleClass=button jfx-button]'Football Club des Girondins de Bordeaux'
     *
     * @return la réponse en String (Football Club des Girondins de Bordeaux)
     */
    private String parseAnswer() {
        return event.getSource().toString().replaceAll(".*]'(.*)'", "$1");
    }

    private void setQuestion(Question question) {
        this.question = question;
    }

    /**
     * Attribue à chaque bouton la réponse qui lui est associée.
     * Attribue une couleur au clic par la suite.
     *
     * @param index correspondant au numéro de la réponse
     * @param answer la réponse associé sous forme de String
     */
    public void setAnswer(int index, String answer) {
        switch (index) {
            case 1:
                answer1.setText(answer);
                setColorOnClick(answer1, answer);
                break;
            case 2:
                answer2.setText(answer);
                setColorOnClick(answer2, answer);
                break;
            case 3:
                answer3.setText(answer);
                setColorOnClick(answer3, answer);
                break;
            case 4:
                answer4.setText(answer);
                setColorOnClick(answer4, answer);
                break;
            default:
                System.err.println("Invalid index number for setAnswer()");
        }
    }

    /**
     * Attribue une couleur au clic en fonction du texte qui est contenu dans le bouton.
     * Si le texte correspond à la bonne réponse sa couleur au clic sera verte, sinon rouge.
     *
     * @param answerButton le bouton dont on souhaite changer la couleur
     * @param answer la réponse associée au bouton
     */
    private void setColorOnClick(JFXButton answerButton, String answer) {
        if (question.isCorrect(answer))
            answerButton.setRipplerFill(Paint.valueOf("LIME"));
        else
            answerButton.setRipplerFill(Paint.valueOf("RED"));
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
