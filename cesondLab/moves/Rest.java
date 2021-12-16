package moves;

import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {
    public Rest(){super(Type.PSYCHIC, 0.0, 100.00);}

    @Override
    protected void applySelfEffects(Pokemon p) {
        Effect.sleep(p);
        p.setMod(Stat.HP, (int) (75-p.getHP()));
    }
}
