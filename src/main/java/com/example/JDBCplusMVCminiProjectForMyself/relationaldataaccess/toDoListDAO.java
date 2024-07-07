package com.example.JDBCplusMVCminiProjectForMyself.relationaldataaccess;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class toDoListDAO {
    private static final String url = "jdbc:mysql://localhost:3306/lesson";
    private static final String user = "root";
    private static final String password = "1234";

    private static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTask(toDoList toDoList){
        try {
            String date = "INSERT INTO todolist(id,title,description,date,isDone) values (?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(date);
            statement.setString(1, String.valueOf(toDoList.getID()));
            statement.setString(2, toDoList.getTitle());
            statement.setString(3, toDoList.getDescription());
            statement.setString(4, toDoList.getDate());
            statement.setBoolean(5, toDoList.isDone());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<toDoList> getAllToDoList(){
        List<toDoList> list = new ArrayList<toDoList>();
        try {
            Statement stmt = conn.createStatement();
            String SQL = "select * from todolist";
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                toDoList obj = new toDoList();
                obj.setID(rs.getInt("id"));
                obj.setTitle(rs.getString("title"));
                obj.setDescription(rs.getString("description"));
                obj.setDate(rs.getString("date"));
                obj.setDone(rs.getBoolean("isDone"));
                list.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public toDoList getToDoList(int id){
        toDoList obj = new toDoList();
        try {
            Statement statement = conn.createStatement();
            String SQL = "select * from todolist where id = "+ id;
            ResultSet rs = statement.executeQuery(SQL);
            while (rs.next()) {
                obj.setID(rs.getInt("id"));
                obj.setTitle(rs.getString("title"));
                obj.setDescription(rs.getString("description"));
                obj.setDate(rs.getString("date"));
                obj.setDone(rs.getBoolean("isDone"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

    public void deletTaskByID(int id) {
        String SQL = "delete from todolist where id = " + id;
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateByID(int id, String title, String description, LocalDate date, Boolean isDone) {
        String SQL = "update todolist set title = ?, description = ?, date = ? , isDone = ? where id = " + id;
        try {
            Statement statement = conn.createStatement();
            if (title.isEmpty()){
                String lastTitle = "";
                try {
                    ResultSet rs = statement.executeQuery("select title from todolist where id = "+id);
                    if (rs.next()){
                        lastTitle = rs.getString("title");
                    }
                    title = lastTitle;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (description.isEmpty()){
                String lastDescription = "";
                try {
                    ResultSet rs = statement.executeQuery("select description from todolist where id = "+id);
                    if (rs.next()){
                        lastDescription = rs.getString("description");
                    }
                    description = lastDescription;
                }catch (SQLException e){
                    throw  new RuntimeException(e);
                }
            }
            System.out.println("SQL: \n\n\n\n\n");
            boolean isNull =false;
            if (date !=null){
                isNull = true;
            }
            try {
                ResultSet rs = statement.executeQuery("select isDone from todolist where id = "+id);
                Boolean lastisDone = false;
                if (rs.next()){
                    lastisDone = rs.getBoolean("isDone");
                }
                if (isDone != lastisDone){
                    isDone = lastisDone;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                if (isNull == false){
                    LocalDate lastDate = null;
                    try {
                        ResultSet rs = statement.executeQuery("select date from todolist where id = "+id);
                        if (rs.next()){
                            lastDate = LocalDate.parse(rs.getString("date"));
                        }
                        date = lastDate;
                    }catch (SQLException e){
                        throw  new RuntimeException(e);
                    }
                }
                PreparedStatement statement2 = conn.prepareStatement(SQL);
                statement2.setString(1, title);
                statement2.setString(2, description);
                statement2.setString(3, String.valueOf(date));
                statement2.setBoolean(4, isDone);
                statement2.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTask(toDoList task) {
        try {
            Statement statement = conn.createStatement();
            String execut = "UPDATE todolist set isDone = "+ task.isDone() +" where id = " + task.getID();
            statement.executeUpdate(execut);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
