package pl.setlikD.memorygame.level;

import pl.setlikD.memorygame.game.Game;

public class MediumLevel implements SelectLevel {
    @Override
    public void chooseLevel() {
        Game.createBoard(LevelOption.MEDIUM.getSize());
        startGame();

    }
    @Override
    public void startGame() {
        Game.getInstance().runningGame(LevelOption.MEDIUM.getGuessChances(), LevelOption.MEDIUM.getDesc(), LevelOption.MEDIUM.getSize());

    }
}
