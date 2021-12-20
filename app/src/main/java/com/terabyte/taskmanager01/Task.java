package com.terabyte.taskmanager01;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    public String name, description;
    public boolean isFinished;
    @PrimaryKey(autoGenerate = true)
    public long id;

    public Task(String name, String description, boolean isFinished) {
        this.name = name;
        this.description = description;
        this.isFinished = isFinished;

    }
}
