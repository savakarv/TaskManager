package com.example.todomanager;

import android.os.Parcel;
import android.os.Parcelable;

//import java.util.Date;

/**
 * Created by rathish.kannan on 12/8/15.
 */
public class Todo implements Parcelable {
    String title;
    String dueDate;
    String priority;

    @Override
    public int describeContents() {
        return 0;
    }

    public static Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel source) {
            Todo todo = new Todo();
            todo.title = source.readString();
            todo.dueDate = source.readString();
            todo.priority = source.readString();
            return todo;
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(dueDate);
        dest.writeString(priority);
    }
}
