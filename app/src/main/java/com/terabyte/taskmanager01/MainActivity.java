package com.terabyte.taskmanager01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.frameFragment);
        if(fragment==null) {
            fragment = new CurrentTasksFragment(getApplicationContext());
            fragmentManager.beginTransaction().add(R.id.frameFragment, fragment).commit();
        }
    }

    public void onClickButtonCurrentTasks(View view) {
        if(!(fragment instanceof CurrentTasksFragment)) {
            fragment = new CurrentTasksFragment(getApplicationContext());
            fragmentManager.beginTransaction().replace(R.id.frameFragment, fragment).commit();
        }
    }

    public void onClickButtonNewTask(View view) {
        Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
        intent.putExtra(Constant.INTENT_KEY_NEW_TASK_MODE, Constant.MODE_CREATING);
        startActivity(intent);
    }

    public void onClickButtonFinishedTasks(View view) {
        if(!(fragment instanceof FinishedTasksFragment)) {
            fragment = new FinishedTasksFragment(getApplicationContext());
            fragmentManager.beginTransaction().replace(R.id.frameFragment, fragment).commit();
        }
    }
}