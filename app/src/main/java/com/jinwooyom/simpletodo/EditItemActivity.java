package com.jinwooyom.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private EditText etEdit;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEdit = (EditText) findViewById(R.id.et_edit);
        submit = (Button) findViewById(R.id.submit);


        etEdit.setText(getIntent().getStringExtra("editText"));
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("editText", etEdit.getText().toString());
                data.putExtra("index",getIntent().getIntExtra("index",0));
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
