package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

public class Form extends AppCompatActivity {

    CheckBox checkBox;
    EditText txtName;
    EditText txtLastName;
    EditText txtNid;
    String nidData;
    String nameData;
    String lastNameData;
    Long date;
    ImageButton attachImageBtn;
    Button btnSave;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    IndividualModel individualModel;
    CharSequence stringExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        /*
        checkBox = findViewById(R.id.checkBox);
        checkBox.setTypeface(ResourcesCompat.getFont(Form.this, R.font.ubuntu_light));
        stringExtra = getIntent().getStringExtra("Attachment");
        if(stringExtra != "false"){
            checkBox.post(new Runnable() {
                public void run() {
                    checkBox.setChecked(true);
                }
            });
        } else {
            checkBox.post(new Runnable() {
                public void run() {
                    checkBox.setChecked(false);
                }
            });
        }

         */
        Toast.makeText(this, stringExtra, Toast.LENGTH_SHORT).show();
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        nidData = preferences.getString("NID", "");
        nameData = preferences.getString("Name","");
        lastNameData = preferences.getString("Lastname","");
        txtName = findViewById(R.id.nameInput);
        txtLastName = findViewById(R.id.lastNameInput);
        txtNid = findViewById(R.id.nidInput);
        txtName.setText(nameData);
        txtLastName.setText(lastNameData);
        txtNid.setText(nidData);
        date = new Date().getTime();
        attachImageBtn = findViewById(R.id.attachImageBtn);
        btnSave = findViewById(R.id.saveButton);
        individualModel = new IndividualModel();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customers");
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");
        attachImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.item1){
            Toast.makeText(this, "Option 1", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item2) {
            Toast.makeText(this, "Option 2", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.item3) {
            Toast.makeText(this, "Option 3", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveData(){
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("NID", txtNid.getText().toString());
        editor.putString("Name", txtName.getText().toString());
        editor.putString("Lastname", txtLastName.getText().toString());
        editor.apply();
        finish();
        individualModel.setName(txtName.getText().toString());
        individualModel.setLastname(txtLastName.getText().toString());
        individualModel.setNID(Integer.parseInt(txtNid.getText().toString().trim()));
        individualModel.setDate(date);
        databaseReference.child(txtNid.getText().toString().trim()).setValue(individualModel);
        Toast.makeText(Form.this, "Data sent to server succesfully", Toast.LENGTH_LONG).show();
    }

    public void uploadImage(){
        Intent uploadImage = new Intent(this, UploadImage.class);
        startActivity(uploadImage);
    }

}
