package classes;

public enum Feelings {

    GREEDY("жадность"),
    ENVY("зависть"),
    DISGUST("отвращение"),
    CURIOSITY("любопытство"),
    PLEASURE("удовольствие");

    private final String feeling;

    Feelings(String feeling) {
        this.feeling = feeling;
    }

    @Override
    public String toString() {
        return "Чувство " + feeling;
    }
}
