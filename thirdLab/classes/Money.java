package classes;

public class Money {
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
}
