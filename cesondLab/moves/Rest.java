package moves;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class Rest extends StatusMove {
    public Rest() {
        super(Type.PSYCHIC, 0.0D, 100.0D);
    }

    protected void applySelfEffects(Pokemon p) {
        Effect.sleep(p);
        p.setMod(Stat.HP, (int)(75.0D - p.getHP()));
    }
}
