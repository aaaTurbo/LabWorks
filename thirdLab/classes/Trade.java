package classes;

public class Trade {
    final private String trade = "обмен";

    public Trade() {
    }

    public String commitTrade(Hero f, Hero s, Object gv, int numgv, Object gt, int numgt) {
        return "Герой " + f.getName() + " выполнил(а) обмен с Героем " + s.getName() + ", отдавая " + numgv + " " + gv.toString() + " и получая " + numgt + " " + gt.toString();
    }

    @Override
    public String toString() {
        return " Действие " + trade;
    }

    @Override
    public int hashCode() {
        return trade.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return true;
    }
}
