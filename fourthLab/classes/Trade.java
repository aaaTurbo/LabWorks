package classes;

import interfaces.IsItem;
import myExeptions.NoItemExeption;

public class Trade {
    final private String trade = "обмен";

    public Trade() {
    }

    public void commitTrade(Hero f, Hero s, IsItem gv, IsItem gt) throws NoItemExeption {
        for (int i = 0; i < f.numberOfItems; i++) {
            if (gv.equals(f.items[i])) {
                for (int j = 0; j < s.numberOfItems; i++) {
                    if (gt.equals(s.items[j])) {
                        gv.addThisItem(s);
                        gv.removeThisItem(f);
                        gt.addThisItem(f);
                        gt.removeThisItem(s);
                        System.out.println("Герой " + f.getName() + " выполнил(а) обмен с Героем " + s.getName() + ", отдавая " + gv + " и получая " + gt);
                        break;
                    } else throw new NoItemExeption("Предмет обмена у Героя " + s.getName() + " не найден!");
                }
                break;
            }
            if (i == f.numberOfItems - 1) {
                throw new NoItemExeption("Предмет обмена у Героя " + f.getName() + " не найден!");
            }
        }
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
