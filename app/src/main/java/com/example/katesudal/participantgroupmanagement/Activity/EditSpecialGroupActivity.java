package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.SpecialGroup;
import com.example.katesudal.participantgroupmanagement.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by katesuda.l on 06/12/2559.
 */

public class EditSpecialGroupActivity extends AppCompatActivity implements View.OnClickListener{
    Realm realm;
    long specialGroupID;
    SpecialGroup specialGroup;
    private TextView textViewSpecialGroupName;
    private ViewGroup layoutAllParticipantName;
    private ViewGroup layoutNewSpecialGroup;
    private ScrollView scrollViewAllParticipantName;
    private ScrollView scrollViewNewSpecialGroup;
    private LinearLayout buttonCreateSpecialGroup;
    private LinearLayout buttonCancelCreateSpecialGroup;
    private LinearLayout layoutTextViewSpecialGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        setContentView(R.layout.activity_add_special_group);
        specialGroupID = getIntent().getExtras().getLong("specialGroupID");
        specialGroup = realm.where(SpecialGroup.class)
                .equalTo("specialGroupID",specialGroupID)
                .findFirst();
        textViewSpecialGroupName = (TextView) findViewById(R.id.textViewSpecialGroupName);
        layoutAllParticipantName = (ViewGroup) findViewById(R.id.layoutAllParticipantName);
        layoutNewSpecialGroup = (ViewGroup) findViewById(R.id.layoutNewSpecialGroup);
        scrollViewAllParticipantName = (ScrollView) findViewById(R.id.scrollViewAllParticipantName);
        scrollViewNewSpecialGroup = (ScrollView) findViewById(R.id.scrollViewNewSpecialGroup);
        buttonCreateSpecialGroup = (LinearLayout) findViewById(R.id.buttonCreateSpecialGroup);
        buttonCancelCreateSpecialGroup = (LinearLayout) findViewById(R.id.buttonCancelCreateSpecialGroup);
        layoutTextViewSpecialGroupName = (LinearLayout) findViewById(R.id.layoutTextViewSpecialGroupName);
        layoutTextViewSpecialGroupName.setOnDragListener(new OnDragItem());
        textViewSpecialGroupName.setText(specialGroup.getSpecialGroupName());
        layoutAllParticipantName.setOnDragListener(new OnDragItem());
        layoutNewSpecialGroup.setOnDragListener(new OnDragItem());
        scrollViewAllParticipantName.setOnDragListener(new OnDragItem());
        scrollViewNewSpecialGroup.setOnDragListener(new OnDragItem());
        buttonCreateSpecialGroup.setOnClickListener(this);
        TextView textViewButtonCreate = (TextView) buttonCreateSpecialGroup.getChildAt(0);
        textViewButtonCreate.setText("Save");
        buttonCreateSpecialGroup.setOnDragListener(new OnDragItem());
        buttonCancelCreateSpecialGroup.setOnClickListener(this);
        buttonCancelCreateSpecialGroup.setOnDragListener(new OnDragItem());
        viewAllUnselectedParticipant(specialGroup.getParticipantIDs(),inflater);
        viewParticipantInSpecialGroup(specialGroup.getParticipantIDs(),layoutNewSpecialGroup,inflater);

    }

    private void viewAllUnselectedParticipant(RealmList<Participant> participantIDs, LayoutInflater inflater) {
        RealmResults<Participant> allRealmParticipants = realm.where(Participant.class).findAll();
        List<Participant> allParticipants = new ArrayList<>();
        allParticipants.addAll(allRealmParticipants);
        allParticipants.removeAll(participantIDs);
        for (int participantIndex = 0; participantIndex < allParticipants.size(); participantIndex++) {
            Participant participant = allParticipants.get(participantIndex);
            View itemView = inflater.inflate(R.layout.item_participant_name, null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(participant.getParticipantType().equals("Staff")){
                    itemView.setBackground(getDrawable(R.drawable.background_participant_staff_name_item));
                }
                else{
                    itemView.setBackground(getDrawable(R.drawable.background_participant_name_item));
                }

            } else {
                if(participant.getParticipantType().equals("Staff")){
                    itemView.setBackgroundResource(R.drawable.background_participant_staff_name_item);
                }
                else{
                    itemView.setBackgroundResource(R.drawable.background_participant_name_item);
                }
            }
            TextView itemName = (TextView) itemView.findViewById(R.id.textViewItemParticipantName);
            itemName.setText(participant.getParticipantName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(participant.getParticipantSex().equals("Male")){
                    itemName.setTextColor(getColor(R.color.colorVeryLightCream));
                }
                else{
                    itemName.setTextColor(getColor(R.color.colorNormalWhite));
                }
            }
            else{
                if(participant.getParticipantSex().equals("Male")){
                    itemName.setTextColor(ContextCompat.getColor(this,R.color.colorVeryLightCream));
                }
                else{
                    itemName.setTextColor(ContextCompat.getColor(this,R.color.colorNormalWhite));
                }
            }
            layoutAllParticipantName.addView(itemView);
            itemView.setOnTouchListener(new OnTouchItem());
        }
    }

    private void viewParticipantInSpecialGroup(RealmList<Participant> participantIDs, ViewGroup layoutNewSpecialGroup, LayoutInflater inflater) {
        for (int participantIndex = 0; participantIndex < participantIDs.size(); participantIndex++) {
            Participant participant = participantIDs.get(participantIndex);
            View itemView = inflater.inflate(R.layout.item_participant_name, null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if(participant.getParticipantType().equals("Staff")){
                    itemView.setBackground(getDrawable(R.drawable.background_participant_staff_name_item));
                }
                else{
                    itemView.setBackground(getDrawable(R.drawable.background_participant_name_item));
                }

            } else {
                if(participant.getParticipantType().equals("Staff")){
                    itemView.setBackgroundResource(R.drawable.background_participant_staff_name_item);
                }
                else{
                    itemView.setBackgroundResource(R.drawable.background_participant_name_item);
                }
            }
            TextView itemName = (TextView) itemView.findViewById(R.id.textViewItemParticipantName);
            itemName.setText(participant.getParticipantName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(participant.getParticipantSex().equals("Male")){
                    itemName.setTextColor(getColor(R.color.colorVeryLightCream));
                }
                else{
                    itemName.setTextColor(getColor(R.color.colorNormalWhite));
                }
            }
            else{
                if(participant.getParticipantSex().equals("Male")){
                    itemName.setTextColor(ContextCompat.getColor(this,R.color.colorVeryLightCream));
                }
                else{
                    itemName.setTextColor(ContextCompat.getColor(this,R.color.colorNormalWhite));
                }
            }
            layoutNewSpecialGroup.addView(itemView);
            itemView.setOnTouchListener(new OnTouchItem());
        }
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonCreateSpecialGroup){
            saveSpecialGroup();
            Intent intent = new Intent(this,ManageSpecialGroup.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonCancelCreateSpecialGroup){
            onBackPressed();
        }
    }

    private void saveSpecialGroup() {
        RealmList<Participant> participants = new RealmList<>();
        for(int participantIndex = 0; participantIndex < layoutNewSpecialGroup.getChildCount();participantIndex++){
            View rootContainerView = layoutNewSpecialGroup.getChildAt(participantIndex);
            TextView textViewNameParticipant = (TextView) rootContainerView.findViewById(R.id.textViewItemParticipantName);
            String participantName = (String) textViewNameParticipant.getText();
            Participant participant = realm.where(Participant.class)
                    .equalTo("participantName",participantName)
                    .findFirst();
            participants.add(participant);
        }
        realm.beginTransaction();
        specialGroup.setParticipantIDs(participants);
        realm.commitTransaction();
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
