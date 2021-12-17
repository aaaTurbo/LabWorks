package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Twineedle extends PhysicalMove {
    public Twineedle() {
        super(Type.BUG, 25.0D, 100.0D);
    }

    protected void applyOppEffects(Pokemon p) {
        int luck = (int)(Math.random() * 10.0D) + 0;
        if ((luck == 1 || luck == 2) && !p.hasType(Type.POISON) && !p.hasType(Type.STEEL)) {
            Effect.poison(p);
        }

    }

    protected void applyOppDamage(Pokemon p, double damage) {
        super.applyOppDamage(p, damage);
        super.applyOppDamage(p, damage);
    }

    protected String describe() {
        return "кастует Twineedle";
    }
}
