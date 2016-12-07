package com.example.katesudal.participantgroupmanagement.Activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.katesudal.participantgroupmanagement.FlowLayout;
import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.Project;
import com.example.katesudal.participantgroupmanagement.PreferencesService;
import com.example.katesudal.participantgroupmanagement.R;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class CreateProjectActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewGroup layoutUnselectedParticipantName;
    private ViewGroup layoutGroupSelectedParticipants;
    private LinearLayout layoutGroups;
    private LinearLayout buttonSubmitCreateProject;
    private LinearLayout buttonCancelCreateProject;
    private LinearLayout layoutCreateProject;
    private ScrollView scrollViewUnselected;
    private ScrollView scrollViewSelected;
    private TextView textViewCreatedProjectName;
    private Project project;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        project = (Project) PreferencesService.getPreferences("Project",Project.class,this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        setContentView(R.layout.activity_create_project);
        textViewCreatedProjectName = (TextView) findViewById(R.id.textViewCreatedProjectName);
        layoutUnselectedParticipantName = (ViewGroup) findViewById(R.id.layoutUnselectedParticipantName);
        layoutGroups = (LinearLayout) findViewById(R.id.layoutGroups);
        buttonSubmitCreateProject = (LinearLayout) findViewById(R.id.buttonSubmitCreateProject);
        buttonCancelCreateProject = (LinearLayout) findViewById(R.id.buttonCancelCreateProject);
        scrollViewUnselected = (ScrollView) findViewById(R.id.scrollViewUnselected);
        scrollViewSelected = (ScrollView) findViewById(R.id.scrollViewSelected);
        layoutCreateProject = (LinearLayout) findViewById(R.id.layoutCreateProject);
        layoutCreateProject.setOnDragListener(new OnDragItem());
        textViewCreatedProjectName.setText("Project Name : "+project.getProjectName());
        buttonSubmitCreateProject.setOnDragListener(new OnDragItem());
        buttonCancelCreateProject.setOnDragListener(new OnDragItem());
        scrollViewUnselected.setOnDragListener(new OnDragItem());
        scrollViewSelected.setOnDragListener(new OnDragItem());
        createParticipantNameItem(realm,layoutUnselectedParticipantName,inflater);
        createSectionLayout(project,inflater,layoutGroups);
        buttonSubmitCreateProject.setOnClickListener(this);
        buttonCancelCreateProject.setOnClickListener(this);
    }

    private void createParticipantNameItem(Realm realm, ViewGroup itemLayout, LayoutInflater inflater) {
        RealmResults<Participant> participants = realm.where(Participant.class).findAll();
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

    private void createSectionLayout(Project project, LayoutInflater inflater,LinearLayout layout){
        for(int sectionIndex = 0; sectionIndex<project.getSectionIDs().size();sectionIndex++){
            View layoutView = inflater.inflate(R.layout.layout_group, null);
            TextView textViewSectionName = (TextView) layoutView.findViewById(R.id.textViewGroupName);
            LinearLayout layoutGroupName = (LinearLayout) layoutView.findViewById(R.id.layoutGroupName);
            layoutGroupSelectedParticipants = (FlowLayout) layoutView.findViewById(R.id.layoutGroupSelectedParticipants);
            String sectionName = project.getSectionIDs().get(sectionIndex).getSectionName();
            textViewSectionName.setText(sectionName);
            layout.addView(layoutView);
            layoutGroupSelectedParticipants.setOnDragListener(new OnDragItem());
            layoutGroupName.setOnDragListener(new OnDragItem());
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
            containView = (ViewGroup) findViewById(R.id.layoutUnselectedParticipantName);
        }
        containView.addView(viewState);
        viewState.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.buttonSubmitCreateProject){
            submitNewProject(view);
        }
        if(view.getId()==R.id.buttonCancelCreateProject){
            onBackPressed();
        }
    }

    private void submitNewProject(View view) {
        if(layoutUnselectedParticipantName.getChildCount()>0){
            AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
            dialogErrorBuilder.setMessage("Please assign participant to any section");
            dialogErrorBuilder.setCancelable(false);
            dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        dialog.dismiss();
                    }
            });
            dialogErrorBuilder.show();
            return;
        }
        project.setProjectID((int) generateProjectID(realm));
        for(int sectionIndex=0; sectionIndex<project.getSectionIDs().size(); sectionIndex++){
            ViewGroup sectionView = (ViewGroup) layoutGroups.getChildAt(sectionIndex);
            ViewGroup sectionViewSub = (ViewGroup) sectionView.getChildAt(0);
            FlowLayout sectionViewSubContainer = (FlowLayout) sectionViewSub.getChildAt(1);
            RealmList<Participant> selectedParticipants = new RealmList<>();
            for(int participantIndex=0; participantIndex<sectionViewSubContainer.getChildCount(); participantIndex++){
                View rootContainerView = sectionViewSubContainer.getChildAt(participantIndex);
                TextView textViewNameParticipant = (TextView) rootContainerView.findViewById(R.id.textViewItemParticipantName);
                String participantName = (String) textViewNameParticipant.getText();
                RealmResults<Participant> participant = realm.where(Participant.class)
                        .equalTo("participantName",participantName)
                        .findAll();
                selectedParticipants.add(participant.first());
            }
            project.getSectionIDs().get(sectionIndex).setParticipantIDs(selectedParticipants);
            project.getSectionIDs().get(sectionIndex).setSectionID(sectionIndex+1);
        }
        realm.beginTransaction();
        realm.copyToRealm(project);
        realm.commitTransaction();
        Intent intent = new Intent(view.getContext(), CreateProjectResultActivity.class);
        intent.putExtra("projectID",project.getProjectID());
        startActivity(intent);
    }

    private long generateProjectID(Realm realm) {
        Number num = realm.where(Project.class).max("projectID");
        if(num==null){
            return 1;
        }
        else{
            return (long)num+1;
        }
    }

}
