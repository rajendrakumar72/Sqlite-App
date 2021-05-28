package com.example.todolistapp.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.AddNewTask;
import com.example.todolistapp.MainActivity;
import com.example.todolistapp.Model.ToDoModel;
import com.example.todolistapp.R;
import com.example.todolistapp.Utils.DataBaseHelper;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> mList;
    private MainActivity activity;
    private DataBaseHelper myDB;

    public ToDoAdapter(MainActivity activity, DataBaseHelper myDB) {
        this.activity = activity;
        this.myDB = myDB;
    }

    @NonNull
    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ViewHolder holder, int position) {
            final ToDoModel model=mList.get(position);
            holder.mCheckBox.setText(model.getTask());
            holder.mCheckBox.setChecked(toBoolean(model.getStatus()));
            holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        myDB.updateStatus(model.getId(),1);
                    }else {
                        myDB.updateStatus(model.getId(),0);
                    }
                }
            });

    }

    public boolean toBoolean(int num){
        return num!=0;
    }

    public Context getContext(){
        return activity;
    }

    public void setTask(List<ToDoModel> modelList){
        this.mList=modelList;
        notifyDataSetChanged();
    }

    public void deleteTask(int position){
        ToDoModel model=mList.get(position);
        myDB.deleteTask(model.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        ToDoModel model=mList.get(position);

        Bundle bundle=new Bundle();
        bundle.putInt("id",model.getId());
        bundle.putString("task",model.getTask());

        AddNewTask newTask=new AddNewTask();
        newTask.setArguments(bundle);
        newTask.show(activity.getSupportFragmentManager(),newTask.getTag());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox mCheckBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox=itemView.findViewById(R.id.mCheckBox);
        }
    }
}
