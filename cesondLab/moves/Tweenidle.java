package moves;

import ru.ifmo.se.pokemon.*;

public class Twineedle extends PhysicalMove {
    public Twineedle() {
        super(Type.BUG, 25.0, 100.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        int luck = ((int) (Math.random() * 10) + 0);
        if ((luck == 1 || luck == 2) && (!p.hasType(Type.POISON) && (!p.hasType(Type.STEEL)))) {
            Effect.poison(p);
        }
    }

    @Override
    protected void applyOppDamage(Pokemon p, double damage) {
        super.applyOppDamage(p, damage);
        super.applyOppDamage(p, damage);
    }

    @Override
    protected String describe() {
        return "кастует Twineedle";
    }
}
