package com.example.todomanager;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by rathish.kannan on 12/8/15.
 */
public class TodoAdapter extends BaseAdapter {
    Context context;
    ArrayList<Todo> todos;


    static class ViewHolder {
        TextView titleTV;
        TextView dueDateTV;
        TextView priorityTV;
    }

    public TodoAdapter(Context context, ArrayList<Todo> todos) {
        this.context = context;
        this.todos = todos;
    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Object getItem(int position) {
        return todos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mainView = null;

        if(convertView == null) {

            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mainView = inflater.inflate(R.layout.row, null);
            ViewHolder vh = new ViewHolder();
            vh.titleTV =  (TextView)mainView.findViewById(R.id.textView4);
            vh.dueDateTV = (TextView) mainView.findViewById(R.id.textView6);
            vh.priorityTV = (TextView) mainView.findViewById(R.id.textView5);
            mainView.setTag(vh);

        } else {
            mainView = convertView;
        }
        Todo todo = todos.get(position);
        ViewHolder vh = (ViewHolder)mainView.getTag();
        vh.titleTV.setText(todo.getTitle());
        vh.dueDateTV.setText(todo.getDueDate());
        vh.priorityTV.setText(todo.getPriority());
        return mainView;
    }
}
