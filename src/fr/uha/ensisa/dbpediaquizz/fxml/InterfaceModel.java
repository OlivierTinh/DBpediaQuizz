package fr.uha.ensisa.dbpediaquizz.fxml;

import fr.uha.ensisa.dbpediaquizz.questions.Question;
import fr.uha.ensisa.dbpediaquizz.questions.QuestionFactory;
import fr.uha.ensisa.dbpediaquizz.util.Constantes;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import java.util.Timer;
import java.util.TimerTask;

class InterfaceModel {

    private InterfaceController controller;

    private Timer timer;
    private final int[] timeLeft = {Constantes.TEMPS_RESTANT};

    private Question question;
    private int score;
    private int questionNumber;

    InterfaceModel(InterfaceController controller) {
        this.controller = controller;
        score = 0;
        questionNumber = 0;
    }

    /**
     * Lance la partie en réinitialisant les données et en générant une nouvelle question.
     */
    void start() {
        resetData();
        generateNewQuestion();
        controller.displayScoreText(score);
        controller.displayQuestionNumberLabel(++questionNumber);
        startTimer();
    }

    /**
     * Démarre un timer qui se décrémente de seconde en seconde.
     * Lorsque le temps restant est nul, on passe à la prochaine question.
     */
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                controller.displayTimeLeftText();
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
     * Récupère la réponse en fonction du bouton cliqué, regarde si la réponse est bonne puis passe
     * à la prochaine question.
     *
     * @param event événement attrapé par le clic de la souris
     */
    void handleAnswerButton(ActionEvent event) {
        handleAnswer(event);
        handleQuestionNumber();
        generateNewQuestion();
        setTimeLeft();
    }

    /**
     * Vérifie si la réponse est correcte. Si c'est le cas, on incrémente le score de 1.
     * Pour cela, on analyse event sous forme de String en sachant que l'on a initialement quelque chose de la forme :
     * JFXButton[id=answer3, styleClass=button jfx-button]'Football Club des Girondins de Bordeaux'
     * @variable answer donne ici : Football Club des Girondins de Bordeaux
     *
     * @param event événement attrapé par le clic de la souris
     */
    private void handleAnswer(ActionEvent event) {
        String answer = event.getSource().toString().replaceAll(".*]'(.*)'", "$1");
        if (question.isCorrect(answer))
            controller.displayScoreText(++score);
    }

    /**
     * Incrémente le nombre de questions posées et le met à jour.
     * Si on atteint le maximum, on finit la partie.
     */
    private void handleQuestionNumber() {
        if (questionNumber == Constantes.NB_QUESTIONS) {
            controller.displayGameOver();
            timer.cancel();
        } else {
            controller.displayQuestionNumberLabel(++questionNumber);
        }
    }

    /**
     * Met à zéro les données liées au jeu (score et nb de questions posées)
     */
    private void resetData() {
        score = 0;
        questionNumber = 0;
    }

    /**
     * On génère une nouvelle question qu'on affiche sur l'interface graphique.
     */
    private void generateNewQuestion() {
        setQuestion(QuestionFactory.createQuestion());
        question.displayQuestion(controller);
    }

    int getScore() {
        return score;
    }

    Question getQuestion() {
        return question;
    }

    private void setQuestion(Question question) {
        this.question = question;
    }

    int getTimeLeft() {
        return timeLeft[0];
    }

    private void setTimeLeft() {
        this.timeLeft[0] = Constantes.TEMPS_RESTANT;
    }
}