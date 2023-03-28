package com.code.todolistapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.code.todolistapplication.Utils.DatabaseHandler;
import com.code.todolistapplication.activity.MainActivity;
import com.code.todolistapplication.adapter.ToDoAdapter;
import com.code.todolistapplication.bottomSheetFragment.ShowCalendarViewBottomSheet;
import com.code.todolistapplication.model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

public class CheckboxActivity extends AppCompatActivity implements DialogCloseListener{

    //calendar
//    @BindView(R.id.calendarView)
//    ImageView calendar;

    private DatabaseHandler db;

    private RecyclerView tasksRecyclerView;
//    private FloatingActionButton fab;
    private ToDoAdapter tasksAdapter;
    private List<ToDoModel> taskList;
    private Button fab;

    //gif
    private static final String URL_GIF_IMAGE ="https://cdn.dribbble.com/users/2319552/screenshots/7045247/media/70ceaeedab3ba3d92f7287f21a5cf4b6.gif";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox);

        //calendar
//        calendar.setOnClickListener(view -> {
//            ShowCalendarViewBottomSheet showCalendarViewBottomSheet = new ShowCalendarViewBottomSheet();
//            showCalendarViewBottomSheet.show(getSupportFragmentManager(), showCalendarViewBottomSheet.getTag());
//        });


        //gif
        ImageView imageViewGif = findViewById(R.id.img_gif);
        Glide.with(this).load(URL_GIF_IMAGE).into(imageViewGif);

        Objects.requireNonNull(getSupportActionBar()).hide();

        db = new DatabaseHandler(this);
        db.openDatabase();

        taskList = new ArrayList<>();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        fab = findViewById(R.id.fab);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

    }
    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
    public void dayAction(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
    public void settingAction(View view) {
        startActivity(new Intent(this, Setting.class));
    }
    public void homeAction(View view)
    {
        startActivity(new Intent(this, GiaodienActivity.class));
    }

}