package com.example.todomanager;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
    int selected_item = -1;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("TODO", todoList);
        outState.putInt("SELECTED_ITEM", selected_item);
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

    public void createDialog(Context context, final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want delete the Task \"" + todoList.get(position).title + "\"");
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                todoList.remove(position);
                selected_item = -1;
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selected_item = -1;
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View fragmentView =
                inflater.inflate(R.layout.fragment_todolist, container, false);
        todoListView =
                (ListView) fragmentView.findViewById(R.id.listView);
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selected_item = position;
                createDialog(inflater.getContext(), position);
                return true;
            }
        });
        if(savedInstanceState != null) {
            todoList = savedInstanceState.getParcelableArrayList("TODO");
            selected_item = savedInstanceState.getInt("SELECTED_ITEM");
            if(selected_item > -1) {
                createDialog(inflater.getContext(), selected_item);
            }
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
