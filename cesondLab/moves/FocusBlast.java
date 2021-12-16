package moves;

import ru.ifmo.se.pokemon.*;

public class FocusBlust extends PhysicalMove {
    public FocusBlust(){
        super(Type.FIGHTING, 170.0, 70.0);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        int luck = ((int)(Math.random() *10) +0);
        if (luck == 1){
            p.setMod(Stat.SPECIAL_DEFENSE, -1);
        }
    }

    @Override
    protected void applyOppDamage(Pokemon p, double damage) {
        super.applyOppDamage(p, damage);
    }

    @Override
    protected String describe(){
        return "кастует Focus Blast";
    }

}
