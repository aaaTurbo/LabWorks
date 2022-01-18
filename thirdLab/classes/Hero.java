package classes;

public class Hero extends Human {

    public Hero(String name, int dnacode) {
        super(name, dnacode);
    }

    @Override
    public String doAction(Move move, int repeatTimes) {
        return "Герой " + this.getName() + " совершает" + move.toString() + " " + repeatTimes + " раз";
    }

    @Override
    public String addFeeling(Feelings feel) {
        return "Герой " + this.getName() + " начал чувствовать " + feel.toString();
    }

    @Override
    public String dellFeeling(Feelings feel) {
        return "Герой " + this.getName() + " перествал чувствовать " + feel.toString();
    }

    public String Taste(Food food) {
        return food.taste(this);
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
        return this.dnacode == hero.dnacode;
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
