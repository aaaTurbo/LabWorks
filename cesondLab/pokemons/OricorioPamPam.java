package pokemons;

import moves.Pound;
import moves.Rest;
import moves.Swagger;
import moves.ThunderWave;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class OricorioPomPom extends Pokemon {
    public OricorioPomPom(String name, int level) {
        super(name, level);
        this.setStats(75.0D, 70.0D, 70.0D, 98.0D, 70.0D, 93.0D);
        this.setType(new Type[]{Type.ELECTRIC, Type.FLYING});
        this.addMove(new Rest());
        this.addMove(new ThunderWave());
        this.addMove(new Pound());
        this.addMove(new Swagger());
    }
}
