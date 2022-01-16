public abstract class Human implements HaveFeelings, AbleToAct{
    protected String name;
    final protected int dnacode;

    public Human(String name, int dnacode){
        this.dnacode = dnacode;
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
