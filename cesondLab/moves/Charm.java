package moves;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class Charm extends StatusMove {
    public Charm() {
        super(Type.FAIRY, 0.0D, 100.0D);
    }

    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.ATTACK, -2);
    }

    protected String describe() {
        return "кастует Charm";
    }
}
