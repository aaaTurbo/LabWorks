package moves;

import ru.ifmo.se.pokemon.*;

public class Pound extends PhysicalMove {
    public Pound(){
        super(Type.NORMAL, 40.0, 100.0);
    }

    @Override
    protected void applyOppDamage(Pokemon p, double damage) {
        super.applyOppDamage(p, damage);
    }

    @Override
    protected String describe(){
        return "кастует Pound";
    }
}
