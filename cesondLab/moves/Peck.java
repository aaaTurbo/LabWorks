package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Peck extends PhysicalMove {
    public Peck(){super(Type.FIGHTING, 35.0, 100.0);}

    @Override
    protected void applyOppDamage(Pokemon p, double damage) {
        super.applyOppDamage(p, damage);
    }

    @Override
    protected String describe(){
        return "кастует Peck";
    }
}
