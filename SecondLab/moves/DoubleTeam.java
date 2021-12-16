package moves;

import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {

    public DoubleTeam(){
        super(Type.NORMAL, 0.0, 0.0);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.EVASION, +2);
    }

    @Override
    protected String describe(){
        return "кастует Double Team";
    }
}
