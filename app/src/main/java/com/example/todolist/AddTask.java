package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {

    private Context mContext;
    private EditText mTaskName;
    private Button mAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        /* Initialize Context */
        mContext  = AddTask.this;



        /* Get Task Details */
        mTaskName = findViewById(R.id.taskName);
        mAdd = findViewById(R.id.addTask);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String  taskName = mTaskName.getText().toString().trim();

                MyDataBaseHelper dbh = new MyDataBaseHelper(mContext);
                 long res =   dbh.AddItems(taskName);
                 displayToast(res);


                startActivity(new Intent(mContext,MainActivity.class));
                finish();
            }
        });


    }

    void displayToast(long result){


        //Creating the LayoutInflater instance
        LayoutInflater li = getLayoutInflater();
        View layout = li.inflate(R.layout.custom_toast,(ViewGroup) findViewById(R.id.custom_toast));

        //Creating the Toast object
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setView(layout);//setting the view of custom toast layout

        if(result == -1){
            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
        }else{
            toast.show();

        }





    }

}