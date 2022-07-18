package pl.setlikD.Motorola;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static String[][] board;
    private static final int EASY = 4;
    private static final int HARD = 8;
    private static final int GUESS_CHANCES_EASY = 10;
    private static final int GUESS_CHANCES_HARD = 15;
    private static Game instance;

    private Game() {
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private Player player;

    public void initGame() {

        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Welcome to the MEMORY-GAME!" + ConsoleColors.RESET);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(ConsoleColors.BLACK_BRIGHT + "Press n for new game, q to quit, print TOP10 best records l" + ConsoleColors.RESET);
            String newGame = scanner.nextLine();
            if (newGame.equals("q")) {
                exitGame();
            } else if (newGame.equals("n")) {
                System.out.print(ConsoleColors.BLUE_BRIGHT + "Enter your name: " + ConsoleColors.RESET);
                String name = scanner.nextLine();
                player = new Player(name, LocalDate.now(), LocalTime.now());
                selectLevel();
            } else if (newGame.equals("l")) {
                Player.top10Printer(FileLoader.printTop10());

            } else {
                System.out.println(ConsoleColors.RED + "Invalid Character !" + ConsoleColors.RESET);
            }
        }

    }

    private void selectLevel() {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            System.out.println(ConsoleColors.BLACK_BRIGHT + "Please choose level: easy/hard ?" + ConsoleColors.RESET);
            String level = scanner.nextLine();
            if (level.equals("easy")) {
                createBoard(EASY);
                flag = false;
                startGame(EASY);
            } else if (level.equals("hard")) {
                createBoard(HARD);
                flag = false;
                startGame(HARD);
            } else {
                System.out.println(ConsoleColors.RED + "Invalid Statement !" + ConsoleColors.RESET);
            }
        }

    }

    private static void exitGame() {
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Bye, Bye :)");
        System.exit(42);
    }

    private void startGame(int level) {

        int guessChances;
        String levelName = "";

        if (level == EASY) {
            guessChances = 10;
            levelName = "easy";

            runningGame(guessChances, levelName, level);
        }

        if (level == HARD) {
            guessChances = 15;
            levelName = "hard";
            runningGame(guessChances, levelName, level);
        }

    }

    private void runningGame(int guessChances, String levelName, int level) {
        List<String> wordsList = Card.shuffleCards(Card.getCards(FileLoader.loadWordsList(), level));
        List<String> rowA = wordsList.subList(0, level);
//        System.out.println(rowA);
        List<String> rowB = wordsList.subList(level, wordsList.size());
//        System.out.println(rowB);

        while (guessChances != 0) {
            System.out.println(ConsoleColors.BLACK_BRIGHT + "Level: " + levelName + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLACK_BRIGHT + "Guess chances: " + guessChances + ConsoleColors.RESET);
            System.out.println();
            displayBoard(board);
            System.out.println(ConsoleColors.BLACK_BRIGHT + "Choose the card to uncover, ex. A1" + ConsoleColors.RESET);
            String regex = setRegex(level);

            String regexValidated1 = regexValidator(regex);
            String str1 = regexValidated1.toUpperCase();
            int row1 = str1.charAt(0) == 'A' ? 0 : 1;
            int column1 = Integer.parseInt(String.valueOf(str1.charAt(1))) - 1;
            List<String> cards1 = row1 == 0 ? rowA : rowB;

            String card1 = setCard(row1, column1, cards1);
            displayBoard(board);
            System.out.println(ConsoleColors.BLACK_BRIGHT + "Choose the card to uncover, ex. A1" + ConsoleColors.RESET);

            String regexValidated2 = regexValidator(regex);
            String str2 = regexValidated2.toUpperCase();
            int row2 = str2.toUpperCase().charAt(0) == 'A' ? 0 : 1;
            int column2 = Integer.parseInt(String.valueOf(str2.charAt(1))) - 1;
            List<String> cards2 = row2 == 0 ? rowA : rowB;

            String card2 = setCard(row2, column2, cards2);
            displayBoard(board);


            if (card1.equals(card2) && !(row1 == row2 && column1 == column2)) {
                System.out.println(ConsoleColors.GREEN_BRIGHT + "Correct :)" + ConsoleColors.RESET);
            } else if (row1 == row2 && column1 == column2) {
                System.out.println(ConsoleColors.YELLOW_BRIGHT + "Already Entered!" + ConsoleColors.RESET);
                board[row1][column1] = "  X  ";
            } else {
                System.out.println((ConsoleColors.RED_BRIGHT + "False :(" + ConsoleColors.RESET));
                board[row1][column1] = "  X  ";
                board[row2][column2] = "  X  ";
            }

            guessChances--;

            if (wonGame()) {
                if (level == EASY) {
                    System.out.println(ConsoleColors.YELLOW_BRIGHT + Player.printCongrats(player, GUESS_CHANCES_EASY - guessChances, LocalTime.now()) + ConsoleColors.RESET);
                } else {
                    System.out.println(ConsoleColors.YELLOW_BRIGHT + Player.printCongrats(player, GUESS_CHANCES_HARD - guessChances, LocalTime.now()) + ConsoleColors.RESET);
                }
                break;
            }

        }

    }

    private static String setCard(int row, int column, List<String> words) {
        board[row][column] = words.get(column);
        return words.get(column);
    }

    private static String regexValidator(String regex) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String cardToUncover = scanner.nextLine();
            if (cardToUncover.matches(regex)) {
                return cardToUncover;
            } else {
                System.out.println(ConsoleColors.RED + "Please select a correct coordinate !!!");
            }
        }
    }

    private static String setRegex(int level) {
        return String.format("^[A-Ba-b][1-%d]", level);
    }

    private static boolean wonGame() {
        for (String[] strings : board) {
            for (int j = 0; j < board[0].length; j++) {
                if (strings[j].equals("  X  ")) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void displayBoard(String[][] board) {

        if (board[0].length == EASY) {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\t\s1\t\t2\t\s\s\s3\t\s\s4" + ConsoleColors.RESET);
        }

        if (board[0].length == HARD) {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\t\s1\t\t2\t\s\s\s3\t\s\s4\t\t\s5\t\t6\t\s\s\s7\t\s\s8" + ConsoleColors.RESET);
        }

        for (int i = 0; i < board.length; i++) {
            if (i == 0) {
                System.out.print(ConsoleColors.PURPLE_BOLD_BRIGHT + "A" + ConsoleColors.RESET);
            } else {
                System.out.print(ConsoleColors.PURPLE_BOLD_BRIGHT + "B" + ConsoleColors.RESET);
            }
            for (int j = 0; j < board[i].length; j++) {
                System.out.print("  " + board[i][j]);
            }
            System.out.println();
        }

    }

    private static void createBoard(int size) {

        board = new String[2][size];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = "  X  ";
            }
        }
    }
}
