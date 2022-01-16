public enum Moves {

    GETSANTIK(" получает сантик "),
    GETSALT(" получает щепоть соли "),
    COME(" подходит(ят) к "),
    GIVESANTIK(" отдает сантик "),
    TAKESALT(" достает щепоть соли "),
    SEESANTIKTALK(" смотрит как растет куча сантиков "),
    TRYREALSALT(" пробует соль в чистом виде "),
    ADDSALTTOSOUP(" добавляет всю соль в суп "),
    SPLITOUT(" выплевывает "),
    BUYTENSALT(" покупает 10 щепоть соли за 10 сантиков "),
    HANDTOPOCKET(" запускает руку в карман ");

    private String move;

    private Moves(String move){
        this.move = move;
    }

    @Override
    public String toString(){
        return "Движение" + move;
    }
}
