package interfaces;

import classes.Hero;
import myExeptions.NoItemExeption;

public interface Tasteable {
    void taste(Hero hero) throws NoItemExeption;
}
