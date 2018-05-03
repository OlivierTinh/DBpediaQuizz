package fr.uha.ensisa.dbpediaquizz.fxml;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToolbar;
import fr.uha.ensisa.dbpediaquizz.questions.Question;
import fr.uha.ensisa.dbpediaquizz.questions.QuestionFactory;
import fr.uha.ensisa.dbpediaquizz.util.Constantes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class InterfaceController implements Initializable {

    private ActionEvent event;
    private Timer timer = new Timer();
    private final int[] timeLeft = {Constantes.TEMPS_RESTANT};

    private Question question;
    private int score = 0;
    private int questionNumber = 0;


    /* --- Menu Panel --- */

    @FXML
    private Pane menuPanel;

    @FXML
    private JFXButton newGameButton;

    @FXML
    private JFXButton exitButton;


    /* --- Game Panel --- */

    @FXML
    private Pane gamePanel;

    @FXML
    private JFXToolbar scoreBar;

    @FXML
    private Text scoreText;

    @FXML
    private Text timeLeftText;

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


    /* --- Final Score Panel --- */

    @FXML
    private Pane gameOverPanel;

    @FXML
    private Label gameOverScoreLabel;

    @FXML
    private Label gameOverCommentLabel;

    @FXML
    private JFXButton gameOverReplay;

    @FXML
    private JFXButton gameOverExit;

    /* --- FXML Functions --- */

    @FXML
    void handleNewGameButton() {
        gamePanel.setVisible(true);
        scoreBar.setVisible(true);
        menuPanel.setVisible(false);
        gameOverPanel.setVisible(false);
        startGame();
    }

    @FXML
    void handleExitButton() {
        System.exit(0);
    }

    @FXML
    void handleAnswerButton(ActionEvent event) {
        this.event = event;
        handleAnswer();
        handleQuestionNumber();
        generateNewQuestion();
        timeLeft[0] = Constantes.TEMPS_RESTANT;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuPanel.setVisible(true);
        gamePanel.setVisible(false);
        gameOverPanel.setVisible(false);
        scoreBar.setVisible(false);
    }

    /**
     * Lance la partie en réinitialisant les données et en générant une nouvelle question.
     */
    private void startGame() {
        resetData();
        generateNewQuestion();
        setScoreText(score);
        setQuestionNumberLabel(++questionNumber);
        startTimer();
    }

    /**
     * Démarre un timer qui se décrémente de seconde en seconde.
     * Lorsque le temps restant est nul, on passe à la prochaine question.
     */
    private void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                displayTimeLeftText();
                timeLeft[0]--;
                if(timeLeft[0] < 0) {
                    Platform.runLater(() -> {
                        handleQuestionNumber();
                        generateNewQuestion();
                    });
                    timeLeft[0] = Constantes.TEMPS_RESTANT;
                }
            }
        }, 0, Constantes.TEMPS_RESTANT * 100);
    }

    /**
     * Gère la fin de la partie. Affiche le panel montrant le score ainsi qu'un commentaire lié à celui-ci.
     */
    private void gameOver() {
        gameOverPanel.setVisible(true);
        gamePanel.setVisible(false);
        scoreBar.setVisible(false);
        displayGameOverScoreLabel();
        displayGameOverCommentLabel();
        timer.cancel();
    }

    /**
     * Vérifie si la réponse est correcte. Si c'est le cas, on incrémente le score de 1.
     */
    private void handleAnswer() {
        String answer = parseAnswer();
        if (question.isCorrect(answer))
            setScoreText(++score);
    }

    /**
     * Incrémente le nombre de questions posées et le met à jour.
     * Si on atteint le maximum, on finit la partie.
     */
    private void handleQuestionNumber() {
        if (questionNumber == Constantes.NB_QUESTIONS)
            gameOver();
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

    /**
     * Met à zéro les données liées au jeu (score et nb de questions posées)
     */
    private void resetData() {
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
     * TODO: C'est super facile de tricher avec ça... A corriger
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

    private void setQuestion(Question question) {
        this.question = question;
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

    private void displayTimeLeftText() {
        this.timeLeftText.setText("Temps restant: " + timeLeft[0] + "s");
    }

    private void displayGameOverScoreLabel() {
        this.gameOverScoreLabel.setText(score + "/10");
    }

    /**
     * Affiche un commentaire en fonction du score du joueur à la fin d'une partie.
     */
    private void displayGameOverCommentLabel() {
        String comment;

        if (score < 3)
            comment = "La culture G et vous ça ne fait pas deux, n'est-ce pas ?";
        else if (score < 6)
            comment = "Pas mal, mais peut mieux faire.";
        else if (score < 9)
            comment = "Un score honorable ! Bravo :)";
        else
            comment = "Vous avez triché, n'est-ce pas ?";

        this.gameOverCommentLabel.setText(comment);
    }

}
