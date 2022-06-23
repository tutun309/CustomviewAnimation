package com.nmt.minhtu.tunm42_lesson4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    CustomViewCoin c;
    CustomViewHeader customViewHeader;
    Button btnClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = findViewById(R.id.custom_view);
        customViewHeader = findViewById(R.id.custom_header);
        btnClick = findViewById(R.id.btn_click_me);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.setAnimation();
                customViewHeader.setAnimation();
            }

        });

    }
}