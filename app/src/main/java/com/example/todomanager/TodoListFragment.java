package com.example.todomanager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by rathish.kannan on 12/8/15.
 */
public class TodoListFragment extends Fragment implements AddTodoDelegate {
    Button addButton;
    ListView todoListView;
    ArrayList<Todo> todoList = new ArrayList<Todo>();
    TodoAdapter adapter;
    int selected_item = -1;
    int checked_item = -1;
    Context context;

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100 && resultCode == Activity.RESULT_OK) {
            int item_position = data.getIntExtra("TODO_DELETE_ITEM", -1);
            Todo todo_resp = data.getParcelableExtra("TODO_LIST_VAL");
            if(todo_resp == null && item_position > -1) {
                todoList.remove(item_position);
                adapter.notifyDataSetChanged();
            }
        }
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

    public void sortDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sort By");
        final View view = LayoutInflater.from(context).inflate(R.layout.list_dialog, null, false);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup);
        if(checked_item >= 0 ) {
            radioGroup.check(checked_item);
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup);
                int checkedRadioButton = radioGroup.getCheckedRadioButtonId();
                checked_item = checkedRadioButton;
                switch (checkedRadioButton) {
                    case R.id.sort_title_asc:
                        Collections.sort(todoList, new Comparator<Todo>() {
                            public int compare(Todo todo1, Todo todo2) {
                                return todo1.title.compareToIgnoreCase(todo2.title);
                            }
                        });
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.sort_title_desc:
                        Collections.sort(todoList, Collections.reverseOrder(new Comparator<Todo>() {
                            public int compare(Todo todo1, Todo todo2) {
                                return todo1.title.compareToIgnoreCase(todo2.title);
                            }
                        }));
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.sort_prior_asc:
                        Collections.sort(todoList, new Comparator<Todo>() {
                            public int compare(Todo todo1, Todo todo2) {
                                return todo1.priority.compareToIgnoreCase(todo2.priority);
                            }
                        });
                        adapter.notifyDataSetChanged();
                        break;
                    case R.id.sort_prior_desc:
                        Collections.sort(todoList, Collections.reverseOrder(new Comparator<Todo>() {
                            public int compare(Todo todo1, Todo todo2) {
                                return todo1.priority.compareToIgnoreCase(todo2.priority);
                            }
                        }));
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checked_item = -1;
                dialog.cancel();
            }
        });
        builder.setView(view);
        builder.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_activity_bar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("Test", "Item id: " + item.getItemId());
        switch (item.getItemId()) {
            case R.id.action_sort:
                sortDialog(context);
                break;
        }
        return true;

    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View fragmentView =
                inflater.inflate(R.layout.fragment_todolist, container, false);
        context = inflater.getContext();
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
        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TodoDetailActivity.class);
                intent.putParcelableArrayListExtra("TODO_LIST", todoList);
                intent.putExtra("SELECTED_TODO", position);
                startActivityForResult(intent, 100);
            }
        });
        if(savedInstanceState != null) {
            todoList = savedInstanceState.getParcelableArrayList("TODO");
            selected_item = savedInstanceState.getInt("SELECTED_ITEM");
            if(selected_item > -1) {
                createDialog(inflater.getContext(), selected_item);
            }
            if(checked_item > -1) {
                sortDialog(inflater.getContext());
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
