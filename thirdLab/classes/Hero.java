package classes;

import interfaces.IsItem;

public class Hero extends Human {

    public Hero(String name, int dnacode) {
        super(name, dnacode);
    }

    @Override
    public void takeItem(IsItem item) {
        for (int i = 0; i < feelings.length; i++) {
            if (items[i] == item && inHand == null) {
                IsItem itemRem = (IsItem) items[i];
                itemRem.removeThisItem(this);
                System.out.println("Герой " + this.getName() + " взял " + item);
                break;
            } else System.out.println("Герой " + this.getName() + " не имеет " + item.toString());
        }
    }

    @Override
    public void putItem(IsItem item) {
        if (inHand == item) {
            item.addThisItem(this);
            System.out.println("Герой " + this.getName() + " положил " + item);
        }
        System.out.println("У Героя " + this.getName() + " нет ничего в руке");
    }

    @Override
    public void changeLocation(String newLocation) {
        location.changeLocation(newLocation);
        System.out.println("Герой " + this.getName() + " поменял свою локацию на " + location.toString());
    }

    @Override
    public void changeCity(String newLocation, String newCity) {
        location.changeCity(newCity, newLocation);
        System.out.println("Герой " + this.getName() + " поменял свою локацию на " + location.toString());
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }


    @Override
    public void addFeeling(Feelings feel) {
        for (int i = 0; i < feelings.length; i++) {
            if (feelings[i] == null) {
                feelings[i] = feel;
                System.out.println("Герой " + this.getName() + " начал чувствовать " + feel.toString());
                break;
            }
        }
    }

    @Override
    public void dellFeeling(Feelings feel) {
        for (int i = 0; i < feelings.length; i++) {
            if (feelings[i] == feel) {
                feelings[i] = null;
                System.out.println("Герой " + this.getName() + " перестал чувствовать " + feel.toString());
                break;
            }
        }
    }

    public void Taste(Food food) {
        food.taste(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hero hero = (Hero) o;
        return (this.dnacode == hero.dnacode && this.feelings == hero.feelings && this.items == hero.items && this.location == hero.location && this.inHand == hero.inHand && this.getName() == hero.getName());
    }

    @Override
    public int hashCode() {
        return dnacode;
    }

    @Override
    public String toString() {
        return "Герой " + this.getName() + " присоединился к истории";
    }
}
