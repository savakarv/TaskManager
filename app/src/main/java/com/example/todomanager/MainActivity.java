package com.example.todomanager;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private void loadListFragment() {
        FragmentManager manager = getSupportFragmentManager();

        TodoListFragment frag =
                (TodoListFragment) manager.findFragmentByTag("TODOLISTFRAGMENT");
        if(frag == null) {
            frag = new TodoListFragment();
            FragmentTransaction trans = manager.beginTransaction();
            trans.add(R.id.mainLayout, frag, "TODOLISTFRAGMENT");
            trans.commit();
        }
    }

    public void switchToAddFragment() {
        FragmentManager manager = getSupportFragmentManager();

        TodoListFragment frag = (TodoListFragment) manager.findFragmentByTag("TODOLISTFRAGMENT");
        if(frag!= null) {
            FragmentTransaction trans = manager.beginTransaction();
            AddTodoFragment addFrag = new AddTodoFragment();

            addFrag.delegate = frag;
            trans.remove(frag);
            trans.add(R.id.mainLayout, addFrag, "ADDTODOFRAGMENT");
            trans.addToBackStack("ADD");
            trans.commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState ==  null) {
            loadListFragment();
        }

    }
}
