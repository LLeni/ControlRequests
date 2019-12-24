package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class Request {
    private String initiator;
    private String department;
    private String address;
    private Influence influence;
    private GregorianCalendar dateBegin;
    private GregorianCalendar dateDeadLine;
    private GregorianCalendar dateEnd;
    private String description;
    private CheckBox condition;

    public Request(String initiator, String department, String address, String influence, String dateBegin, String dateDeadLine, String dateEnd, String description, int condition) {
        this.initiator = initiator;
        this.department = department;
        this.address = address;
        this.influence = Influence.defineInfluence(influence);
        this.dateBegin = Date.getGregorianCalendar(dateBegin);
        this.dateDeadLine = Date.getGregorianCalendar(dateDeadLine);
        this.dateEnd = Date.getGregorianCalendar(dateEnd);
        this.description = description;
        setCondition(condition);
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateBegin() {
        return Date.format(dateBegin);
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = Date.getGregorianCalendar(dateBegin);
    }

    public String getDateDeadLine() {
        return Date.format(dateDeadLine);
    }

    public void setDateDeadLine(String dateDeadLine) {
        this.dateDeadLine = Date.getGregorianCalendar(dateDeadLine);
    }

    public String getDateEnd() {
        if(dateEnd == null){
            return null;
        } else {
            return Date.format(dateEnd);
        }
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = Date.getGregorianCalendar(dateEnd);
    }

    public String getInfluence() {
        return influence.name();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CheckBox getCondition() {
        return condition;
    }

    public int getValueCondition(){
        if(condition.isSelected()){
            return 1;
        } else {
            return 0;
        }
    }

    private void setCondition(int condition) {
        CheckBox checkBox = new CheckBox();
        switch(condition){
            case 0:
                checkBox.setSelected(false);
                break;
            case 1:
                checkBox.setSelected(true);
                break;
        }
        this.condition = checkBox;
    }


}
