package com.example.project3;

public class TaskFirebase {
    private String id;
    private String task;

    // Constructor kosong diperlukan untuk Firebase
    public TaskFirebase() {
    }

    public TaskFirebase(String id, String task) {
        this.id = id;
        this.task = task;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}