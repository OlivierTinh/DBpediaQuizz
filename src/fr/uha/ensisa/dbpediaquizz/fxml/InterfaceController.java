package fr.uha.ensisa.dbpediaquizz.fxml;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToolbar;
import fr.uha.ensisa.dbpediaquizz.util.Constantes;
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
    private JFXButton startSoloGameButton;

    @FXML
    private JFXButton newMultiGameButton;

    @FXML
    private JFXButton exitButton;


    /* --- Multiplayer --- */

    @FXML
    private Pane multiplayerPanel;

    @FXML
    private Label invalidPlayerNameLabel;

    @FXML
    private JFXTextField player1Field;

    @FXML
    private JFXTextField player2Field;

    @FXML
    private JFXTextField player3Field;

    @FXML
    private JFXTextField player4Field;

    @FXML
    private JFXButton startMultiGameButton;

    @FXML
    private JFXButton multiToMenuButton;


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
    private Label gameOverSentenceLabel;

    @FXML
    private Label gameOverScoreLabel;

    @FXML
    private Label gameOverCommentLabel;

    @FXML
    private JFXButton gameOverReplay;

    @FXML
    private JFXButton gameOverNext;

    @FXML
    private JFXButton gameOverMenu;

    @FXML
    private JFXButton gameOverExit;


    /* --- Sidebar Panel --- */

    @FXML
    private Pane sidebarPanel;

    @FXML
    private Pane sidebarGamePanel;

    @FXML
    private Pane sidebarScorePanel;

    @FXML
    private Text currentPlayerNameText;

    @FXML
    private Text player1ScoreText;

    @FXML
    private Text player2ScoreText;

    @FXML
    private Text player3ScoreText;

    @FXML
    private Text player4ScoreText;


    /* --- FXML Functions --- */

    @FXML
    void handleNewGameButton() {
        displayGameMenu();
        game.setMode(Constantes.MODE_SOLO);
        game.start();
    }

    @FXML
    void handleNewMultiGameButton() {
        if (invalidPlayerNames()) {
            invalidPlayerNameLabel.setVisible(true);
            return;
        }

        System.out.println("Création du mode multijoueur...");

        game.setMode(Constantes.MODE_MULTIJOUEUR);
        invalidPlayerNameLabel.setVisible(false);

        game.createPlayer(player1Field.getText());
        game.createPlayer(player2Field.getText());
        game.createPlayer(player3Field.getText());
        game.createPlayer(player4Field.getText());

        setSidebarPlayerScore();
        sidebarScorePanel.setVisible(true);

        displayGameMenu();
        game.resetTurn();
        game.start();
        displayCurrentPlayerName();
    }

    @FXML
    void handleMultiGameSettingsButton() {
        multiplayerPanel.setVisible(true);
    }

    @FXML
    void handleGiveUpButton() {
        displayGameOver();
        game.reset();
    }

    @FXML
    void handleNextTurnButton() {
        game.nextTurn();
        displayGameMenu();
        game.start();
        displayCurrentPlayerName();
    }

    @FXML
    void handleMenuButton() {
        displayMenu();
        game.setMode(Constantes.MODE_SOLO);
        game.reset();
        game.players.clear();
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
        multiplayerPanel.setVisible(false);
        scoreBar.setVisible(false);
        sidebarGamePanel.setVisible(false);
        sidebarScorePanel.setVisible(false);
    }

    /**
     * Affiche le panel de la partie courante.
     */
    private void displayGameMenu() {
        menuPanel.setVisible(false);
        gamePanel.setVisible(true);
        gameOverPanel.setVisible(false);
        scoreBar.setVisible(true);
        sidebarGamePanel.setVisible(true);
        displayScoreText(game.getScore());
        displayQuestionNumberLabel(game.getQuestionNumber());
    }

    /**
     * Affiche le menu de game over.
     */
    void displayGameOver() {
        System.out.println("Game over");
        System.out.println("turn: " + game.playerTurn);
        System.out.println("size: " + game.players.size());

        if (game.mode == Constantes.MODE_MULTIJOUEUR) {
            setSidebarPlayerScore();

            if (game.playerTurn == game.players.size() - 1) {
                gameOverNext.setVisible(false);
            } else {
                gameOverNext.setVisible(true);
            }
            gameOverReplay.setVisible(false);
        } else {
            gameOverNext.setVisible(false);
            gameOverReplay.setVisible(true);
        }

        menuPanel.setVisible(false);
        gamePanel.setVisible(false);
        gameOverPanel.setVisible(true);
        scoreBar.setVisible(false);
        sidebarGamePanel.setVisible(false);
        displayGameOverSentenceLabel();
        displayGameOverScoreLabel();
        displayGameOverCommentLabel();
    }

    /**
     * Utilisé en MODE_MULTIJOUEUR.
     * Vérifie si le nom des deux premiers joueurs est vide - auquel cas l'utilisateur recevra
     * une notification.
     */
    private boolean invalidPlayerNames() {
        return player1Field.getText().isEmpty() && player2Field.getText().isEmpty();
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

    void setSidebarPlayerScore() {
        switch (game.players.size()) {
            case 4:
                player4ScoreText.setVisible(true);
                player4ScoreText.setText(game.players.get(3) + ": " + game.players.get(3).score);
            case 3:
                player3ScoreText.setVisible(true);
                player3ScoreText.setText(game.players.get(2) + ": " + game.players.get(2).score);
            default:
                player2ScoreText.setText(game.players.get(1) + ": " + game.players.get(1).score);
                player1ScoreText.setText(game.players.get(0) + ": " + game.players.get(0).score);
                break;
        }
    }

    private void displayCurrentPlayerName() {
        this.currentPlayerNameText.setText(game.player.toString());
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

    private void displayGameOverSentenceLabel() {
        if (game.mode == Constantes.MODE_MULTIJOUEUR)
            this.gameOverSentenceLabel.setText("Le score de " + game.player + " est de");
        else
            this.gameOverSentenceLabel.setText("Votre score est de");
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