package pl.setlikD.memorygame.player;

import lombok.Getter;
import lombok.Setter;
import pl.setlikD.memorygame.file_card.FileLoader;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
public
class Player {

    private String name;
    private LocalDate localDate;
    private LocalTime localTime;
    private int chancesTaken;
    private long timeSolving;

    public Player(String name, LocalDate localDate, LocalTime localTime) {
        this.name = name;
        this.localDate = localDate;
        this.localTime = localTime;

    }

    public Player(String name, LocalDate localDate, long timeSolving, int chancesTaken) {
        this.name = name;
        this.localDate = localDate;
        this.chancesTaken = chancesTaken;
        this.timeSolving = timeSolving;
    }

    public static String printCongrats(Player player, int chancesTaken, LocalTime endGame) {
        LocalTime startGame = player.localTime;
        player.setChancesTaken(chancesTaken);
        player.timeSolving = Duration.between(startGame, endGame).getSeconds();
        FileLoader.saveRecord(player);
        return String.format("You solved the memory game after %d chances. It took you %d seconds", player.getChancesTaken(), player.getTimeSolving());

    }

    public static void top10Printer(List<Player> players) {
        int listSizeTop10 = 10;
        if (players.size() >= listSizeTop10) {
            printPlayer(players.subList(0, listSizeTop10));
        } else {
            printPlayer(players);
        }
    }

    private static void printPlayer(List<Player> players) {
        for (Player player : players) {
            System.out.println("Name: " + player.getName() + " Date:" + player.getLocalDate() + " Guessing time: " + player.getTimeSolving() + " Guessing Tries: " + player.getChancesTaken());
        }
    }

    @Override
    public String toString() {
        return this.name + ", " + this.localDate + ", " + this.timeSolving + ", " + this.chancesTaken;
    }

}
