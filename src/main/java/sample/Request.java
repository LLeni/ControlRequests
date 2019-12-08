package sample;

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

    public int getCondition() {
        if(condition.isSelected()){
            return 1;
        } else {
            return 0;
        }
    }

    public void setCondition(int condition) {
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

    public Request(String initiator, String department, String address, int influence, String dateBegin, String dateDeadLine, String dateEnd, String description, int condition) {
        this.initiator = initiator;
        this.department = department;
        this.address = address;
        setInfluence(influence);
        this.dateBegin = getGregorianCalendar(dateBegin);
        this.dateDeadLine = getGregorianCalendar(dateDeadLine);
        this.dateEnd = getGregorianCalendar(dateEnd);
        this.description = description;
        setCondition(condition);
    }

    private GregorianCalendar getGregorianCalendar(String date){
        if(date.equals("")){
            return null;
        } else {
            StringTokenizer tokenizer = new StringTokenizer(date, " ");
            GregorianCalendar calendar = new GregorianCalendar(Integer.valueOf(tokenizer.nextToken()), Integer.valueOf(tokenizer.nextToken()),
                    Integer.valueOf(tokenizer.nextToken()));
            calendar.set(Calendar.HOUR, Integer.valueOf(tokenizer.nextToken()));
            calendar.set(Calendar.MINUTE, Integer.valueOf(tokenizer.nextToken()));
            return calendar;
        }
    }
    
    private String getString(GregorianCalendar calendar){
        return calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." +
                calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR) + ":" +
                calendar.get(Calendar.MINUTE);
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
        return getString(dateBegin);
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = getGregorianCalendar(dateBegin);
    }

    public String getDateDeadLine() {
        return getString(dateDeadLine);
    }

    public void setDateDeadLine(String dateDeadLine) {
        this.dateDeadLine = getGregorianCalendar(dateDeadLine);
    }

    public String getDateEnd() {
        return getString(dateEnd);
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = getGregorianCalendar(dateEnd);
    }



    public int getInfluence() {
        return influence.getHours();
    }

    public void setInfluence(int influence) {
        switch (influence){
            case 1:
                this.influence = Influence.ONE;
                break;
            case 2:
                this.influence = Influence.TWO;
                break;
            case 3:
                this.influence = Influence.THREE;
                break;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
