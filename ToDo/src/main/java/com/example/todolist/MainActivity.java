package com.example.todolist;

import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Task> tasks;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editTextTask = findViewById(R.id.editTextTask);
        final ListView listViewTasks = findViewById(R.id.listViewTasks);
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        tasks = new ArrayList<>();
        adapter = new TaskAdapter(tasks);
        listViewTasks.setAdapter(adapter);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitle = editTextTask.getText().toString();
                if (!taskTitle.isEmpty()) {
                    Task task = new Task(taskTitle);
                    tasks.add(task);
                    adapter.notifyDataSetChanged();
                    editTextTask.setText("");
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        listViewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBoxTask = view.findViewById(R.id.checkBoxTask);
                Task task = tasks.get(position);
                task.setCompleted(checkBoxTask.isChecked());
                if (checkBoxTask.isChecked()) {
                    tasks.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        adapter.notifyDataSetChanged();
    }
    private class TaskAdapter extends ArrayAdapter<Task> {
        TaskAdapter(ArrayList<Task> tasks) {
            super(MainActivity.this, R.layout.task_item, R.id.textViewTask, tasks);
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
                holder = new ViewHolder();
                holder.textViewTask = convertView.findViewById(R.id.textViewTask);
                holder.checkBoxTask = convertView.findViewById(R.id.checkBoxTask);
                holder.imageViewDelete = convertView.findViewById(R.id.imageViewDelete);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final Task task = getItem(position);
            holder.textViewTask.setText(task.getTitle());
            holder.checkBoxTask.setChecked(task.isCompleted());
            holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tasks.remove(position);
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }
        private class ViewHolder {
            TextView textViewTask;
            CheckBox checkBoxTask;
            ImageView imageViewDelete;
        }
    }
    private class Task {
        private String title;
        private boolean completed;
        Task(String title) {
            this.title = title;
            this.completed = false;
        }
        String getTitle() {
            return title;
        }
        void setTitle(String title) {
            this.title = title;
        }
        boolean isCompleted() {
            return completed;
        }
        void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }
}