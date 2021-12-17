package pokemons;

import moves.DoubleTeam;
import moves.ThunderWave;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Ralts extends Pokemon {
    public Ralts(String name, int level) {
        super(name, level);
        this.setStats(28.0D, 25.0D, 25.0D, 45.0D, 35.0D, 40.0D);
        this.setType(new Type[]{Type.FAIRY, Type.PSYCHIC});
        this.addMove(new DoubleTeam());
        this.addMove(new ThunderWave());
    }
}
