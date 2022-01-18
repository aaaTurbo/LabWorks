package classes;

import interfaces.HaveFeelings;

public abstract class Human implements HaveFeelings {
    private String name;
    final protected int dnacode;

    public Human(String name, int dnacode) {
        this.dnacode = dnacode;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract public String doAction(Move move, int repeatTimes);

}
