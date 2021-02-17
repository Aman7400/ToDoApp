package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private RecyclerView mTaskRecyclerView;
    private Toolbar mToolbar ;
    private FloatingActionButton mAddTasks;

    private ArrayList<String> tasks;
    private ArrayList<String> taskIds;
    private MyDataBaseHelper dbh;

    private ProgressBar mProgress;
    private TextView mTaskRemaining;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Initialize Context */
        mContext = MainActivity.this;
        /* Initialize DB*/
        dbh = new MyDataBaseHelper(mContext);


        /* Set Toolbar */
        mToolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);

        /* Add Tasks to List */
        mAddTasks = findViewById(R.id.addTasks);
        mAddTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,AddTask.class);
                startActivity(i);
            }
        });

        /* Show Remaining Tasks */
        int count = dbh.taskCount();
        mTaskRemaining = findViewById(R.id.taskRemaining);
        mTaskRemaining.setText("Remaining tasks : "+count);

        mProgress = findViewById(R.id.progress);
        mProgress.setProgress(count);

        /* Display Tasks in RecyclerView */
        tasks = new ArrayList<>();
        taskIds = new ArrayList<>();

        showTasks();



        mTaskRecyclerView = findViewById(R.id.addedItemRecyclerView);
        mTaskRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mTaskRecyclerView.setAdapter(new TaskAdapter(mContext,MainActivity.this,taskIds,tasks) );





    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       switch (item.getItemId()){
           case R.id.removeAll :
               dbh.deleteAll();
               recreate();
               break;

           case R.id.rateUs :
               rateUs();
               break;


           default:
               throw new IllegalStateException("Unexpected value: " + item.getItemId());
       }



        return true;
    }



    private void rateUs() {

        Dialog rating = new Dialog(mContext);
        rating.setContentView(R.layout.rate_us);

        RatingBar ratingBar = rating.findViewById(R.id.ratingBar);

        Button button = rating.findViewById(R.id.submitRating);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating.dismiss();
            }
        });

        rating.show();


        }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return true;
    }

    /* Add tasks from database to list */
    void showTasks(){

        Cursor cursor = dbh.ShowItems();

        if(cursor.getCount() == 0){
            Toast.makeText(mContext, "No tasks added ", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                taskIds.add(cursor.getString(0));
                tasks.add(cursor.getString(1));
            }

        }


    }


}