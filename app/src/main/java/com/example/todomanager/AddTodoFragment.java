package com.example.todomanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by rathish.kannan on 12/8/15.
 */
public class AddTodoFragment extends Fragment {
    EditText title;
    EditText dueDate;
    EditText priority;
    Button doneButton;
    Button cancelButton;
    AddTodoDelegate delegate;

    public AddTodoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_add_todo, container, false);
        title = (EditText) fragmentView.findViewById(R.id.editText);
        dueDate = (EditText) fragmentView.findViewById(R.id.editText2);
        priority = (EditText) fragmentView.findViewById(R.id.editText3);

        doneButton = (Button) fragmentView.findViewById(R.id.button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoTitle = title.getText().toString();
                String todoDueDate = dueDate.getText().toString();
                String todoPriority = priority.getText().toString();
                Todo todo = new Todo();
                todo.title = todoTitle;
                todo.dueDate = todoDueDate;
                todo.priority = todoPriority;
                if(delegate != null) {
                    delegate.addTodo(todo);
                }

                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        });
        cancelButton = (Button) fragmentView.findViewById(R.id.button2);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                manager.popBackStack();
            }
        });
        return fragmentView;
    }
}
