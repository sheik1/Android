package com.example.sheikr.muziekapplicatie.Test;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sheikr.muziekapplicatie.R;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class TaskListActivity extends AppCompatActivity{

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm.init(this);
        setContentView(R.layout.activity_task_list);

        realm = Realm.getDefaultInstance();

        RealmResults<Task> tasks = realm.where(Task.class).findAll();
        tasks = tasks.sort("timestamp");
        final TaskAdapter adapter = new TaskAdapter(this, tasks);

        ListView listView = (ListView) findViewById(R.id.task_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            final Task task = (Task) adapterView.getAdapter().getItem(i);
            final EditText taskEditText = new EditText(TaskListActivity.this);
            taskEditText.setText(task.getName());
            AlertDialog dialog = new AlertDialog.Builder(TaskListActivity.this)
                    .setTitle("Edit Task")
                    .setView(taskEditText)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            changeTaskName(task.getId(), String.valueOf(taskEditText.getText()));
                        }
                    })
                    .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteTask(task.getId());
                        }
                    })
                    .create();
            dialog.show();
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            final EditText taskEditText = new EditText(TaskListActivity.this);
            AlertDialog dialog = new AlertDialog.Builder(TaskListActivity.this)
                    .setTitle("Add Task")
                    .setView(taskEditText)
                    .setPositiveButton("Add", (dialogInterface, i) -> realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.createObject(Task.class, UUID.randomUUID().toString())
                                    .setName(String.valueOf(taskEditText.getText()));
                        }
                    }))
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_delete) {
            deleteAllDone();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeTaskDone(final String taskId) {
        realm.executeTransactionAsync(realm -> {
            Task task = realm.where(Task.class).equalTo("id", taskId).findFirst();
            task.setDone(!task.isDone());
        });
    }

    private void changeTaskName(final String taskId, final String name) {
        realm.executeTransactionAsync(realm -> {
            Task task = realm.where(Task.class).equalTo("id", taskId).findFirst();
            task.setName(name);
        });
    }

    private void deleteTask(final String taskId) {
        realm.executeTransactionAsync(realm -> realm.where(Task.class).equalTo("id", taskId)
                .findFirst()
                .deleteFromRealm());
    }

    private void deleteAllDone() {
        realm.executeTransactionAsync(realm -> realm.where(Task.class).equalTo("done", true)
                .findAll()
                .deleteAllFromRealm());
    }
}
