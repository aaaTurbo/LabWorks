package moves;

import ru.ifmo.se.pokemon.*;

public class TeeterDance extends PhysicalMove{
    public TeeterDance(){super(Type.NORMAL, 0.0, 100.00);}

    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect.confuse(p);
    }

    @Override
    protected void applyOppDamage(Pokemon p, double damage){
        int luck = ((int) (Math.random() * 10) + 1);
        int turn = ((int) (Math.random() * 4) + 1);
        int attack = (int) p.getStat(Stat.ATTACK);
        if ((luck == 1) || (luck == 2) || (luck == 3)) {
            for (int i=0; i < turn; i++) {
                super.applyOppDamage(p, attack/turn);
            }
        }
    }

    @Override
    protected String describe() {
        return "кастует Teeter Dance";
    }
}
