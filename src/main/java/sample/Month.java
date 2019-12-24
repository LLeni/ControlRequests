package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum  Month {
    Январь("01"), Февраль("02"), Март("03"), Апрель("04"), Май("05"),
    Июнь("06"), Июль("07"), Август("08"), Сентябрь("09"),
    Октябрь("10"), Ноябрь("11"), Декабрь("12");

    private String number;
    Month(String number){
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public static ObservableList<Month> getListMonths(){
        ObservableList<Month> list = FXCollections.observableArrayList();
        list.add(Month.Январь);
        list.add(Month.Февраль);
        list.add(Month.Март);
        list.add(Month.Апрель);
        list.add(Month.Май);
        list.add(Month.Июнь);
        list.add(Month.Июль);
        list.add(Month.Август);
        list.add(Month.Сентябрь);
        list.add(Month.Октябрь);
        list.add(Month.Ноябрь);
        list.add(Month.Декабрь);
        return list;
    }
}
