package com.example.rick.rickbakker_pset4;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper helper;
    Context context;
    ArrayList<ToDo> todolist;
    ToDo todo2;
    ToDo todos1;
    ToDo todos2;
    ToDo todos3;

    SimpleCursorAdapter adapter;
    private String[] from = new String[]{DBHelper.KEY_TODO};
    private int[] to = new int[]{R.id.textitem};
    ListView listview;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.ListViewID);
        addButton = (Button) findViewById(R.id.button);
        listview.setOnItemLongClickListener(new LongListener());
        addButton.setOnClickListener(new Listener());
        context = this;
        helper = new DBHelper(this);
        Read();
        Show();

    }

    public class Listener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            EditText ToDoText = (EditText) findViewById(R.id.editText);
            String ToDoString = ToDoText.getText().toString();
            if(ToDoString.equals("")){
                Toast.makeText(MainActivity.this, "Nothing to add", Toast.LENGTH_SHORT).show();
            } else {
                todo2 = new ToDo(ToDoString);
                helper.create(todo2);
                Read();
                Show();
                ToDoText.getText().clear();
            }
        }
    }

    public void Read() {
        todolist = helper.read();
    }

    public void Show() {
        helper = new DBHelper(this);
        Cursor cursor = helper.fetch();
        adapter = new SimpleCursorAdapter(this, R.layout.item_in_list, cursor, from, to);
        adapter.notifyDataSetChanged();
        Log.d("DFSDFSdf", todolist.toString());
        if (todolist != null) {
            listview.setAdapter(adapter);
        }
        if(todolist.isEmpty()){
                String s1 = "Type in your item below";
                String s2 = "Press add to add item in to do list";
                String s3 = "Hold item to remove or set as done";
                todos1 = new ToDo(s1);
                helper.create(todos1);
                todos2 = new ToDo(s2);
                helper.create(todos2);
                todos3 = new ToDo(s3);
                helper.create(todos3);
                listview.setAdapter(adapter);
        }
    }

    private class LongListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, final long id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("What do you want to do?");
            builder.setCancelable(true);

            builder.setPositiveButton(
                    "Remove item",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            helper.Delete(id);
                            Show();
                        }
                    });

            builder.setNegativeButton(
                    "Set item to done!",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (((TextView) view.findViewById(R.id.textitem)).getText().toString().startsWith(("(Done!) "))){
                                return;
                            } else {
                                String selected = ((TextView) view.findViewById(R.id.textitem)).getText().toString();
                                String todo = "(Done!) " + selected;
                                helper.Update(id, todo);
                                Show();
                            }
                        }
                    });

            AlertDialog alert1 = builder.create();
            alert1.show();
            return true;
        }
    }
}