import classes.*;


public class Story {
    private int storyCode;
    private String storyTeller;
    private static Salt salt = new Salt();
    private static Trade trade = new Trade();
    private Hero[] heroes = new Hero[100];
    private String storyName;

    public Story(int storyCode, String storyTeller, String storyName){
        this.storyCode = storyCode;
        this.storyTeller = storyTeller;
        this.storyName = storyName;
    }

    public void addHeroInStory(Hero hero) {
        for (int i=0; i < heroes.length; i++){
            if (heroes[i] == null){
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

    static public void main(String[] args) {
        Story story = new Story(1, "Семенов Антон", "Коротышки");
        Location location = new Location("Где - то", "Город Коротышек");
        Money santik = new Money("Сантик", "coin");
        Hero ponchik = new Hero("Пончик", 1);
        story.addHeroInStory(ponchik);
        for (int i = 0; i < 20; i++){
            salt.addThisItem(ponchik);
        }
        ponchik.addFeeling(Feelings.ENVY);;
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
        salt.taste(luntik);
        Hero another = new Hero("Другой", 6);
        story.addHeroInStory(another);
        for (int i = 0; i < 10; i++){
            santik.addThisItem(another);
        }
        for (int i = 0; i < 10; i++){
            trade.commitTrade(another, ponchik, santik, salt);
        }
        Food soup = new Food("Суп");
        soup.addThisItem(another);
        for (int i = 0; i < 10; i++){
            soup.addSalt(another);
        }
        soup.taste(another);
    }
}
