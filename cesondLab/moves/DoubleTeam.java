package moves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class DoubleTeam extends StatusMove {
    public DoubleTeam() {
        super(Type.NORMAL, 0.0D, 100.0D);
    }

    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.EVASION, 1);
        if ((int)p.getStat(Stat.ACCURACY) > 1) {
            p.setMod(Stat.ACCURACY, -1);
        }

    }

    protected String describe() {
        return "кастует Double Team";
    }
}
}
