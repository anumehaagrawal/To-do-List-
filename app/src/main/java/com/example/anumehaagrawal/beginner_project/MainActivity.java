package com.example.anumehaagrawal.beginner_project;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TaskHelper mHelper;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelper=new TaskHelper(this);
        mListView =(ListView) findViewById(R.id.listtodo);
        updateUI();




    }

    private void updateUI(){
        ArrayList<String> taskList=new ArrayList<>();
        SQLiteDatabase db =mHelper.getReadableDatabase();
        Cursor cursor=db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }
        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item,
                    R.id.textView,
                    taskList);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
        cursor.close();
        db.close();

    }
    public void deleteTask(View view){
        View parent=(View) view.getParent();
        TextView taskview = (TextView) parent.findViewById(R.id.textView);
        String task= String.valueOf(taskview.getText());
        SQLiteDatabase db= mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId())
        {
            case R.id.action_add_task :
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new task")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                SQLiteDatabase db =mHelper.getWritableDatabase();
                                ContentValues values=new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE,task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
