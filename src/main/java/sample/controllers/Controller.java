package sample.controllers;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Date;
import sample.Influence;
import sample.Month;
import sample.Request;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class Controller {
    @FXML
    TextField fieldInitiator, fieldDepartment, fieldAddress;
    @FXML
    ComboBox<Influence> comboBoxInfluence;
    @FXML
    TextArea areaDescription;
    @FXML
    Button buttonCleaner, buttonSave;
    @FXML
    TableView tableView;
    @FXML
    TableColumn columnInitiator, columnDepartment, columnAddress, columnInfluence, columnDateBegin, columnDateDeadLine, columnDateEnd, columnDescription, columnCondition;
    @FXML
    BarChart<String, Number> barChart;
    @FXML
    CategoryAxis xAxis;
    @FXML
    NumberAxis yAxis;
    @FXML
    ComboBox<String> yearComboBox;
    @FXML
    ComboBox<Month> monthComboBox;


    private XYChart.Series<String, Number> inputRequestsChart;
    private XYChart.Series<String, Number> doneRequestsChart;
    private ObservableList<Request> requestsData;
    private  Connection conn;
    private boolean hasDate;

    @FXML
    public void initialize(){
        hasDate = false;
        requestsData = FXCollections.observableArrayList();
        getConnection();
        getData();

        for (int i = 0; i < requestsData.size(); i++) {
            addActionListener(requestsData.get(i));
        }

        comboBoxInfluence.setItems(Influence.getListInfluences());
        comboBoxInfluence.getSelectionModel().select(0);

        columnInitiator.setCellValueFactory(new PropertyValueFactory<Request, String>("initiator"));
        columnDepartment.setCellValueFactory(new PropertyValueFactory<Request, String>("department"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<Request, String>("address"));
        columnInfluence.setCellValueFactory(new PropertyValueFactory<Request, Influence>("influence"));
        columnDateBegin.setCellValueFactory(new PropertyValueFactory<Request, GregorianCalendar>("dateBegin"));
        columnDateDeadLine.setCellValueFactory(new PropertyValueFactory<Request, GregorianCalendar>("dateDeadLine"));
        columnDateEnd.setCellValueFactory(new PropertyValueFactory<Request, GregorianCalendar>("dateEnd"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<Request, String>("description"));
        columnCondition.setCellValueFactory(new PropertyValueFactory<Request, CheckBox>("condition"));

        tableView.setItems(requestsData);
        ObservableList<String> years = FXCollections.observableArrayList();
        int startYear = 2018;
        for (int i = 0; i < 15; i++) {
            years.add(String.valueOf(startYear + i));
        }

        yearComboBox.setItems(years);
        monthComboBox.setItems(Month.getListMonths());

        inputRequestsChart = new XYChart.Series<String, Number>();
        inputRequestsChart.setName("Поступившие заявки");
        doneRequestsChart = new XYChart.Series<String, Number>();
        doneRequestsChart.setName("Выполненные заявки");

        xAxis.setLabel("День");
        yAxis.setLabel("Количество заявок");
        yAxis.setTickUnit(1);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(25);
        yAxis.setAutoRanging(false);

        barChart.getData().add(inputRequestsChart);
        barChart.getData().add(doneRequestsChart);
        barChart.setBarGap(1);
        barChart.setCategoryGap(5);

    }

    public void clean(){
        fieldInitiator.setText("");
        fieldDepartment.setText("");
        fieldAddress.setText("");
        areaDescription.setText("");
    }

    private void getConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:techSupport.db";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getData(){
        if(!hasDate) {
            hasDate = true;
            try {
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM requests;");
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    requestsData.add(new Request(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4),
                            resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8),
                            resultSet.getString(9), resultSet.getInt(10)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertData(){
        if(fieldInitiator.getText().equals("") || fieldDepartment.getText().equals("")
        || fieldAddress.getText().equals("") || areaDescription.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Произошла ошибка");
            alert.setContentText("Вы не все ввели");
            alert.show();
        } else {
            String currentDate  = Date.getCurrentDate();
            Request request = new Request(fieldInitiator.getText(), fieldDepartment.getText(), fieldAddress.getText(),
                    comboBoxInfluence.getValue().name(), currentDate, setDeadLine(currentDate, comboBoxInfluence.getValue().name()),
                    null, areaDescription.getText(), 0);
            System.out.println(request.getDateBegin());
            System.out.println(request.getDateDeadLine());
            try {
                PreparedStatement statement = conn.prepareStatement("INSERT INTO requests(initiator, department, address, influence, dateBegin, dateDeadLine, dateEnd, description, condition) VALUES(?,?,?,?,?,?,?,?,?);");
                statement.setString(1, request.getInitiator());
                statement.setString(2, request.getDepartment());
                statement.setString(3, request.getAddress());
                statement.setString(4, request.getInfluence());
                statement.setString(5, request.getDateBegin());
                statement.setString(6, request.getDateDeadLine());
                statement.setString(7, request.getDateEnd());
                statement.setString(8, request.getDescription());
                statement.setInt(9, request.getValueCondition());
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            addActionListener(request);
            requestsData.add(request);
            clean();
        }
    }

    private void updateData(Request request){
        PreparedStatement statement = null;
        request.setDateEnd(Date.getCurrentDate());
        try {
            statement = conn.prepareStatement("UPDATE requests SET dateEnd = ?, condition = ? WHERE dateBegin = ?;");
            statement.setString(1, request.getDateEnd());
            statement.setInt(2, 1);
            statement.setString(3, request.getDateBegin());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < requestsData.size(); i++) {
            Request r = requestsData.get(i);
            if(r.getDateBegin().equals(request.getDateBegin())) {
                r.setDateEnd(request.getDateEnd());
                requestsData.set(i, r);
                break;
            }
        }

        for (int i = 0; i < requestsData.size(); i++) {
            System.out.println(requestsData.get(i).getDepartment());
        }
    }



    public String setDeadLine(String date, String influenceS){
        Influence influence = Influence.defineInfluence(influenceS);
        GregorianCalendar calendar = Date.getGregorianCalendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + influence.getHours());
        return Date.format(calendar);
    }

    public void addActionListener(Request request){
            CheckBox checkBox = request.getCondition();
            checkBox.selectedProperty().addListener( (observable, oldValue, newValue) -> {
               if(request.getDateEnd() == null){
                   updateData(request);
               }
               checkBox.setSelected(true);
            });
    }

    public void buildChart(){
        System.out.println("asdasd");
        HashMap<String, Integer> inputRequests = new HashMap<>();
        HashMap<String, Integer> doneRequests = new HashMap<>();

        for (int i = 1; i <= 31; i++) {
            String d = String.format("%02d", i);
            System.out.println("d " + d);
            inputRequests.put(d, 0);
            doneRequests.put(d, 0);
        }

        String dateBegin;
        String year;
        String month;
        String day;
        for (Request r:
             requestsData) {
            dateBegin = r.getDateBegin();
            year = Date.getYear(dateBegin);
            month = Date.getMonth(dateBegin);
            System.out.println("month c " + monthComboBox.getValue().getNumber());
            if(year.equals(yearComboBox.getValue()) &&
                    month.equals(monthComboBox.getValue().getNumber())){
                day = Date.getDay(dateBegin);
                inputRequests.put(day, inputRequests.get(day) + 1);
                if(r.getCondition().isSelected()){
                    doneRequests.put(day, doneRequests.get(day) + 1);
                }
            }
        }

        for (String d: // день
             inputRequests.keySet()) {
            inputRequestsChart.getData().add(new XYChart.Data<>(d, inputRequests.get(d)));
            doneRequestsChart.getData().add(new XYChart.Data<>(d, doneRequests.get(d)));
        }


        Collections.sort(inputRequestsChart.getData(), (Comparator<XYChart.Data>) (o1, o2) -> {
            String xValue1 = (String) o1.getXValue();
            String xValue2 = (String) o2.getXValue();
            return xValue1.compareTo(xValue2);
        });
    }

}
