package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    EditText ed1, ed2;
    TextView res;
    Button add, sub , mul, div;
    int firstVal, secondVal, ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ed1 = findViewById(R.id.editTextNumber);
        ed2 = findViewById(R.id.editTextNumber2);

        res = findViewById(R.id.textView);

        add = findViewById(R.id.button);
        sub = findViewById(R.id.button2);
        mul = findViewById(R.id.button3);
        div = findViewById(R.id.button4);

        Toast.makeText(MainActivity.this, "Secret: 537", Toast.LENGTH_SHORT).show();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValues();
                ans = firstVal + secondVal;
                jumpToDiary(ans);
                res.setText(String.valueOf(ans));
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValues();
                ans = firstVal - secondVal;
                jumpToDiary(ans);
                res.setText(String.valueOf(ans));
            }
        });

        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValues();
                ans = firstVal * secondVal;
                jumpToDiary(ans);
                res.setText(String.valueOf(ans));
            }
        });

        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValues();
                if(secondVal!=0){
                    ans = firstVal/secondVal;
                    jumpToDiary(ans);
                    res.setText(String.valueOf(ans));
                }
                else{
                    Toast.makeText(MainActivity.this, "Cannot divide by 0", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void jumpToDiary(int num){
        if(num == 537){
            Intent intent = new Intent(MainActivity.this, Load_Diary.class);
            startActivity(intent);
        }
    }
    public void setValues(){
        firstVal = Integer.parseInt(ed1.getText().toString().isEmpty() ? "0" : ed1.getText().toString());
        secondVal = Integer.parseInt(ed2.getText().toString().isEmpty()  ? "0" : ed2.getText().toString());
    }

}
