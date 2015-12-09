package com.example.todomanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by rathish.kannan on 12/8/15.
 */
public class AddTodoFragment extends Fragment {
    EditText title;
    EditText dueDate;
    EditText notes;
    Button doneButton;
    Button cancelButton;
    AddTodoDelegate delegate;
    private DatePickerDialog datePickerDialog;
    DatePickerDialog.OnDateSetListener pickerListener;
    Spinner priority;

    public AddTodoFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_add_todo, container, false);
        title = (EditText) fragmentView.findViewById(R.id.editText);
        dueDate = (EditText) fragmentView.findViewById(R.id.editText2);
        notes = (EditText) fragmentView.findViewById(R.id.editText3);
        priority = (Spinner) fragmentView.findViewById(R.id.spinner);

        //Date Picker for Due Date
        Calendar newCalendar = Calendar.getInstance();
        dueDate.setText(new StringBuilder().append(newCalendar.get(Calendar.DAY_OF_MONTH)).append("-").append(newCalendar.get(Calendar.MONTH)+1).append("-").append(newCalendar.get(Calendar.YEAR)));
        pickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, day);
                dueDate.setText(new StringBuilder().append(day).append("-").append(month+1).append("-").append(year));
            }
        };
        datePickerDialog = new DatePickerDialog(inflater.getContext(), pickerListener, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        doneButton = (Button) fragmentView.findViewById(R.id.button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoTitle = title.getText().toString();
                String todoDueDate = dueDate.getText().toString();
                String todoNotes = notes.getText().toString();
                String todoPriority = priority.getSelectedItem().toString();
                Todo todo = new Todo();
                todo.title = todoTitle;
                todo.notes = todoNotes;
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
