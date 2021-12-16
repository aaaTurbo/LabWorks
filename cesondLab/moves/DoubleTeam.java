package moves;

import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {

    public DoubleTeam(){
        super(Type.NORMAL, 0.0, 100.0);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.EVASION, +1);
        if((int) p.getStat(Stat.ACCURACY) > 1) {
            p.setMod(Stat.ACCURACY, -1);
        }
    }

    @Override
    protected String describe(){
        return "кастует Double Team";
    }
}
