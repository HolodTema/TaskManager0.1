package com.terabyte.taskmanager01;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE id = :id")
    Task getById(long id);

    @Query("SELECT * FROM task WHERE isFinished = :isFinished")
    List<Task> getByIsFinished(boolean isFinished);

}
