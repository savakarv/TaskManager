package com.example.todomanager;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class TodoDetailActivity extends AppCompatActivity {
    ViewPager viewPager;
    FragmentStatePagerAdapter adapter;
    ArrayList<Todo> todoArray;
    int selected_item = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);
        Intent launchingIntent = getIntent();
        todoArray  = launchingIntent.getParcelableArrayListExtra("TODO_LIST");
        selected_item = launchingIntent.getIntExtra("SELECTED_TODO", 0);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                TodoDetailFragment fragment = new TodoDetailFragment();
                Todo todo = todoArray.get(position);
                Bundle b =  new Bundle();
                b.putParcelable("TODO", todo);
                b.putInt("TODO_SELECTED_ITEM", position);
                fragment.setArguments(b);
                return fragment;
            }

            @Override
            public int getCount() {
                return todoArray.size();
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(selected_item);
    }
}
