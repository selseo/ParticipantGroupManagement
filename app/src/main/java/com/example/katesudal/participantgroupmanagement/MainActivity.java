package com.example.katesudal.participantgroupmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonEditParticipant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonEditParticipant = (Button) findViewById(R.id.buttonEditParticipant);
        buttonEditParticipant.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonEditParticipant){
            Intent intent = new Intent(view.getContext(), AddParticipantActivity.class);
            startActivity(intent);
        }
    }
}
