package fr.uha.ensisa.dbpediaquizz.fxml;

class Player {

    String name;
    int score = 0;

    Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
