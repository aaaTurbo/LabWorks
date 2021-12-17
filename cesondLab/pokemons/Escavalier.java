package pokemons;

import moves.DoubleTeam;
import moves.Peck;
import moves.Twineedle;
import moves.XScissor;
import ru.ifmo.se.pokemon.Type;

public class Escavalier extends Karrablast {
    public Escavalier(String name, int level) {
        super(name, level);
        this.setStats(70.0D, 135.0D, 105.0D, 60.0D, 105.0D, 20.0D);
        this.setType(new Type[]{Type.BUG, Type.STEEL});
        this.addMove(new XScissor());
        this.addMove(new DoubleTeam());
        this.addMove(new Peck());
        this.addMove(new Twineedle());
    }
}
