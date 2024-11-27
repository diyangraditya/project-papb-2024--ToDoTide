package com.example.project3;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(TaskEntity task);

    @Query("SELECT * FROM tasks")
    List<TaskEntity> getAllTasks();

}
