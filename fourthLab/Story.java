import classes.*;
import myExeptions.NoItemExeption;


public class Story {
    private final int storyCode;
    private final String storyTeller;
    private static final Salt salt = new Salt();
    private static final Trade trade = new Trade();
    private final Hero[] heroes = new Hero[100];
    private final String storyName;

    public Story(int storyCode, String storyTeller, String storyName) throws NullPointerException {
        NullPointerException exception = new NullPointerException();
        if (storyTeller == null || storyName == null) throw exception;
        this.storyCode = storyCode;
        this.storyTeller = storyTeller;
        this.storyName = storyName;
    }

    public void collectInfo(Hero hero) {
        class InfoGetter {
            @Override
            public String toString() {
                return "----------Hero Info----------\nИмя: " + hero.getName() + "\nЛокация: " + hero.getLocation().toString() + "\n-----------------------------";
            }
        }
        InfoGetter infoGetter = new InfoGetter();
        System.out.println(infoGetter);
    }

    public void addHeroInStory(Hero hero) {
        for (int i = 0; i < heroes.length; i++) {
            if (heroes[i] == null) {
                heroes[i] = hero;
                System.out.println("Герой " + hero.getName() + " присоединился к истории");
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "История " + storyName + ";\nрассказчик: " + storyTeller + "\n";
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
        return (storyTeller == st.storyTeller && storyName == st.storyName);
    }

    @Override
    public int hashCode() {
        return storyCode;
    }

    private static class StoryStarter {
        public void storyStart(Story story) {
            System.out.println("----------Начинается история---------\n" + story.toString() + "-------------------------------------------");
        }
    }

    private static class StoryEnder {
        public void storyEnd(Story story) {
            System.out.println("------------Истоиия заканчивается------------");
        }
    }

    static public void main(String[] args) throws NoItemExeption {
        Story story = new Story(1, "Семенов Антон", "Коротышки");
        StoryStarter storyStarter = new StoryStarter();
        StoryEnder storyEnder = new StoryEnder();
        storyStarter.storyStart(story);
        Location location = new Location(("Где - то"), ("Город Коротышек"));
        Money santik = new Money("Сантик", "coin");
        Hero ponchik = new Hero("Пончик", 1);
        ponchik.introduce();
        Location uPochika = new Location(("у пончика"), ("Город Коротышек"));
        ponchik.setLocation(uPochika);
        story.addHeroInStory(ponchik);
        for (int i = 0; i < 20; i++) {
            salt.addThisItem(ponchik);
        }
        ponchik.addFeeling(Feelings.ENVY);
        ponchik.takeItem(salt);
        ponchik.addFeeling(Feelings.GREEDY);
        Hero korotysh = new Hero("Коротышка", 2);
        story.addHeroInStory(korotysh);
        santik.addThisItem(korotysh);
        trade.commitTrade(korotysh, ponchik, santik, salt);
        Hero others = new Hero("Другие посетители", 3);
        story.addHeroInStory(others);
        others.setLocation(location);
        others.changeLocation("У Пончика");
        Hero errybody = new Hero("Каждый", 4);
        santik.addThisItem(errybody);
        trade.commitTrade(errybody, ponchik, santik, salt);
        ponchik.addFeeling(Feelings.PLEASURE);
        Hero luntik = new Hero("Лунатик", 5);
        story.addHeroInStory(luntik);
        salt.addThisItem(luntik);
        salt.taste(luntik);
        Hero another = new Hero("Другой", 6);
        story.addHeroInStory(another);
        for (int i = 0; i < 10; i++) {
            santik.addThisItem(another);
        }
        for (int i = 0; i < 10; i++) {
            trade.commitTrade(another, ponchik, santik, salt);
        }
        Food soup = new Food("Суп");
        soup.addThisItem(another);
        for (int i = 0; i < 10; i++) {
            soup.addSalt(another);
        }
        soup.taste(another);
        errybody.addFeeling(Feelings.CURIOSITY);
        Food lunch = new Food("Обед");
        Hero someones = new Hero("Некоторые", 7);
        story.addHeroInStory(someones);
        lunch.taste(someones);
        salt.addThisItem(someones);
        lunch.addSalt(someones);
        lunch.taste(someones);
        Hero lots = new Hero("Многие", 7);
        story.addHeroInStory(someones);
        Food shi = new Food("Щи");
        Food noodles = new Food("Макароны");
        Food pancakes = new Food("Обладьи");
        Food potato = new Food("Картошка");
        Food borch = new Food("Борщ");
        Food friedZucchini = new Food("Жаренные кабачки");
        shi.setLevelOfSalt(1);
        borch.setLevelOfSalt(1);
        noodles.setLevelOfSalt(1);
        pancakes.setLevelOfSalt(1);
        potato.setLevelOfSalt(1);
        friedZucchini.setLevelOfSalt(1);
        soup.taste(lots);
        borch.taste(lots);
        shi.taste(lots);
        noodles.taste(lots);
        pancakes.taste(lots);
        potato.taste(lots);
        friedZucchini.taste(lots);
        storyEnder.storyEnd(story);
    }
}
