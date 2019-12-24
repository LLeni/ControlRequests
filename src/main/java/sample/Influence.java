package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum Influence {
    Критическое(2), Серьезное(5), Обычное(10);

    private int hours;

    Influence(int hours){
        this.hours = hours;
    }

    public int getHours(){
        return  hours;
    }

    public static Influence defineInfluence(String influenceS){
        Influence influence = null;
        switch (influenceS){
            case "Критическое":
                influence = Influence.Критическое;
                break;
            case "Серьезное":
                influence = Influence.Серьезное;
                break;
            case "Обычное":
                influence = Influence.Обычное;
                break;
        }
        return  influence;
    }

    public static ObservableList<Influence> getListInfluences(){
        ObservableList<Influence> list = FXCollections.observableArrayList();
        list.add(Influence.Критическое);
        list.add(Influence.Серьезное);
        list.add(Influence.Обычное);
        return list;
    }
}
