package pl.setlikD.memorygame.level;

import pl.setlikD.memorygame.ConsoleColors;

import java.util.Optional;
import java.util.Scanner;

public class Level {
    private static final Scanner scanner = new Scanner(System.in);

    public void initLevel() {
        final boolean[] flag = {true};
        while (flag[0]) {
            System.out.println(ConsoleColors.BLACK_BRIGHT + "Please choose level: " + ConsoleColors.RESET);
            for (LevelOption value : LevelOption.values()) {
                System.out.println(ConsoleColors.CYAN_BRIGHT + "#" + value.getDesc() + ConsoleColors.RESET);
            }
            String level = scanner.nextLine();
            Optional<LevelOption> option = LevelOption.fromConsole(level);
            option.ifPresentOrElse(
                    levelOption -> {
                        levelOption.getSelectLevel().chooseLevel();
                        flag[0] = false;
                    },
                    () -> System.out.println(ConsoleColors.RED + "Invalid Statement !" + ConsoleColors.RESET)
            );
        }
    }
}
