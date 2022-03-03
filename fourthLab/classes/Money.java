package classes;

import interfaces.IsItem;

public class Money implements IsItem {
    private final String money;
    private final String type;
    private Nominal nominalo = new Nominal(1);

    public Money(String money, String type) throws NullPointerException {
        NullPointerException exception = new NullPointerException();
        if (money == null || type == null) throw exception;
        this.money = money;
        this.type = type;
    }

    public void setNominalo(Nominal nominalo) {
        this.nominalo = nominalo;
    }

    class Nominal {
        private final int nominalo;

        public Nominal(int i) {
            nominalo = i;
        }
    }

    @Override
    public String toString() {
        return type + " " + money + " : номинал " + nominalo.nominalo;
    }

    @Override
    public int hashCode() {
        return money.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money m = (Money) o;
        return this.type == m.type && this.nominalo == m.nominalo;
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
}
