package pl.setlikD.memorygame.game;

import pl.setlikD.memorygame.file_card.Card;
import pl.setlikD.memorygame.ConsoleColors;
import pl.setlikD.memorygame.file_card.FileLoader;
import pl.setlikD.memorygame.level.Level;
import pl.setlikD.memorygame.level.LevelOption;
import pl.setlikD.memorygame.player.Player;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static String[][] board;
    private static Game instance;
    private final NewGame brandNewGame = new NewGame();
    private final Level level = new Level();

    private Game() {
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private static void exitGame() {
        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Bye, Bye :)");
        System.exit(42);
    }

    private static int[] selectingRow(String regex) {
        String regexValidated = regexValidator(regex);
        String str = regexValidated.toUpperCase();
        int row = str.charAt(0) == 'A' ? 0 : 1;
        int column = Integer.parseInt(String.valueOf(str.charAt(1))) - 1;
        return new int[]{row, column};
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
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .noneMatch(x -> x.equals("  X  "));
    }

    private static int flippedCardsCounter(String card) {
        return (int) Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(x -> x.equals(card))
                .count();
    }

    private static int blankCardCounter() {
        return (int) Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(x -> x.equals("  X  "))
                .count();
    }

    private static void displayBoard(String[][] board) {
        if (board[0].length == LevelOption.EASY.getSize()) {
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "\t\s1\t\t2\t\s\s\s3\t\s\s4" + ConsoleColors.RESET);
        }

        if (board[0].length == LevelOption.HARD.getSize()) {
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

    public static void createBoard(int size) {
        int row = 2;
        board = new String[row][size];
        Arrays.setAll(board, x -> {
            Arrays.fill(board[x], "  X  ");
            return board[x];
        });
    }

    private static void noBagCardsValidation(int row1, int column1, String card1, int row2, int column2, String card2) {
        if (card1.equals(card2) && !(row1 == row2 && column1 == column2)) {
            System.out.println(ConsoleColors.GREEN_BRIGHT + "Correct :)" + ConsoleColors.RESET);
        } else {
            System.out.println((ConsoleColors.RED_BRIGHT + "False :(" + ConsoleColors.RESET));
            board[row1][column1] = "  X  ";
            board[row2][column2] = "  X  ";
        }
    }

    private static void cardsValidation(int row1, int column1, String card1, int row2, int column2, String card2) {
        if (!bagValidation(row1, column1, card1, row2, column2, card2)) {
            noBagCardsValidation(row1, column1, card1, row2, column2, card2);
        }
    }

    private static boolean bagValidation(int row1, int column1, String card1, int row2, int column2, String card2) {
        if (row1 == row2 && column1 == column2 && card1.equals(card2)) {
            System.out.println(ConsoleColors.YELLOW_BRIGHT + "Already Entered!" + ConsoleColors.RESET);
            if (!(blankCardCounter() % 2 == 0)) {
                board[row2][column2] = "  X  ";
            }
            return true;
        }
        if (flippedCardsCounter(card1) == 2 && !card1.equals(card2)) {
            System.out.println(ConsoleColors.RED_BRIGHT + "One of the cards is already uncovered" + ConsoleColors.RESET);
            board[row2][column2] = "  X  ";
            return true;
        }
        if (flippedCardsCounter(card2) == 2 && !card1.equals(card2)) {
            System.out.println(ConsoleColors.RED_BRIGHT + "One of the cards is already uncovered" + ConsoleColors.RESET);
            board[row1][column1] = "  X  ";
            return true;
        }
        return false;
    }


    private static String getStringRegex(int guessChances, String levelName, int level) {
        System.out.println(ConsoleColors.BLACK_BRIGHT + "Level: " + levelName + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLACK_BRIGHT + "Guess chances: " + guessChances + ConsoleColors.RESET);
        System.out.println();
        displayBoard(board);
        System.out.println(ConsoleColors.BLACK_BRIGHT + "Choose the card to uncover, ex. A1" + ConsoleColors.RESET);
        return setRegex(level);
    }

    private static String findCard(int row, int column, List<String> rowA, List<String> rowB) {
        List<String> cards = row == 0 ? rowA : rowB;
        return setCard(row, column, cards);
    }

    public void initGame() {
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + "Welcome to the MEMORY-GAME!" + ConsoleColors.RESET);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(ConsoleColors.BLACK_BRIGHT + "Press n for new game, q to quit, print TOP10 best records l" + ConsoleColors.RESET);
            String newGame = scanner.nextLine();
            switch (newGame) {
                case "q" -> exitGame();
                case "n" -> {
                    brandNewGame.setPlayerData();
                    level.initLevel();
                }
                case "l" -> Player.top10Printer(FileLoader.sortedPlayers(FileLoader.loadPlayers()));
                default -> System.out.println(ConsoleColors.RED + "Invalid Character !" + ConsoleColors.RESET);
            }
        }

    }

    public void runningGame(int guessChances, String levelName, int level) {
        List<String> wordsList = Card.shuffleCards(Card.getCards(FileLoader.loadWordsList(), level));
        List<String> rowA = wordsList.subList(0, level);
        List<String> rowB = wordsList.subList(level, wordsList.size());
        System.out.println(rowA + " " + rowB);
        while (guessChances != 0) {
            String regex = getStringRegex(guessChances, levelName, level);
            int[] intsOne = selectingRow(regex);
            int row1 = intsOne[0];
            int column1 = intsOne[1];
            String card1 = findCard(row1, column1, rowA, rowB);
            displayBoard(board);
            System.out.println(ConsoleColors.BLACK_BRIGHT + "Choose the card to uncover, ex. A1" + ConsoleColors.RESET);
            int[] intsTwo = selectingRow(regex);
            int row2 = intsTwo[0];
            int column2 = intsTwo[1];
            String card2 = findCard(row2, column2, rowA, rowB);
            displayBoard(board);
            cardsValidation(row1, column1, card1, row2, column2, card2);
            guessChances--;
            if (isWinning(guessChances, levelName)) {
                break;
            }
        }

    }

    private boolean isWinning(int guessChances, String levelName) {
        if (wonGame()) {
            System.out.println(ConsoleColors.YELLOW_BRIGHT + Player.printCongrats(brandNewGame.getPlayer(), LevelOption.fromConsole(levelName).get().getGuessChances() - guessChances, LocalTime.now()) + ConsoleColors.RESET);
            return true;
        }

        if (guessChances == 0) {
            System.out.println(ConsoleColors.RED + "Game Over" + ConsoleColors.RESET);
        }
        return false;
    }
}

