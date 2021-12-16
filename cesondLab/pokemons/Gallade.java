package pokemons;

import moves.Charm;
import moves.DoubleTeam;
import moves.FocusBlust;
import moves.ThunderWave;
import ru.ifmo.se.pokemon.Type;

public class Gallade extends Kirilia{
    public Gallade(String name, int level){
        super(name, level);
        setStats(68.0, 125.0, 65.0, 65.0, 115.0, 80.0);
        setType(Type.FIGHTING, Type.PSYCHIC);
        addMove(new DoubleTeam());
        addMove(new ThunderWave());
        addMove(new Charm());
        addMove(new FocusBlust());
    }
}
