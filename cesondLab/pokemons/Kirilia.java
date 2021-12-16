package pokemons;

import moves.Charm;
import moves.DoubleTeam;
import moves.ThunderWave;
import ru.ifmo.se.pokemon.*;

public class Kirilia extends Ralts{

    public Kirilia(String name, int level){
        super(name, level);
        setStats(38.0, 35.0, 35.0, 65.0, 55.0, 50.0);
        setType(Type.FAIRY, Type.PSYCHIC);
        addMove(new DoubleTeam());
        addMove(new ThunderWave());
        addMove(new Charm());
    }

}
