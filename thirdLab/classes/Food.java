package classes;

import interfaces.Tasteable;

public class Food implements Tasteable {
    private String food;
    private int levelOfSalt = 0;

    public Food(String name) {
        food = name;
    }

    @Override
    public String taste(Hero hero) {
        if (levelOfSalt == 0) {
            return "Герой " + hero.getName() + " пробует Еду " + food + " и говорит что она не соленая";
        }
        if (levelOfSalt == 1 || levelOfSalt == 2) {
            return "Герой " + hero.getName() + " пробует Еду " + food + " и говорит что соли в самый раз";
        }
        if (levelOfSalt > 2) {
            return "Герой " + hero.getName() + " пробует Еду " + food + " и выплевывет, говоря что она пересолена";
        }
        return "error";
    }

    public String AddSalt(Hero hero, int times) {
        for (int i = 0; i < times; i++) {
            levelOfSalt++;
        }
        return "Герой " + hero.getName() + " солит Еду " + food + " " + times + "раз";
    }

    @Override
    public String toString() {
        return "Еда " + food;
    }

    @Override
    public int hashCode() {
        return food.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Food f = (Food) o;
        return this.levelOfSalt == f.levelOfSalt;
    }
}
