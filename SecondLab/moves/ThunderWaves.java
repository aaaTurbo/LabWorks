package moves;

import ru.ifmo.se.pokemon.*;

public class ThunderWave extends StatusMove {

    public ThunderWave(){
        super(Type.ELECTRIC, 0.0, 90.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect.paralyze(p);
    }

    @Override
    protected String describe(){
        return "кастует Thunder Wave";
    }
}
