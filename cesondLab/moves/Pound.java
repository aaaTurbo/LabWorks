package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Pound extends PhysicalMove {
    public Pound() {
        super(Type.NORMAL, 40.0D, 100.0D);
    }

    protected void applyOppDamage(Pokemon p, double damage) {
        super.applyOppDamage(p, damage);
    }

    protected String describe() {
        return "кастует Pound";
    }
}
