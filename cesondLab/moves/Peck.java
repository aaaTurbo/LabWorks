package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Peck extends PhysicalMove {
    public Peck() {
        super(Type.FIGHTING, 35.0D, 100.0D);
    }

    protected void applyOppDamage(Pokemon p, double damage) {
        super.applyOppDamage(p, damage);
    }

    protected String describe() {
        return "кастует Peck";
    }
}
