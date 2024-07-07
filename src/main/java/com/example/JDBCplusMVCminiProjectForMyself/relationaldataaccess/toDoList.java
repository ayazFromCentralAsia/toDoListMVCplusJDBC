package com.example.JDBCplusMVCminiProjectForMyself.relationaldataaccess;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity(name = "todolist")
public class toDoList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private String title;
    private String description;
    private String date;
    private boolean isDone;
    public toDoList(int ID, String title, String description, String date, boolean isDone) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.date = date;
        this.isDone = isDone;
    }
    public toDoList() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return "toDoList{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", isDone=" + isDone +
                '}';
    }
}
