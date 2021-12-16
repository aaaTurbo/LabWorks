import pokemons.*;
import  ru.ifmo.se.pokemon.*;

public class Main {

    static public void main(String[] args){
        Ralts ralts = new Ralts("Pokemon", 10);
        Kirilia kirilia = new Kirilia("Pokemon", 20);
        Gallade gallade = new Gallade("Pokemon", 30);
        Escavalier escavalier = new Escavalier("Pokemon", 30);
        Karrablast karrablast = new Karrablast("Pokemon", 20);
        OricorioPomPom oricorioPomPom = new OricorioPomPom("Pokemon", 20);
        Battle b = new Battle();
        b.addAlly(gallade);
        b.addAlly(karrablast);
        b.addAlly(ralts);
        b.addFoe(escavalier);
        b.addFoe(kirilia);
        b.addFoe(oricorioPomPom);
        b.go();
    }
}
