package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class OricorioPomPom extends Pokemon {
    public OricorioPomPom(String name, int level){
        super(name, level);
        setStats(75.0, 70.0, 70.0, 98.0, 70.0, 93.0);
        setType(Type.ELECTRIC, Type.FLYING);
        addMove(new Rest());
        addMove(new ThunderWave());
        addMove(new Pound());
        addMove(new Swagger());
    }
}
