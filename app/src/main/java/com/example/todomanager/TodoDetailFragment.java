package com.example.todomanager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.BitSet;

/**
 * Created by rathish.kannan on 12/11/15.
 */
public class TodoDetailFragment extends Fragment {
    Todo todo;
    int selected_item=-1;
    Button backButton;
    Button deleteButton;
    Button editButton;
    TextView title;
    TextView dueDate;
    TextView priority;
    TextView notes;
    boolean is_delete=false;

    public TodoDetailFragment() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("TODO_ITEM", todo);
        outState.putInt("SELECTED_ITEM", selected_item);
        outState.putBoolean("IS_DELETE", is_delete);
    }

    private void updateUI() {

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void createDialog(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want delete the Task \"" + todo.title + "\"");
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                is_delete = false;
                Todo null_todo = null;
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("TODO_DELETE_ITEM", selected_item);
                intent.putExtra("TODO_LIST_VAL", null_todo);
                getActivity().setResult(Activity.RESULT_OK, intent);
                TodoDetailActivity todoDetailActivity = (TodoDetailActivity)getActivity();
                todoDetailActivity.finish();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                is_delete = false;
                dialog.cancel();
            }
        });
        builder.show();
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView =
                inflater.inflate(R.layout.fragment_todo_detail, container, false);
        title = (TextView) fragmentView.findViewById(R.id.textView9);
        dueDate = (TextView) fragmentView.findViewById(R.id.textView11);
        priority = (TextView) fragmentView.findViewById(R.id.textView13);
        notes = (TextView) fragmentView.findViewById(R.id.textView15);
        if(savedInstanceState != null) {
            todo = savedInstanceState.getParcelable("TODO_ITEM");
            selected_item = savedInstanceState.getInt("SELECTED_ITEM");
            is_delete = savedInstanceState.getBoolean("IS_DELETE");
            if(is_delete) {
                createDialog(inflater.getContext());
            }
        }
        backButton = (Button) fragmentView.findViewById(R.id.button5);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoDetailActivity todoDetailActivity = (TodoDetailActivity)getActivity();
                todoDetailActivity.finish();
            }
        });
        editButton = (Button) fragmentView.findViewById(R.id.button3);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        deleteButton = (Button) fragmentView.findViewById(R.id.button4);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_delete = true;
                createDialog(inflater.getContext());
            }
        });
        Bundle args = getArguments();
        todo = args.getParcelable("TODO");
        selected_item = args.getInt("TODO_SELECTED_ITEM");
        title.setText(todo.title);
        dueDate.setText(todo.dueDate);
        priority.setText(todo.priority);
        notes.setText(todo.notes);
        return fragmentView;
    }
}
