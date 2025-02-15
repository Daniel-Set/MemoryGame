package pl.setlikD.memorygame.file_card;

import java.util.*;

public class Card {
    private static final Random random = new Random();
    public static List<String> getCards(List<String> allWords, int numberOfCards) {
        LinkedHashSet<String> cards = new LinkedHashSet<>();
        while (cards.size() != numberOfCards) {
            int index = random.nextInt(allWords.size());
            cards.add(allWords.get(index));
        }
        List<String> wordsList = new ArrayList<>(cards);
        wordsList.addAll(cards);
        return wordsList;
    }

    public static List<String> shuffleCards(List<String> cards) {
        Collections.shuffle(cards);
        return cards;
    }


}
