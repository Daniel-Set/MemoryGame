package pl.setlikD.memorygame.level;

import pl.setlikD.memorygame.game.Game;

class EasyLevel implements SelectLevel {

    @Override
    public void chooseLevel() {
        Game.createBoard(LevelOption.EASY.getSize());
        startGame();
    }

    @Override
    public void startGame() {
        Game.getInstance().runningGame(LevelOption.EASY.getGuessChances(), LevelOption.EASY.getDesc(), LevelOption.EASY.getSize());
    }
}
