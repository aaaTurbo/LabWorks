import classes.*;


public class Story {
    private String story = "Story Begins...\n\n";
    private Trade trade = new Trade();

    public void addHeroInStory(Object o) {
        if (o == (Hero) o) {
            story += o.toString();
            story += "\n";
        }
    }

    public void addFeelingInStory(Hero hero, Feelings feeling) {
        story += hero.addFeeling(feeling);
        story += "\n";
    }

    public void dellFeelingInStory(Hero hero, Feelings feeling) {
        story += hero.dellFeeling(feeling);
        story += "\n";
    }

    public void addMoveInStory(Hero hero, Move move, int repeats) {
        story += hero.doAction(move, repeats);
        story += "\n";
    }

    public void addTradeInStory(Hero f, Hero s, Object gv, int numgv, Object gt, int numgt) {
        story += trade.commitTrade(f, s, gv, numgv, gt, numgt);
        story += "\n";
    }

    public void addTasteMove(Hero hero, Object o) {
        if (o.getClass() == Food.class) {
            Food f = (Food) o;
            story += f.taste(hero);
            story += "\n";
        }
        if (o.getClass() == Salt.class) {
            Salt s = (Salt) o;
            story += s.taste(hero);
            story += "\n";
        }
        if (o.getClass() != Salt.class && o.getClass() != Food.class) {
            story += "Объект нельзя попробовать";
            story += "\n";
        }
    }

    public void addAddSaltMove(Hero hero, int times, Food food) {
        story += food.AddSalt(hero, times);
        story += "\n";
    }

    @Override
    public String toString() {
        story += "\n\nThe end of the Stoty...";
        return story;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Story st = (Story) o;
        return this.story == st.story;
    }

    @Override
    public int hashCode() {
        return story.hashCode();
    }

    public void startStory() {
        System.out.println(this.toString());
    }

    static public void main(String[] args) {
        Trade trade = new Trade();
        Story story = new Story();
        Salt salt = new Salt();
        Money santik = new Money("Сантик", "coin");
        Hero ponchik = new Hero("Пончик", 1);
        story.addHeroInStory(ponchik);
        story.addFeelingInStory(ponchik, Feelings.ENVY);
        Move handtopocket = new Move("потянуться рукой в карман");
        story.addMoveInStory(ponchik, handtopocket, 1);
        Move takesatk = new Move("взять " + salt.toString());
        story.addMoveInStory(ponchik, takesatk, 1);
        story.addFeelingInStory(ponchik, Feelings.GREEDY);
        Hero korotysh = new Hero("Коротышка", 2);
        story.addHeroInStory(korotysh);
        story.addTradeInStory(korotysh, ponchik, santik, 1, salt, 1);
        Hero others = new Hero("Другие посетители", 3);
        story.addHeroInStory(others);
        Move startToCome = new Move("начали подходить");
        story.addMoveInStory(others, startToCome, 1);
        Hero errybody = new Hero("Каждый", 4);
        story.addTradeInStory(errybody, ponchik, santik, 1, salt, 1);
        story.addFeelingInStory(ponchik, Feelings.PLEASURE);
        Move seeSantikGrow = new Move("смотрит как стопка " + santik.toString() + " растет");
        story.addMoveInStory(ponchik, seeSantikGrow, 1);
        Hero luntik = new Hero("Лунатик", 5);
        story.addHeroInStory(luntik);
        story.addTasteMove(luntik, salt);
        story.addFeelingInStory(luntik, Feelings.DISGUST);
        Hero another = new Hero("Другой", 6);
        story.addHeroInStory(another);
        story.addTradeInStory(another, ponchik, santik, 10, salt, 10);
        Food soup = new Food("Суп");
        story.addAddSaltMove(another, 10, soup);
        story.addTasteMove(another, soup);
        story.startStory();
    }
}
