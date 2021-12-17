package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class ThunderWave extends StatusMove {
    public ThunderWave() {
        super(Type.ELECTRIC, 0.0D, 90.0D);
    }

    protected void applyOppEffects(Pokemon p) {
        Effect.paralyze(p);
    }

    protected String describe() {
        return "кастует Thunder Wave";
    }
}
