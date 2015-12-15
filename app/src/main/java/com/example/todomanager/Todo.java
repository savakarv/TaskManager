package com.example.todomanager;

import android.os.Parcel;
import android.os.Parcelable;

//import java.util.Date;

/**
 * Created by rathish.kannan on 12/8/15.
 */
public class Todo implements Parcelable {
    private String id;
    private String title;
    private String notes;
    private String dueDate;
    private String priority;
    private String deleted;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return this.deleted.equals("Y");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel source) {
            Todo todo = new Todo();
            todo.title = source.readString();
            todo.notes = source.readString();
            todo.dueDate = source.readString();
            todo.priority = source.readString();
            todo.deleted = source.readString();
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
        dest.writeString(notes);
        dest.writeString(dueDate);
        dest.writeString(priority);
        dest.writeString(deleted);
    }
}
