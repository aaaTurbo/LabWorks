package classes;

import interfaces.IsItem;

public class Money implements IsItem {
    private String money;
    private String type;

    public Money(String money, String type){
        this.money = money;
        this.type = type;
    }

    @Override
    public String toString(){
        return type + " " + money;
    }

    @Override
    public int hashCode(){
        return money.hashCode();
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money m = (Money) o;
        return this.type == m.type;
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
}
