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

public class FinishedTasksFragment extends Fragment {
    private TextView textFinishedTasks;
    private ListView listFinishedTasks;
    private Context context;

    public FinishedTasksFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        class DatabaseAsyncTask extends AsyncTask<AppDatabase, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(AppDatabase ...db) {
                return db[0].taskDao().getByIsFinished(true);
            }
            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                if(tasks.size()>0) {
                    textFinishedTasks.setText(getResources().getString(R.string.finished_tasks));
                    listFinishedTasks.setAdapter(new TaskAdapter(context, R.layout.list_tasks, tasks));
                    listFinishedTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    textFinishedTasks.setText(getResources().getString(R.string.finished_tasks_not_found));
                }

            }
        }
        DatabaseAsyncTask asyncTask = new DatabaseAsyncTask();
        asyncTask.execute(DatabaseClient.getInstance(context).getAppDatabase());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finished_tasks, container, false);

        textFinishedTasks = view.findViewById(R.id.textFinishedTasks);
        listFinishedTasks = view.findViewById(R.id.listFinishedTasks);

        return view;
    }
}