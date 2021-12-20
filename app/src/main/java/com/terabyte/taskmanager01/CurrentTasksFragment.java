package com.terabyte.taskmanager01;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class CurrentTasksFragment extends Fragment {
    private TextView textCurrentTasks;
    private ListView listCurrentTasks;
    private Context context;

    public CurrentTasksFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        class DatabaseAsyncTask extends AsyncTask<AppDatabase, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(AppDatabase ...db) {
                return db[0].taskDao().getByIsFinished(false);
            }
            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                if(tasks.size()>0) {
                    textCurrentTasks.setText(getResources().getString(R.string.current_tasks));
                    listCurrentTasks.setAdapter(new TaskAdapter(context, R.layout.list_tasks, tasks));
                    listCurrentTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(context, NewTaskActivity.class);
                            intent.putExtra(Constant.INTENT_KEY_NEW_TASK_MODE, Constant.MODE_MODIFICATION);
                            intent.putExtra(Constant.INTENT_KEY_TASK_ID, tasks.get(i).id); // TODO: 18.12.2021
                            startActivity(intent);
                        }
                    });
                }
                else {
                    textCurrentTasks.setText(getResources().getString(R.string.current_tasks_not_found));
                }

            }
        }
        DatabaseAsyncTask asyncTask = new DatabaseAsyncTask();
        asyncTask.execute(DatabaseClient.getInstance(context).getAppDatabase());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_tasks, container, false);
        textCurrentTasks = view.findViewById(R.id.textCurrentTasks);
        listCurrentTasks = view.findViewById(R.id.listCurrentTasks);
        return view;
    }
}