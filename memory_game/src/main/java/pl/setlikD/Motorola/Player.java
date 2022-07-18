package pl.setlikD.Motorola;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
public class Player implements Comparable<Player> {

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
        if (players.size() >= 10) {
            for (Player player : players) {
                System.out.println("Name: " + player.getName() + " Date: " + player.getLocalDate() + " Guessing time: " + player.getTimeSolving() + " Guessing Tries: " + player.getChancesTaken());
            }
        } else {
            for (Player player : players) {
                System.out.println("Name: " + player.getName() + " Date:" + player.getLocalDate() + " Guessing time: " + player.getTimeSolving() + " Guessing Tries: " + player.getChancesTaken());

            }
        }
    }


    @Override
    public String toString() {
        return this.name + ", " + this.localDate + ", " + this.timeSolving + ", " + this.chancesTaken;
    }

    @Override
    public int compareTo(Player p) {
        if (chancesTaken > p.chancesTaken) {
            return 1;
        } else if (chancesTaken < p.chancesTaken) {
            return -1;
        }

        if (timeSolving > p.timeSolving) {
            return 1;
        } else if (timeSolving < p.timeSolving) {
            return -1;

        }
        return 0;
    }
}
