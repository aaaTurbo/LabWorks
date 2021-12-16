package moves;

import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class XScissor extends PhysicalMove {
    public XScissor(){super(Type.FIGHTING, 80.0, 100.0);}

    @Override
    protected void applyOppDamage(Pokemon p, double damage) {
        super.applyOppDamage(p, damage);
    }

    @Override
    protected String describe(){
        return "кастует X-Scissor";
    }
}
