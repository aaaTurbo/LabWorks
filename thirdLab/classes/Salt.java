package classes;

import interfaces.Tasteable;

public class Salt implements Tasteable {
    final private String salt = "щепоть соли";

    public Salt() {
    }

    @Override
    public String toString() {
        return "Предмет " + salt;
    }

    @Override
    public int hashCode() {
        return salt.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return true;
    }

    @Override
    public String taste(Hero hero) {
        return "Герой " + hero.getName() + " пробует и выплевывает соль";
    }
}
