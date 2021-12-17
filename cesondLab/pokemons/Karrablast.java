package pokemons;

import moves.DoubleTeam;
import moves.Peck;
import moves.XScissor;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Karrablast extends Pokemon {
    public Karrablast(String name, int level) {
        super(name, level);
        this.setStats(50.0D, 75.0D, 45.0D, 40.0D, 45.0D, 60.0D);
        this.setType(new Type[]{Type.BUG});
        this.addMove(new XScissor());
        this.addMove(new DoubleTeam());
        this.addMove(new Peck());
    }
}
