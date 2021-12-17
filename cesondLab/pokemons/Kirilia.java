package pokemons;

import moves.Charm;
import moves.DoubleTeam;
import moves.ThunderWave;
import ru.ifmo.se.pokemon.Type;

public class Kirilia extends Ralts {
    public Kirilia(String name, int level) {
        super(name, level);
        this.setStats(38.0D, 35.0D, 35.0D, 65.0D, 55.0D, 50.0D);
        this.setType(new Type[]{Type.FAIRY, Type.PSYCHIC});
        this.addMove(new DoubleTeam());
        this.addMove(new ThunderWave());
        this.addMove(new Charm());
    }
}
