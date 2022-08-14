package pl.setlikD.memorygame.file_card;

import pl.setlikD.memorygame.player.Player;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class FileLoader {

    public static List<String> loadWordsList() {
        List<String> cardsList = new ArrayList<>();
        String fileURL = "src/main/resources/words/Words.txt";
        File file = new File(fileURL);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String record = scanner.nextLine();
                cardsList.add(record);
            }
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return cardsList;
    }


    public static void saveRecord(Player player) {
        String fileName = "src/main/resources/best_scores/best-scores.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(player.toString() + "\n");
        } catch (IOException ex) {
            System.out.println("Could not save file !");
        }
    }

    public static List<Player> loadPlayers() {
        List<Player> top10 = new ArrayList<>();
        String fileURL = "src/main/resources/best_scores/best-scores.txt";
        var file = new File(fileURL);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String record = scanner.nextLine();
                String[] strings = record.split(", ");
                top10.add(new Player(strings[0], LocalDate.parse(strings[1]), Long.parseLong(strings[2]), Integer.parseInt(strings[3])));
            }
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception.getMessage());
        }
        return top10;
    }

    public static List<Player> sortedPlayers(List<Player> top10) {
        return top10.stream().sorted(Comparator.comparing(Player::getChancesTaken)
                .thenComparing(Player::getTimeSolving)).toList();
    }
}
