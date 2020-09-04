package com.cyberdynesystems.quarantineio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Reader extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    ZXingScannerView scannerView;
    DatabaseReference db;
    TextView statusView;
    Button btn;
    SimpleDateFormat simpleDateFormat;
    String dateFormat;
    String nid;
    Long lastTime;
    Long nextTime;
    Long nowTime;
    Integer count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        scannerView = findViewById(R.id.zxscan);
        statusView = findViewById(R.id.nextDateView);
        btn = findViewById(R.id.viewDataBtn);
        count = 0;
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                scannerView.setResultHandler(Reader.this);
                scannerView.startCamera();
            }
            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(Reader.this, "You must accept this permission", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
            }
        }).check();
    }

    @Override
    protected void onDestroy() {
        scannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    public void handleResult(com.google.zxing.Result rawResult) {
        try{
            Toast.makeText(Reader.this, "READ!", Toast.LENGTH_LONG).show();
            db = FirebaseDatabase.getInstance().getReference().child("Customers").child(rawResult.getText());
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    nid = dataSnapshot.child("nid").getValue().toString();
                    lastTime = Long.parseLong(dataSnapshot.child("date").getValue().toString());
                    nextTime = lastTime + 604800000;
                    dateFormat = "E MMM dd yyyy HH:mm:ss";
                    simpleDateFormat = new SimpleDateFormat(dateFormat);
                    nowTime = new Date().getTime();
                    if (nextTime < nowTime) {
                        //count ++;
                        Toast.makeText(Reader.this, count.toString(), Toast.LENGTH_LONG).show();
                        statusView.setBackgroundColor(Color.parseColor("#2FD835"));
                        statusView.setTextColor(Color.parseColor("#ffffff"));
                        //Drawable drawable = getContext().getResources().getDrawable(R.drawable.smiley);
                        statusView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
                        statusView.setPadding(230,0,280,0);
                        statusView.setText("AUTHORIZED");
                        btn.setVisibility(View.VISIBLE);
                        btn.setClickable(true);
                        //scannerView.setResultHandler(Reader.this);
                        //scannerView.startCamera();
                    } else {
                        //count ++;
                        Toast.makeText(Reader.this, count.toString(), Toast.LENGTH_LONG).show();
                        statusView.setBackgroundColor(Color.parseColor("#FF0000"));
                        statusView.setTextColor(Color.parseColor("#ffffff"));
                        statusView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_block, 0, 0, 0);
                        statusView.setPadding(220,0,260,0);
                        statusView.setText("UNAUTHORIZED");
                        btn.setVisibility(View.VISIBLE);
                        btn.setClickable(true);
                        //scannerView.setResultHandler(Reader.this);
                        //scannerView.startCamera();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Reader.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e){
            Toast.makeText(Reader.this, "Something went wrong: " + e, Toast.LENGTH_LONG).show();
        }
    }

    public void viewData(View view){
        Intent viewData = new Intent(this, ViewStatus.class);
        viewData.putExtra("NID", "33241675");
        startActivity(viewData);
    }

}
