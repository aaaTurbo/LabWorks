package pokemons;

import moves.Charm;
import moves.DoubleTeam;
import moves.FocusBlust;
import moves.ThunderWave;
import ru.ifmo.se.pokemon.Type;

public class Gallade extends Kirilia {
    public Gallade(String name, int level) {
        super(name, level);
        this.setStats(68.0D, 125.0D, 65.0D, 65.0D, 115.0D, 80.0D);
        this.setType(new Type[]{Type.FIGHTING, Type.PSYCHIC});
        this.addMove(new DoubleTeam());
        this.addMove(new ThunderWave());
        this.addMove(new Charm());
        this.addMove(new FocusBlust());
    }
}
