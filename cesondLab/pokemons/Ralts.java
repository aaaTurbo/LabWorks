package pokemons;

import moves.DoubleTeam;
import moves.ThunderWave;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class Ralts extends Pokemon {

    public Ralts(String name, int level){
        super(name, level);
        setStats(28.0, 25.0, 25.0, 45.0, 35.0, 40.0);
        setType(Type.FAIRY, Type.PSYCHIC);
        addMove(new DoubleTeam());
        addMove(new ThunderWave());
    }
}
