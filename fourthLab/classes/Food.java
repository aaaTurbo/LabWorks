package classes;

import interfaces.IsItem;
import interfaces.Tasteable;
import myExeptions.NoItemExeption;

public class Food implements Tasteable, IsItem {
    private final String food;
    private int levelOfSalt = 0;

    public Food(String name) throws NullPointerException {
        NullPointerException exception = new NullPointerException();
        if (name == null) throw exception;
        food = name;
    }

    @Override
    public void addThisItem(Hero hero) {
        hero.items[hero.numberOfItems] = this;
        hero.numberOfItems++;
    }

    @Override
    public void removeThisItem(Hero hero) {
        for (int i = 0; i < hero.numberOfItems; i++) {
            if (this.equals(hero.items[i])) {
                hero.items[i] = null;
                break;
            }
        }
    }

    @Override
    public void taste(Hero hero) {
        if (levelOfSalt == 0) {
            System.out.println("Герой " + hero.getName() + " пробует Еду " + food + " и говорит что она не соленая");
            hero.addFeeling(Feelings.DISGUST);
            hero.dellFeeling(Feelings.DISGUST);
        }
        if (levelOfSalt == 1 || levelOfSalt == 2) {
            System.out.println("Герой " + hero.getName() + " пробует Еду " + food + " и говорит что соли в самый раз");
            hero.addFeeling(Feelings.PLEASURE);
            hero.dellFeeling(Feelings.PLEASURE);
        }
        if (levelOfSalt > 2) {
            System.out.println("Герой " + hero.getName() + " пробует Еду " + food + " и выплевывет, говоря что она пересолена");
            hero.addFeeling(Feelings.DISGUST);
            hero.dellFeeling(Feelings.DISGUST);
        }
    }

    public void addSalt(Hero hero) throws NoItemExeption {
        boolean haveSalt = false;
        Salt salt = new Salt();
        for (int i = 0; i < hero.numberOfItems; i++) {
            if (salt.equals(hero.numberOfItems)) {
                haveSalt = true;
                salt.removeThisItem(hero);
                levelOfSalt++;
                break;
            } else if (salt.equals(hero.inHand)) {
                haveSalt = true;
                hero.inHand.removeThisItem(hero);
                levelOfSalt++;
                break;
            } else {
                throw new NoItemExeption("Ошибка: У героя нет предмета Соль");
            }
        }
        if (haveSalt == false) {
            System.out.println("У коротышки нет соли");
        }
        if (haveSalt == true) {
            System.out.println("Герой " + hero.getName() + " солит " + this);
        }
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

    public void setLevelOfSalt(int levelOfSalt) {
        this.levelOfSalt = levelOfSalt;
    }
}
