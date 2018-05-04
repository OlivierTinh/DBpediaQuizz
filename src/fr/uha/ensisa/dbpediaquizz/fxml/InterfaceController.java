package fr.uha.ensisa.dbpediaquizz.fxml;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToolbar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class InterfaceController implements Initializable {

    private InterfaceModel game;


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
        displayGameMenu();
        game.start();
    }

    @FXML
    void handleExitButton() {
        System.exit(0);
    }

    @FXML
    void handleAnswerButton(ActionEvent event) {
        game.handleAnswerButton(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new InterfaceModel(this);
        displayMenu();
    }

    /**
     * Affiche le menu de démarrage du jeu.
     */
    private void displayMenu() {
        menuPanel.setVisible(true);
        gamePanel.setVisible(false);
        gameOverPanel.setVisible(false);
        scoreBar.setVisible(false);
    }

    /**
     * Affiche le panel de la partie courante.
     */
    private void displayGameMenu() {
        menuPanel.setVisible(false);
        gamePanel.setVisible(true);
        gameOverPanel.setVisible(false);
        scoreBar.setVisible(true);
    }

    /**
     * Affiche le menu de game over.
     */
    void displayGameOver() {
        menuPanel.setVisible(false);
        gamePanel.setVisible(false);
        gameOverPanel.setVisible(true);
        scoreBar.setVisible(false);
        displayGameOverScoreLabel();
        displayGameOverCommentLabel();
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
        if (game.getQuestion().isCorrect(answer))
            answerButton.setRipplerFill(Paint.valueOf("LIME"));
        else
            answerButton.setRipplerFill(Paint.valueOf("RED"));
    }

    public void setField(String matiere) {
        this.fieldLabel.setText(matiere);
    }

    public void setQuestionLabel(String question) {
        this.questionLabel.setText(question);
    }

    void displayQuestionNumberLabel(int nb) {
        this.questionNumberLabel.setText("Question " + nb + "/10");
    }

    void displayScoreText(int score) {
        this.scoreText.setText("Score : " + score);
    }

    void displayTimeLeftText() {
        this.timeLeftText.setText(game.getTimeLeft() + "");
    }

    private void displayGameOverScoreLabel() {
        this.gameOverScoreLabel.setText(game.getScore() + "/10");
    }

    /**
     * Affiche un commentaire en fonction du score du joueur à la fin d'une partie.
     */
    private void displayGameOverCommentLabel() {
        String comment;
        int score = game.getScore();
        gameOverScoreLabel.setTextFill(Paint.valueOf("GREEN"));

        if (score < 4) {
            gameOverScoreLabel.setTextFill(Paint.valueOf("RED"));
            comment = "La culture G et vous ça ne fait pas deux, n'est-ce pas ?";
        } else if (score < 6) {
            gameOverScoreLabel.setTextFill(Paint.valueOf("ORANGE"));
            comment = "Pas mal, mais peut mieux faire.";
        } else if (score < 9) {
            comment = "Un score honorable ! Bravo :)";
        } else {
            comment = "Vous avez triché, n'est-ce pas ?";
        }

        this.gameOverCommentLabel.setText(comment);
    }

}