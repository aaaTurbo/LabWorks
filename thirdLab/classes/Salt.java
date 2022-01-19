package classes;

import interfaces.IsItem;
import interfaces.Tasteable;

public class Salt implements Tasteable, IsItem {
    final private String salt = "щепоть соли";

    public Salt() {
    }

    @Override
    public void addThisItem(Hero hero) {
        hero.items[hero.numberOfItems] = this;
        hero.numberOfItems++;
    }

    @Override
    public void removeThisItem(Hero hero) {
        for(int i=0; i < hero.numberOfItems; i++){
            if (this.equals(hero.items[i])){
                hero.items[i] = null;
                break;
            }
        }
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
    public void taste(Hero hero) {
        System.out.println("Герой " + hero.getName() + " пробует и выплевывает соль");
        hero.addFeeling(Feelings.DISGUST);
        hero.dellFeeling(Feelings.DISGUST);
    }
}
