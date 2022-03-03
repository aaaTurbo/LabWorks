package classes;

import interfaces.HaveFeelings;
import interfaces.IsItem;

public abstract class Human implements HaveFeelings {
    private final String name;
    final protected int dnacode;
    protected Feelings[] feelings = new Feelings[5];
    public Object[] items = new Object[100];
    public int numberOfItems = 0;
    protected Location location;
    protected IsItem inHand;

    public Human(String name, int dnacode) throws NullPointerException {
        NullPointerException exception = new NullPointerException();
        if (name == null) throw exception;
        this.dnacode = dnacode;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract public void takeItem(IsItem item);

    abstract public void putItem(IsItem item);

    abstract public void changeLocation(String newLocation);

    abstract public void changeCity(String newLocation, String newCity);

    abstract public void setLocation(Location location);
}
