package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Type;

public class FocusBlust extends PhysicalMove {
    public FocusBlust() {
        super(Type.FIGHTING, 170.0D, 70.0D);
    }

    protected void applyOppEffects(Pokemon p) {
        int luck = (int)(Math.random() * 10.0D) + 0;
        if (luck == 1) {
            p.setMod(Stat.SPECIAL_DEFENSE, -1);
        }

    }

    protected void applyOppDamage(Pokemon p, double damage) {
        super.applyOppDamage(p, damage);
    }

    protected String describe() {
        return "кастует Focus Blast";
    }
}
