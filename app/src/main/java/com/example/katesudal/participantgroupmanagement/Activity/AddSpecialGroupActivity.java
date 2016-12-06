package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.SpecialGroup;
import com.example.katesudal.participantgroupmanagement.R;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class AddSpecialGroupActivity extends AppCompatActivity implements View.OnClickListener{
    private String specialGroupName;
    private TextView textViewSpecialGroupName;
    private ViewGroup layoutAllParticipantName;
    private ViewGroup layoutNewSpecialGroup;
    private ScrollView scrollViewAllParticipantName;
    private ScrollView scrollViewNewSpecialGroup;
    private Button buttonCreateSpecialGroup;
    private Button buttonCancelCreateSpecialGroup;
    private SpecialGroup specialGroup;
    private Realm realm;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_special_group);
        realm = Realm.getDefaultInstance();
        specialGroupName = getIntent().getExtras().getString("specialGroupName");
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        textViewSpecialGroupName = (TextView) findViewById(R.id.textViewSpecialGroupName);
        layoutAllParticipantName = (ViewGroup) findViewById(R.id.layoutAllParticipantName);
        layoutNewSpecialGroup = (ViewGroup) findViewById(R.id.layoutNewSpecialGroup);
        scrollViewAllParticipantName = (ScrollView) findViewById(R.id.scrollViewAllParticipantName);
        scrollViewNewSpecialGroup = (ScrollView) findViewById(R.id.scrollViewNewSpecialGroup);
        buttonCreateSpecialGroup = (Button) findViewById(R.id.buttonCreateSpecialGroup);
        buttonCancelCreateSpecialGroup = (Button) findViewById(R.id.buttonCancelCreateSpecialGroup);
        textViewSpecialGroupName.setText(specialGroupName);
        textViewSpecialGroupName.setOnDragListener(new OnDragItem());
        layoutAllParticipantName.setOnDragListener(new OnDragItem());
        layoutNewSpecialGroup.setOnDragListener(new OnDragItem());
        scrollViewAllParticipantName.setOnDragListener(new OnDragItem());
        scrollViewNewSpecialGroup.setOnDragListener(new OnDragItem());
        buttonCreateSpecialGroup.setOnClickListener(this);
        buttonCancelCreateSpecialGroup.setOnClickListener(this);
        createParticipantNameItem(realm,layoutAllParticipantName,inflater);
    }

    private void createParticipantNameItem(Realm realm, ViewGroup itemLayout, LayoutInflater inflater) {
        RealmResults<Participant> participants = realm.where(Participant.class).findAll();
        for (int participantIndex = 0; participantIndex < participants.size(); participantIndex++) {
            View itemView = inflater.inflate(R.layout.item_participant_name, null);
            TextView itemName = (TextView) itemView.findViewById(R.id.textViewItemParticipantName);
            itemName.setText(participants.get(participantIndex).getParticipantName());
            itemLayout.addView(itemView);
            itemView.setOnTouchListener(new OnTouchItem());
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonCreateSpecialGroup){
            createNewSpecialGroup();
            Intent intent = new Intent(this,ManageSpecialGroup.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonCancelCreateSpecialGroup){
            onBackPressed();
        }
    }

    private void createNewSpecialGroup() {
        specialGroup = new SpecialGroup();
        specialGroup.setSpecialGroupName(specialGroupName);
        specialGroup.setSpecialGroupID(generateSpecialGroupID(realm));
        RealmList<Participant> selectedParticipantList = new RealmList<>();
        for(int participantIndex=0; participantIndex<layoutNewSpecialGroup.getChildCount(); participantIndex++){
            View rootContainerView = layoutNewSpecialGroup.getChildAt(participantIndex);
            TextView textViewNameParticipant = (TextView) rootContainerView.findViewById(R.id.textViewItemParticipantName);
            String participantName = (String) textViewNameParticipant.getText();
            RealmResults<Participant> participant = realm.where(Participant.class)
                    .equalTo("participantName",participantName)
                    .findAll();
            selectedParticipantList.add(participant.first());
        }
        specialGroup.setParticipantIDs(selectedParticipantList);
        realm.beginTransaction();
        realm.copyToRealm(specialGroup);
        realm.commitTransaction();
    }

    private long generateSpecialGroupID(Realm realm) {
        Number num = realm.where(SpecialGroup.class).max("specialGroupID");
        if(num==null){
            return 1;
        }
        else{
            return (long)num+1;
        }
    }

    class OnTouchItem implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent arg1) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.startDragAndDrop(data, shadow, v, 0);
            } else {
                v.startDrag(data, shadow, v, 0);
            }
            v.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    class OnDragItem implements View.OnDragListener {

        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            final int action = dragEvent.getAction();
            switch (action) {

                case DragEvent.ACTION_DRAG_STARTED:
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:
                    break;

                case DragEvent.ACTION_DROP: {
                    pasteItem((ViewGroup) view, dragEvent);
                    break;
                }
                case DragEvent.ACTION_DRAG_ENDED: {
                    break;
                }
                default:
                    break;
            }
            return true;
        }
    }

    private void pasteItem(ViewGroup view, DragEvent dragEvent) {
        ViewGroup viewState = (ViewGroup) dragEvent.getLocalState();
        ViewGroup viewgroup = (ViewGroup) viewState.getParent();
        viewgroup.removeView(viewState);
        ViewGroup containView;
        if(view.getId()==R.id.layoutNewSpecialGroup||view.getId()==R.id.scrollViewNewSpecialGroup){
            containView = (ViewGroup) findViewById(R.id.layoutNewSpecialGroup);
        }
        else{
            containView = (ViewGroup) findViewById(R.id.layoutAllParticipantName);
        }
        containView.addView(viewState);
        viewState.setVisibility(View.VISIBLE);
    }
}
