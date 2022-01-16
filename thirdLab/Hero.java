public class Hero extends Human{

    public Hero(String name, int dnacode) {
        super(name, dnacode);
    }

    @Override
    public String addFeeling(Feelings feel) {
        return "Герой " + name + " начал чувствовать " + feel.toString();
    }

    @Override
    public String dellFeeling(Feelings feel){
        return "Герой " +name + " перествал чувствовать " + feel.toString();
    }

    @Override
    public String doSimpleAction(Moves move) {
        return "Герой " + name + " совершает " + move.toString();
    }

    @Override
    public String doActionWithSmb(Moves move, Hero smb){
        return "Герой " + name + " совершает " + move.toString() + "Герою " + smb.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hero hero = (Hero) o;
        return this.dnacode == hero.dnacode;
    }

    @Override
    public int hashCode(){
        return dnacode;
    }

    @Override
    public String toString(){
        return "Герой " + name + " присоединился к истории";
    }
}
