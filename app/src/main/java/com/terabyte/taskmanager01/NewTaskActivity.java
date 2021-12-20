package com.terabyte.taskmanager01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class NewTaskActivity extends AppCompatActivity {
    private EditText editTaskName, editTaskDescription;
    private Switch switchIsFinished;
    private int mode;
    private long taskId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        editTaskName = findViewById(R.id.editTaskName);
        editTaskDescription = findViewById(R.id.editTaskDescription);
        switchIsFinished = findViewById(R.id.switchIsFinished);

        mode = getIntent().getExtras().getInt(Constant.INTENT_KEY_NEW_TASK_MODE);

        if(mode==Constant.MODE_CREATING) {
            findViewById(R.id.buttonDelete).setVisibility(View.GONE);
        }
        if(mode==Constant.MODE_MODIFICATION) {
            taskId = getIntent().getExtras().getLong(Constant.INTENT_KEY_TASK_ID);
            fillDataFromDatabase();
        }
    }

    public void onClickButtonCancel(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void onClickButtonApply(View view) {
        if (mode == Constant.MODE_CREATING) {
            class DatabaseAsyncTask extends AsyncTask<AppDatabase, Void, Void> {
                @Override
                protected Void doInBackground(AppDatabase ...db) {
                    Task task = new Task(editTaskName.getText().toString(), editTaskDescription.getText().toString(), switchIsFinished.isChecked());
                    db[0].taskDao().insert(task);
                    return null;
                }
                @Override
                protected void onPostExecute(Void aVoid) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    super.onPostExecute(aVoid);
                }
            }
            DatabaseAsyncTask asyncTask = new DatabaseAsyncTask();
            asyncTask.execute(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase());
        }
        if(mode == Constant.MODE_MODIFICATION) {
            class DatabaseAsyncTask extends AsyncTask<AppDatabase, Void, Void> {
                @Override
                protected Void doInBackground(AppDatabase ...db) {
                    Task task = db[0].taskDao().getById(taskId);
                    task.name = editTaskName.getText().toString();
                    task.description = editTaskDescription.getText().toString();
                    task.isFinished = switchIsFinished.isChecked();
                    db[0].taskDao().update(task);
                    return null;
                }
                @Override
                protected void onPostExecute(Void aVoid) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    super.onPostExecute(aVoid);
                }
            }
            DatabaseAsyncTask asyncTask = new DatabaseAsyncTask();
            asyncTask.execute(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase());
        }
    }

    public void onClickButtonDelete(View view) {
        if(mode==Constant.MODE_MODIFICATION) {
            class DatabaseAsyncTask extends AsyncTask<AppDatabase, Void, Void> {
                @Override
                protected Void doInBackground(AppDatabase ...db) {
                    db[0].taskDao().delete(db[0].taskDao().getById(taskId));
                    return null;
                }
                @Override
                protected void onPostExecute(Void aVoid) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    super.onPostExecute(aVoid);
                }
            }
            DatabaseAsyncTask asyncTask = new DatabaseAsyncTask();
            asyncTask.execute(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase());
        }
    }

    private void fillDataFromDatabase() {
        class DatabaseAsyncTask extends AsyncTask<AppDatabase, Void, Task> {
            @Override
            protected Task doInBackground(AppDatabase ...db) {
                return db[0].taskDao().getById(taskId);
            }

            @Override
            protected void onPostExecute(Task task) {
                super.onPostExecute(task);
                editTaskName.setText(task.name);
                editTaskDescription.setText(task.description);
                switchIsFinished.setChecked(task.isFinished);
            }
        }
        DatabaseAsyncTask asyncTask = new DatabaseAsyncTask();
        asyncTask.execute(DatabaseClient.getInstance(getApplicationContext()).getAppDatabase());
    }
}