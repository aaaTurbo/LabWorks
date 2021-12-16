package moves;

import ru.ifmo.se.pokemon.*;

public class Charm extends StatusMove {

    public Charm(){
        super(Type.FAIRY, 0.0, 0.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.ATTACK, -2);
    }

    @Override
    protected String describe(){
        return "кастует Charm";
    }
}
