package moves;

import ru.ifmo.se.pokemon.*;

public class Swagger extends PhysicalMove {
    public Swagger(){super(Type.NORMAL, 0.0, 85.00);}

    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect.confuse(p);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.ATTACK, 2);
    }

    @Override
    protected void applyOppDamage(Pokemon p, double damage) {
        int turn = ((int) (Math.random() * 4) + 1);
        int attack = (int) p.getStat(Stat.ATTACK);
        for (int i = 0; i < turn; i++) {
            super.applyOppDamage(p, attack / turn);
        }
    }

    @Override
    protected String describe() {
        return "кастует Swagger";
    }
}
