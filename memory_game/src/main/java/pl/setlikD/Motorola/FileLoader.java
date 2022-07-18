package pl.setlikD.Motorola;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
        Path path = Paths.get("src/main/resources/best_scores/best-scores.txt");
        try {
            Files.writeString(path, player.toString());
        } catch (IOException ex) {
            System.out.println("Could not save file !");
        }
    }

    public static List<Player> printTop10() {
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

        Collections.sort(top10);
        return top10;
    }
}
