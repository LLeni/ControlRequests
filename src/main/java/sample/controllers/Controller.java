package sample.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Request;

import java.sql.*;


public class Controller {
    @FXML
    TextField fieldInitiator, fieldDepartment, fieldAddress, fieldInfluence;
    @FXML
    TextArea areaDescription;
    @FXML
    Button buttonCleaner, buttonSave;
    @FXML
    TableView tableView;
    @FXML
    TableColumn columnInitiator, columnDepartment, columnAddress, columnInfluence, columnDateBegin, columnDateDeadLine, columnDateEnd, columnDescription, columnCondition;

    private ObservableList<Request> requestsData;
    private  Connection conn;
    private boolean hasDate;

    public void clean(){
        fieldInitiator.setText("");
        fieldDepartment.setText("");
        fieldAddress.setText("");
        fieldInfluence.setText("");
        areaDescription.setText("");
    }
    public void save(){

    }

    private void getConnection(){
        String url = "jdbc:sqlite:/techSupport.db";
        // create a connection to the database
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getData(){
        if(!hasDate) {
            hasDate = true;
            try {
                Statement statement = conn.createStatement();
                statement.execute("SELECT * FROM requests;");
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    requestsData.add(new Request(resultSet.getString(0), resultSet.getString(1), resultSet.getString(2),
                            resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                            resultSet.getString(7), resultSet.getInt(8)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertData(Request request){
        try {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO requests VALUES(?,?,?,?,?,?,?,?,?)");
            statement.setString(1, request.getInitiator());
            statement.setString(2, request.getDepartment());
            statement.setString(3, request.getAddress());
            statement.setInt(4, request.getInfluence());
            statement.setString(5, request.getDateBegin());
            statement.setString(6, request.getDateDeadLine());
            statement.setString(7, request.getDateEnd());
            statement.setString(8, request.getDescription());
            statement.setInt(9, request.getCondition());


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateData(Request request){
        PreparedStatement statement = null;
        try {
            statement = conn.prepareStatement("UPDATE requests SET dateEnd = ?, condition = ?");
            statement.setString(1, request.getDateEnd());
            statement.setInt(2,request.getCondition());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void initialize(){
        hasDate = false;
        requestsData = FXCollections.observableArrayList();
        getConnection();
        getData();
    }
}
