package pl.setlikD.memorygame.file_card;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


class FileLoaderTest {

    @Test
    void shouldCreatWordsListFromExistFile() {
        //given+when
        List<String> wordsList = FileLoader.loadWordsList();
        //then
        assertThat(wordsList.size(), equalTo(100));
    }

    @Test
    void loadPlayers() {
    }

    @Test
    void sortedPlayers() {
    }
}