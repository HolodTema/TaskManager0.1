package com.terabyte.taskmanager01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    private LayoutInflater inflater;
    private int layout;
    private List<Task> tasks;

    public TaskAdapter(Context context, int layout, List<Task> tasks) {
        super(context, layout, tasks);
        this.tasks = tasks;
        this.layout = layout;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=inflater.inflate(this.layout, parent, false);

        Task task = tasks.get(position);

        TextView textTaskName = view.findViewById(R.id.textTaskName);
        TextView textTaskDescription = view.findViewById(R.id.textTaskDescription);

        textTaskName.setText(task.name);
        if(task.description.length()>Constant.TASK_DESCRIPTION_LENGTH_LIMIT) {
            textTaskDescription.setText(task.description.substring(0, Constant.TASK_DESCRIPTION_LENGTH_LIMIT)+"...");
        }
        else {
            textTaskDescription.setText(task.description);
        }

        return view;
    }
}
