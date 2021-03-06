package com.cyberdynesystems.quarantineio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Status extends AppCompatActivity {

    TextView fullNameView;
    TextView nidView;
    TextView lastDateView;
    TextView timeSpanView;
    TextView nextDateView;
    TextView permissionView;
    DatabaseReference Customers;
    DatabaseReference TimeSpan;
    SimpleDateFormat simpleDateFormat;
    String nidData;
    String timeSpan;
    String dateFormat;
    String lastDate;
    String nextDate;
    Long lastTime;
    Long nextTime;
    Long nowTime;
    FirebaseAuth mAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        // Initialize Firebase Auth
        /*
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Status.this, "Log Success", Toast.LENGTH_SHORT).show();
                            user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Status.this, "Log Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

         */
        fullNameView = findViewById(R.id.fullNameView);
        nidView = findViewById(R.id.nidView);
        lastDateView = findViewById(R.id.lastDateView);
        timeSpanView = findViewById(R.id.timeSpanView);
        nextDateView = findViewById(R.id.nextDateView);
        permissionView = findViewById(R.id.permissionView);
        TimeSpan = FirebaseDatabase.getInstance().getReference().child("TimeSpan").child("Days");
        TimeSpan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot timeSpanDataSnapshot) {
                timeSpan = timeSpanDataSnapshot.getValue().toString();
                SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                nidData = preferences.getString("NID", "");
                Customers = FirebaseDatabase.getInstance().getReference().child("Customers").child(nidData);
                Customers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot customerDataSnapshot) {
                        lastTime = Long.parseLong(customerDataSnapshot.child("date").getValue().toString());
                        nextTime = lastTime + 604800000;
                        Locale locale = new Locale("en", "US");
                        /*
                        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
                        dateFormatSymbols.setWeekdays(new String[]{
                                "Sin Uso",
                                "Domingo",
                                "Lunes",
                                "Martes",
                                "Miercoles",
                                "Jueves",
                                "Viernes",
                                "Sabado",
                        });
                        dateFormatSymbols.setMonths(new String[]{
                                "Enero",
                                "Febrero",
                                "Marzo",
                                "Abril",
                                "Mayo",
                                "Junio",
                                "Julio",
                                "Septiembre",
                                "Octubre",
                                "Noviembre",
                                "Diciembre",
                        });
                         */
                        simpleDateFormat = new SimpleDateFormat("EEEE MMMM d',' yyyy", locale);
                        fullNameView.setText(customerDataSnapshot.child("name").getValue().toString() + " " + customerDataSnapshot.child("lastname").getValue().toString());
                        nidView.setText(customerDataSnapshot.child("nid").getValue().toString());
                        lastDate = simpleDateFormat.format(new Date(lastTime));
                        nextDate = simpleDateFormat.format(new Date(nextTime));
                        lastDateView.setText(lastDate);
                        timeSpanView.setText(timeSpan + " days");
                        nextDateView.setText(nextDate);
                        nowTime = new Date().getTime();
                        if (nextTime < nowTime) {
                            permissionView.setBackgroundColor(Color.parseColor("#2FD835"));
                            permissionView.setTextColor(Color.parseColor("#ffffff"));
                            permissionView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
                            permissionView.setPadding(230,0,280,0);
                            permissionView.setText("AUTHORIZED");
                        } else {
                            permissionView.setBackgroundColor(Color.parseColor("#FF0000"));
                            permissionView.setTextColor(Color.parseColor("#ffffff"));
                            permissionView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_block, 0, 0, 0);
                            permissionView.setPadding(200,0,240,0);
                            permissionView.setText("UNAUTHORIZED");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError customerDatabaseError) {
                        Toast.makeText(Status.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError timeSpanDatabaseError) {
                Toast.makeText(Status.this, "Something went wrong...", Toast.LENGTH_LONG).show();
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

}
