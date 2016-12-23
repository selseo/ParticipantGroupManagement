package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.SpecialGroup;
import com.example.katesudal.participantgroupmanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class CreateSpecialGroupActivity extends AppCompatActivity implements View.OnClickListener{
    public static String specialGroupName;
    private Realm realm;

    @BindView(R.id.textViewSpecialGroupName)
    TextView textViewSpecialGroupName;
    @BindView(R.id.layoutAllParticipantName)
    ViewGroup layoutAllParticipantName;
    @BindView(R.id.layoutNewSpecialGroup)
    ViewGroup layoutNewSpecialGroup;
    @BindView(R.id.scrollViewAllParticipantName)
    ScrollView scrollViewAllParticipantName;
    @BindView(R.id.scrollViewNewSpecialGroup)
    ScrollView scrollViewNewSpecialGroup;
    @BindView(R.id.buttonCreateSpecialGroup)
    LinearLayout buttonCreateSpecialGroup;
    @BindView(R.id.buttonCancelCreateSpecialGroup)
    LinearLayout buttonCancelCreateSpecialGroup;
    @BindView(R.id.layoutTextViewSpecialGroupName)
    LinearLayout layoutTextViewSpecialGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_special_group);
        ButterKnife.bind(this);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        specialGroupName = getIntent().getExtras().getString("specialGroupName");
        textViewSpecialGroupName.setText(specialGroupName);

        setOnDrag();
        setOnClick();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        createParticipantNameItem(realm,layoutAllParticipantName, inflater);
    }

    private void setOnClick() {
        buttonCreateSpecialGroup.setOnClickListener(this);
        buttonCancelCreateSpecialGroup.setOnClickListener(this);
    }

    private void setOnDrag() {
        layoutTextViewSpecialGroupName.setOnDragListener(new OnDragItem());
        textViewSpecialGroupName.setOnDragListener(new OnDragItem());
        layoutAllParticipantName.setOnDragListener(new OnDragItem());
        layoutNewSpecialGroup.setOnDragListener(new OnDragItem());
        scrollViewAllParticipantName.setOnDragListener(new OnDragItem());
        scrollViewNewSpecialGroup.setOnDragListener(new OnDragItem());
        buttonCreateSpecialGroup.setOnDragListener(new OnDragItem());
        buttonCancelCreateSpecialGroup.setOnDragListener(new OnDragItem());
    }

    private void createParticipantNameItem(Realm realm, ViewGroup itemLayout, LayoutInflater inflater) {
        RealmResults<Participant> participants = realm.where(Participant.class).findAll();
        for (int participantIndex = 0; participantIndex < participants.size(); participantIndex++) {
            Participant participant = participants.get(participantIndex);

            View itemView = inflater.inflate(R.layout.item_participant_name, null);
            setParticipantNameItemBackground(participant, itemView);
            TextView itemName = (TextView) itemView.findViewById(R.id.textViewItemParticipantName);
            itemName.setText(participant.getParticipantName());
            setParticipantNameItemTextColor(participant, itemName);

            itemLayout.addView(itemView);
            itemView.setOnTouchListener(new OnTouchItem());
        }
    }

    private void setParticipantNameItemTextColor(Participant participant, TextView itemName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(participant.getParticipantGender().equals("Male")){
                itemName.setTextColor(getColor(R.color.colorVeryLightCream));
            }
            else{
                itemName.setTextColor(getColor(R.color.colorNormalWhite));
            }
        }
        else{
            if(participant.getParticipantGender().equals("Male")){
                itemName.setTextColor(ContextCompat.getColor(this,R.color.colorVeryLightCream));
            }
            else{
                itemName.setTextColor(ContextCompat.getColor(this,R.color.colorNormalWhite));
            }
        }
    }

    private void setParticipantNameItemBackground(Participant participant, View itemView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(participant.getParticipantType().equals("Staff")){
                itemView.setBackground(getDrawable(R.drawable.background_participant_staff_name_item));
            }
            else if(participant.getParticipantType().equals("Participant")){
                itemView.setBackground(getDrawable(R.drawable.background_participant_name_item));
            }
        } else {
            if(participant.getParticipantType().equals("Staff")){
                itemView.setBackgroundResource(R.drawable.background_participant_staff_name_item);
            }
            else if(participant.getParticipantType().equals("Participant")){
                itemView.setBackgroundResource(R.drawable.background_participant_name_item);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonCreateSpecialGroup){
            if(layoutNewSpecialGroup.getChildCount()<1){
                showNoSelectedParticipant();
                return;
            }
            createNewSpecialGroup();
            Intent intent = new Intent(this,ManageSpecialGroup.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonCancelCreateSpecialGroup){
            onBackPressed();
        }
    }

    private void showNoSelectedParticipant() {
        AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
        dialogErrorBuilder.setMessage("Please select participant at least one.");
        dialogErrorBuilder.setCancelable(false);
        dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                dialog.dismiss();
            }
        });
        dialogErrorBuilder.show();
    }

    private void createNewSpecialGroup() {
        SpecialGroup specialGroup = new SpecialGroup();
        specialGroup.setSpecialGroupName(specialGroupName);
        specialGroup.setSpecialGroupID(generateSpecialGroupID(realm));
        specialGroup.setParticipantIDs(addParticipantToSpecialGroup());
        realm.beginTransaction();
        realm.copyToRealm(specialGroup);
        realm.commitTransaction();
    }

    @NonNull
    private RealmList<Participant> addParticipantToSpecialGroup() {
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
        return selectedParticipantList;
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
                    pasteItem(view, dragEvent);
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

    private void pasteItem(View view, DragEvent dragEvent) {
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
