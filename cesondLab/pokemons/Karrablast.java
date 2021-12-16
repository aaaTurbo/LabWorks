package pokemons;

import moves.DoubleTeam;
import moves.Peck;
import moves.XScissor;
import ru.ifmo.se.pokemon.*;

public class Karrablast extends Pokemon {
    public Karrablast(String name, int level){
        super(name, level);
        setStats(50.0, 75.0, 45.0, 40.0, 45.0, 60.0);
        setType(Type.BUG);
        addMove(new XScissor());
        addMove(new DoubleTeam());
        addMove(new Peck());
    }
}
