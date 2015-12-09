package com.example.todomanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by rathish.kannan on 12/8/15.
 */
public class TodoListFragment extends Fragment implements AddTodoDelegate {
    Button addButton;
    ListView todoListView;
    ArrayList<Todo> todoList = new ArrayList<Todo>();
    TodoAdapter adapter;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("TODO", todoList);
    }

    public TodoListFragment() {
        Todo todo = new Todo();
    }

    public void addTodo(Todo todo) {
        todoList.add(todo);
    }

    private void updateUI() {

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView =
                inflater.inflate(R.layout.fragment_todolist, container, false);
        todoListView =
                (ListView) fragmentView.findViewById(R.id.listView);
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(inflater.getContext());
                builder.setMessage("Are you sure you want delete the Task \"" + todoList.get(position).title + "\"");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        todoList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            }
        });
        if(savedInstanceState != null) {
            todoList = savedInstanceState.getParcelableArrayList("TODO");
        }
        adapter = new TodoAdapter(getContext(), todoList);
        todoListView.setAdapter(adapter);
        addButton = (Button) fragmentView.findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma = (MainActivity)getActivity();
                ma.switchToAddFragment();
            }
        });
        return fragmentView;
    }
}
