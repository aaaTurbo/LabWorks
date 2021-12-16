package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Escavalier extends Karrablast{
    public Escavalier(String name, int level){
        super(name, level);
        setStats(70.0, 135.0, 105.0, 60.0, 105.0, 20.0);
        setType(Type.BUG, Type.STEEL);
        addMove(new XScissor());
        addMove(new DoubleTeam());
        addMove(new Peck());
        addMove(new Twineedle());
    }
}
