package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.todolistapp.Adapter.ToDoAdapter;
import com.example.todolistapp.Model.ToDoModel;
import com.example.todolistapp.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogueCloseListener{

    RecyclerView recyclerView;
    FloatingActionButton fab;
    DataBaseHelper myDB;

    List<ToDoModel> modelList;
    ToDoAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        fab=findViewById(R.id.fab);

        myDB=new DataBaseHelper(MainActivity.this);
        modelList=new ArrayList<>();
        adapter=new ToDoAdapter(MainActivity.this,myDB);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        modelList=myDB.getAllTask();
        Collections.reverse(modelList);
        adapter.setTask(modelList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.getInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onDialogueClose(DialogInterface dialogInterface) {
        modelList=myDB.getAllTask();
        Collections.reverse(modelList);
        adapter.setTask(modelList);
        adapter.notifyDataSetChanged();
    }
}