package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class Swagger extends PhysicalMove {
    public Swagger() {
        super(Type.NORMAL, 0.0D, 85.0D);
    }

    protected void applyOppEffects(Pokemon p) {
        Effect.confuse(p);
    }

    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.ATTACK, 2);
    }

    protected void applyOppDamage(Pokemon p, double damage) {
        int turn = (int)(Math.random() * 4.0D) + 1;
        int attack = (int)p.getStat(Stat.ATTACK);

        for(int i = 0; i < turn; ++i) {
            super.applyOppDamage(p, (double)(attack / turn));
        }

    }

    protected String describe() {
        return "кастует Swagger";
    }
}
