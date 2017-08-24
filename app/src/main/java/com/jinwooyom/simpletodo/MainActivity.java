package com.jinwooyom.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 20;

    private EditText etNewItem;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readItems();
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        items = new ArrayList<String>();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void onAddItem(View view) {
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String newText = data.getExtras().getString("editText");
            int index = data.getExtras().getInt("index",0);
            items.set(index, newText);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                    items.remove(pos);
                    itemsAdapter.notifyDataSetChanged();
                    writeItems();
                    return true;
                }
            }
        );

        lvItems.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                    Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                    i.putExtra("editText",items.get(pos));
                    i.putExtra("index",pos);
                    startActivityForResult(i, REQUEST_CODE);
                }
            }
        );
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile,items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
