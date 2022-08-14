package pl.setlikD.memorygame.level;

import pl.setlikD.memorygame.game.Game;

class HardLevel implements SelectLevel {

    @Override
    public void chooseLevel() {
        Game.createBoard(LevelOption.HARD.getSize());
        startGame();
    }

    @Override
    public void startGame() {
        Game.getInstance().runningGame(LevelOption.HARD.getGuessChances(), LevelOption.HARD.getDesc(), LevelOption.HARD.getSize());
    }
}
