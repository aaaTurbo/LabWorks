package classes;

public enum Feelings {

    GREEDY("жадность"),
    ENVY("зависть"),
    DISGUST("отвращение"),
    PLEASURE("удовольствие");

    private String feeling;

    private Feelings(String feeling) {
        this.feeling = feeling;
    }

    @Override
    public String toString() {
        return "Чувство " + feeling;
    }
}
