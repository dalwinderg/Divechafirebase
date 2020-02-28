package com.example.divechafiebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nameet , qtet, priceet;
    Button post;
    Button retreive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameet = findViewById(R.id.name);
        priceet = findViewById(R.id.price);
        qtet = findViewById(R.id.qty);
        post = findViewById(R.id.add);
        retreive = findViewById(R.id.retreive);


        post.setOnClickListener(this);
        retreive.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.retreive:
                retreiveData();
                break;
            case R.id.add:
                addDataFirestore();
                break;
        }
    }


    public void retreiveData(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("orders")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     if(task.isSuccessful()){

                         for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                             System.out.println(documentSnapshot.getData().toString());


                         }
                     }
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void addDataFirestore(){

        String name = nameet.getText().toString();
        String qty = qtet.getText().toString();
        String price = priceet.getText().toString();


        HashMap<String,String> order = new HashMap<>();
        order.put("name",name);
        order.put ("qty",qty);
        order.put ("price",price);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("orders")
                .add(order)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                    }
                })

        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
}
