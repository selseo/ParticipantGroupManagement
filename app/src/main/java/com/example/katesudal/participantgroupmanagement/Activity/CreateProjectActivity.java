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
    private ScrollView scrollViewUnselected;
    private ScrollView scrollViewSelected;
    private Project project;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        project = (Project) PreferencesService.getPreferences("Project",Project.class,this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        setContentView(R.layout.activity_create_project);
        layoutUnselectedParticipantName = (ViewGroup) findViewById(R.id.layoutUnselectedParticipantName);
        layoutGroups = (LinearLayout) findViewById(R.id.layoutGroups);
        buttonSubmitCreateProject = (LinearLayout) findViewById(R.id.buttonSubmitCreateProject);
        buttonCancelCreateProject = (LinearLayout) findViewById(R.id.buttonCancelCreateProject);
        scrollViewUnselected = (ScrollView) findViewById(R.id.scrollViewUnselected);
        scrollViewSelected = (ScrollView) findViewById(R.id.scrollViewSelected);
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
            View itemView = inflater.inflate(R.layout.item_participant_name, null);
            TextView itemName = (TextView) itemView.findViewById(R.id.textViewItemParticipantName);
            itemName.setText(participants.get(participantIndex).getParticipantName());
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
            submitNewProject();
            Intent intent = new Intent(view.getContext(), CreateProjectResultActivity.class);
            startActivity(intent);
        }
        if(view.getId()==R.id.buttonCancelCreateProject){
            onBackPressed();
        }
    }

    private void submitNewProject() {
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
