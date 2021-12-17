package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class TeeterDance extends PhysicalMove {
    public TeeterDance() {
        super(Type.NORMAL, 0.0D, 100.0D);
    }

    protected void applyOppEffects(Pokemon p) {
        Effect.confuse(p);
    }

    protected void applyOppDamage(Pokemon p, double damage) {
        int luck = (int)(Math.random() * 10.0D) + 1;
        int turn = (int)(Math.random() * 4.0D) + 1;
        int attack = (int)p.getStat(Stat.ATTACK);
        if (luck == 1 || luck == 2 || luck == 3) {
            for(int i = 0; i < turn; ++i) {
                super.applyOppDamage(p, (double)(attack / turn));
            }
        }

    }

    protected String describe() {
        return "кастует Teeter Dance";
    }
}
