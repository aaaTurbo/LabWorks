public class Story {
    private String story = "Story Begins...\n\n";

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

    public void addSimpleMoveInStory(Hero hero, Moves move) {
        story += hero.doSimpleAction(move);
        story += "\n";
    }

    public void addMoveWithSmbInStory(Hero hero, Hero smb, Moves move) {
        story += hero.doActionWithSmb(move, smb);
        story += "\n";
    }

    @Override
    public String toString() {
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
        Story story = new Story();
        Hero ponchik = new Hero("Пончик", 1);
        story.addHeroInStory(ponchik);
        story.addFeelingInStory(ponchik, Feelings.ENVY);
        story.addSimpleMoveInStory(ponchik, Moves.HANDTOPOCKET);
        story.addSimpleMoveInStory(ponchik, Moves.TAKESALT);
        story.addFeelingInStory(ponchik, Feelings.GREEDY);
        Hero korotysh = new Hero("Коротышка", 2);
        story.addHeroInStory(korotysh);
        story.addSimpleMoveInStory(ponchik, Moves.GETSANTIK);
        story.addSimpleMoveInStory(korotysh, Moves.GETSALT);
        Hero others = new Hero("Другие посетители", 3);
        story.addHeroInStory(others);
        story.addMoveWithSmbInStory(others, ponchik, Moves.COME);
        Hero errybody = new Hero("Каждый", 4);
        story.addMoveWithSmbInStory(errybody, ponchik, Moves.GIVESANTIK);
        story.addSimpleMoveInStory(errybody, Moves.GETSALT);
        story.addFeelingInStory(ponchik, Feelings.PLEASURE);
        story.addSimpleMoveInStory(ponchik, Moves.SEESANTIKTALK);
        Hero luntik = new Hero("Лунатик", 5);
        story.addHeroInStory(luntik);
        story.addSimpleMoveInStory(luntik, Moves.TRYREALSALT);
        story.addFeelingInStory(luntik, Feelings.DISGUST);
        story.addSimpleMoveInStory(luntik, Moves.SPLITOUT);
        Hero another = new Hero("Другой", 6);
        story.addHeroInStory(another);
        story.addSimpleMoveInStory(another, Moves.BUYTENSALT);
        story.addSimpleMoveInStory(another, Moves.ADDSALTTOSOUP);
        story.startStory();
    }
}
