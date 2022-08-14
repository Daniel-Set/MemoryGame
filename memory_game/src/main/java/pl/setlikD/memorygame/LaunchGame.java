package pl.setlikD.memorygame;

import pl.setlikD.memorygame.game.Game;

class LaunchGame {

    public static void main(String[] args) {

        Game game = Game.getInstance();
        game.initGame();

    }
}
