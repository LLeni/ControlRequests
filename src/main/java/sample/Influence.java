package sample;

public enum Influence {
    ONE(2, "Критическое"), TWO(5, "Серьезное"), THREE(10, "Обычное");

    private int hours;
    private String description;

    Influence(int hours, String description){
        this.hours = hours;
        this.description = description;
    }

    public int getHours(){
        return  hours;
    }

    public  String getDescription(){
        return  description;
    }
}
