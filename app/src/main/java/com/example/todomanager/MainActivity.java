package com.example.todomanager;

import android.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class MainActivity extends AppCompatActivity {

    private void loadRecycleBin() {
        FragmentManager manager = getSupportFragmentManager();

        RecycleBinFragment frag =
                (RecycleBinFragment) manager.findFragmentByTag("RECYCLEBINFRAGMENT");
        if(frag == null) {
            frag = new RecycleBinFragment();
            FragmentTransaction trans = manager.beginTransaction();
            trans.add(R.id.mainLayout, frag, "RECYCLEBINFRAGMENT");
            trans.commit();
        }
    }

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
