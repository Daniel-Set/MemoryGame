package pl.setlikD.memorygame.game;

import pl.setlikD.memorygame.ConsoleColors;
import pl.setlikD.memorygame.player.Player;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class NewGame {

    private static final Scanner scanner = new Scanner(System.in);
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayerData() {
        System.out.print(ConsoleColors.BLUE_BRIGHT + "Enter your name: " + ConsoleColors.RESET);
        String name = scanner.nextLine();
        player = new Player(name, LocalDate.now(), LocalTime.now());
    }

}
