package com.example.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private Context mContext;
    private Activity mActivity;
    private ArrayList<String> tasks;
    private ArrayList<String> taskIDs;
    private Dialog dialog;


    public TaskAdapter(Context mContext, Activity activity, ArrayList<String> taskIDs, ArrayList<String> tasks) {
        this.mContext = mContext;
        this.tasks = tasks;
        this.taskIDs = taskIDs;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.task,parent,false);
        return new TaskViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        holder.tv.setText(tasks.get(position));
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Delete Task");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MyDataBaseHelper dbh = new MyDataBaseHelper(mContext);
                        dbh.deleteItemRow(taskIDs.get(position));
                        mActivity.recreate();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });


// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public  class  TaskViewHolder extends  RecyclerView.ViewHolder{

        LinearLayout ll ; TextView tv;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            ll = itemView.findViewById(R.id.taskCard);
            tv = itemView.findViewById(R.id.task);


        }
    }
}
