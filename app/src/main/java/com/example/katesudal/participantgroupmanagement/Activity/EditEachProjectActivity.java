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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.katesudal.participantgroupmanagement.FlowLayout;
import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.Project;
import com.example.katesudal.participantgroupmanagement.Model.Section;
import com.example.katesudal.participantgroupmanagement.R;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class EditEachProjectActivity extends AppCompatActivity implements View.OnClickListener{
    long projectID;
    Realm realm;
    Project project;
    private LinearLayout buttonSaveEditedProject;
    private LinearLayout buttonCancelEditProject;
    private LinearLayout layoutEditProject;
    private ScrollView scrollViewEditProject;
    private ViewGroup layoutGroupSelectedParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        setContentView(R.layout.activity_edit_each_project);
        projectID = getIntent().getExtras().getLong("projectID");
        project = realm.where(Project.class)
                .equalTo("projectID",projectID)
                .findFirst();
        layoutEditProject = (LinearLayout) findViewById(R.id.layoutEditProject);
        buttonSaveEditedProject = (LinearLayout) findViewById(R.id.buttonSaveEditedProject);
        buttonCancelEditProject = (LinearLayout) findViewById(R.id.buttonCancelEditProject);
        scrollViewEditProject = (ScrollView) findViewById(R.id.scrollViewEditProject);
        scrollViewEditProject.setOnDragListener(new OnDragItem());
        buttonSaveEditedProject.setOnClickListener(this);
        buttonCancelEditProject.setOnClickListener(this);
        buttonSaveEditedProject.setOnDragListener(new OnDragItem());
        buttonCancelEditProject.setOnDragListener(new OnDragItem());
        viewSectionWithParticipantItem(inflater,layoutEditProject);
    }

    private void viewSectionWithParticipantItem(LayoutInflater inflater, LinearLayout layout) {
        for(int sectionIndex = 0; sectionIndex < project.getSectionIDs().size(); sectionIndex++){
            View layoutView = inflater.inflate(R.layout.layout_group, null);
            TextView textViewSectionName = (TextView) layoutView.findViewById(R.id.textViewGroupName);
            LinearLayout layoutGroupName = (LinearLayout) layoutView.findViewById(R.id.layoutGroupName);
            layoutGroupSelectedParticipants = (FlowLayout) layoutView.findViewById(R.id.layoutGroupSelectedParticipants);
            String sectionName = project.getSectionIDs().get(sectionIndex).getSectionName();
            textViewSectionName.setText(sectionName);
            layout.addView(layoutView);
            layoutGroupSelectedParticipants.setOnDragListener(new OnDragItem());
            layoutGroupName.setOnDragListener(new OnDragItem());
            viewParticipantItemInSection(project.getSectionIDs().get(sectionIndex).getParticipantIDs(),layoutGroupSelectedParticipants,inflater);
        }
    }

    private void viewParticipantItemInSection(List<Participant> participants, ViewGroup itemLayout, LayoutInflater inflater) {
        for (int participantIndex = 0; participantIndex < participants.size(); participantIndex++) {
            Participant participant = participants.get(participantIndex);
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
            itemLayout.addView(itemView);
            itemView.setOnTouchListener(new OnTouchItem());
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonSaveEditedProject){
            saveEditProject();
            Intent intent = new Intent(this,EditProjectActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonCancelEditProject){
            onBackPressed();
        }
    }

    private void saveEditProject() {
        for(int sectionIndex = 0; sectionIndex < project.getSectionIDs().size();sectionIndex++ ){
            Section section = project.getSectionIDs().get(sectionIndex);
            ViewGroup sectionView = (ViewGroup) layoutEditProject.getChildAt(sectionIndex);
            ViewGroup sectionViewSub = (ViewGroup) sectionView.getChildAt(0);
            FlowLayout sectionViewSubContainer = (FlowLayout) sectionViewSub.getChildAt(1);
            RealmList<Participant> participantList = new RealmList<>();
            for(int participantIndex = 0; participantIndex < sectionViewSubContainer.getChildCount();participantIndex++){
                View rootContainerView = sectionViewSubContainer.getChildAt(participantIndex);
                TextView textViewNameParticipant = (TextView) rootContainerView.findViewById(R.id.textViewItemParticipantName);
                String participantName = (String) textViewNameParticipant.getText();
                Participant participant = realm.where(Participant.class)
                        .equalTo("participantName",participantName)
                        .findFirst();
                participantList.add(participant);
            }
            realm.beginTransaction();
            section.setParticipantIDs(participantList);
            realm.commitTransaction();
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
        View viewState = (View) dragEvent.getLocalState();
        ViewGroup viewgroup = (ViewGroup) viewState.getParent();
        viewgroup.removeView(viewState);
        ViewGroup containView;
        if(view.getId()==R.id.layoutGroupSelectedParticipants){
            containView = view;
        }
        else{
            containView = viewgroup;
        }
        containView.addView(viewState);
        viewState.setVisibility(View.VISIBLE);
    }
}
