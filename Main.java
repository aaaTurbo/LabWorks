import moves.DoubleTeam;
import moves.ThunderWave;
import pokemons.Kirilia;
import pokemons.Ralts;
import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Main {

    static public void main(String[] args){
        Ralts ralts = new Ralts("Pokemon", 10);
        Kirilia kirilia = new Kirilia("Pokemon", 10);
        Battle b = new Battle();
        b.addAlly(ralts);
        b.addFoe(kirilia);
        b.go();
    }
}
