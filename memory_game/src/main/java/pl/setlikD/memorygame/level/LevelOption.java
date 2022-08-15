package pl.setlikD.memorygame.level;

import java.util.Optional;

public enum LevelOption {

    EASY(4, 10, "easy", new EasyLevel()),
    MEDIUM(6, 12, "medium", new MediumLevel()),
    HARD(8, 15, "hard", new HardLevel());

    private final int size;
    private final int guessChances;
    private final String desc;
    private final SelectLevel selectLevel;

    LevelOption(int size, int guessChances, String desc, EasyLevel easyLevel) {
        this.size = size;
        this.guessChances = guessChances;
        this.desc = desc;
        this.selectLevel = easyLevel;
    }

    LevelOption(int size, int guessChances, String desc, HardLevel hardLevel) {
        this.size = size;
        this.guessChances = guessChances;
        this.desc = desc;
        this.selectLevel = hardLevel;
    }

    LevelOption(int size, int guessChances, String desc, MediumLevel mediumLevel) {
        this.size = size;
        this.guessChances = guessChances;
        this.desc = desc;
        this.selectLevel = mediumLevel;
    }


    public static Optional<LevelOption> fromConsole(String desc) {
        LevelOption[] values = values();
        for (LevelOption option : values) {
            if (option.getDesc().equals(desc)) return Optional.of(option);
        }
        return Optional.empty();
    }

    public  SelectLevel getSelectLevel() {
        return selectLevel;
    }

    public int getSize() {
        return size;
    }

    public int getGuessChances() {
        return guessChances;
    }

    public String getDesc() {
        return desc;
    }
}
