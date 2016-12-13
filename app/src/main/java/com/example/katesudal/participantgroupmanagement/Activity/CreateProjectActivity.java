package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


public class CreateProjectActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.layoutUnselectedParticipantName)
    ViewGroup layoutUnselectedParticipantName;
    @BindView(R.id.layoutGroups)
    LinearLayout layoutGroups;
    @BindView(R.id.buttonCreateProject)
    LinearLayout buttonCreateProject;
    @BindView(R.id.buttonCancelCreateProject)
    LinearLayout buttonCancelCreateProject;
    @BindView(R.id.layoutCreateProject)
    LinearLayout layoutCreateProject;
    @BindView(R.id.scrollViewUnselected)
    ScrollView scrollViewUnselected;
    @BindView(R.id.scrollViewSelected)
    ScrollView scrollViewSelected;
    @BindView(R.id.textViewCreatedProjectName)
    TextView textViewCreatedProjectName;

    private Project project;
    private Realm realm;
    private boolean allSectionHasParticipant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);
        ButterKnife.bind(this);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        project = (Project) PreferencesService.getPreferences("Project",Project.class,this);
        textViewCreatedProjectName.setText("Project Name : "+project.getProjectName());

        setListener();

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        createParticipantNameItem(realm,layoutUnselectedParticipantName,inflater);
        createSectionLayout(project,inflater,layoutGroups);
    }

    private void setListener() {
        layoutCreateProject.setOnDragListener(new OnDragItem());
        buttonCreateProject.setOnDragListener(new OnDragItem());
        buttonCancelCreateProject.setOnDragListener(new OnDragItem());
        scrollViewUnselected.setOnDragListener(new OnDragItem());
        scrollViewSelected.setOnDragListener(new OnDragItem());
        buttonCreateProject.setOnClickListener(this);
        buttonCancelCreateProject.setOnClickListener(this);
    }

    private void createParticipantNameItem(Realm realm, ViewGroup itemLayout, LayoutInflater inflater) {
        RealmResults<Participant> participants = realm.where(Participant.class).findAll();
        for (int participantIndex = 0; participantIndex < participants.size(); participantIndex++) {
            Participant participant = participants.get(participantIndex);
            View itemView = inflater.inflate(R.layout.item_participant_name, null);
            TextView itemName = (TextView) itemView.findViewById(R.id.textViewItemParticipantName);
            itemName.setText(participant.getParticipantName());
            setItemNameBackground(participant, itemView);
            setItemNameTextColor(participant, itemName);
            itemLayout.addView(itemView);
            itemView.setOnTouchListener(new OnTouchItem());
        }
    }

    private void setItemNameTextColor(Participant participant, TextView itemName) {
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

    private void setItemNameBackground(Participant participant, View itemView) {
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
    }

    private void createSectionLayout(Project project, LayoutInflater inflater,LinearLayout layout){
        for(int sectionIndex = 0; sectionIndex<project.getSectionIDs().size();sectionIndex++){
            View layoutView = inflater.inflate(R.layout.layout_group, null);
            TextView textViewSectionName = (TextView) layoutView.findViewById(R.id.textViewGroupName);
            LinearLayout layoutGroupName = (LinearLayout) layoutView.findViewById(R.id.layoutGroupName);
            ViewGroup layoutGroupSelectedParticipants = (FlowLayout) layoutView.findViewById(R.id.layoutGroupSelectedParticipants);
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
        if(view.getId()==R.id.buttonCreateProject){
            createNewProject(view);
        }
        if(view.getId()==R.id.buttonCancelCreateProject){
            onBackPressed();
        }
    }

    private void createNewProject(View view) {
        if(layoutUnselectedParticipantName.getChildCount()>0){
            showDialogNotAssignAllParticipant();
            return;
        }
        allSectionHasParticipant = true;
        project.setProjectID((int) generateProjectID(realm));
        setSectionInProject();
        if(!allSectionHasParticipant) {
            showAllSectionShouldHaveParticipant();
            return;
        }
        realm.beginTransaction();
        realm.copyToRealm(project);
        realm.commitTransaction();

        Intent intent = new Intent(view.getContext(), CreateProjectResultActivity.class);
        intent.putExtra("projectID",project.getProjectID());
        startActivity(intent);
    }

    private void showAllSectionShouldHaveParticipant() {
        AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
        dialogErrorBuilder.setMessage("All section should have participant.");
        dialogErrorBuilder.setCancelable(false);
        dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                dialog.dismiss();
            }
        });
        dialogErrorBuilder.show();
    }

    private void setSectionInProject() {
        for(int sectionIndex=0; sectionIndex<project.getSectionIDs().size(); sectionIndex++){
            ViewGroup sectionView = (ViewGroup) layoutGroups.getChildAt(sectionIndex);
            ViewGroup sectionViewSub = (ViewGroup) sectionView.getChildAt(0);
            FlowLayout sectionViewSubContainer = (FlowLayout) sectionViewSub.getChildAt(1);
            RealmList<Participant> selectedParticipants = new RealmList<>();
            addParticipantToSectionInProject(sectionViewSubContainer, selectedParticipants);
            project.getSectionIDs().get(sectionIndex).setParticipantIDs(selectedParticipants);
            project.getSectionIDs().get(sectionIndex).setSectionID(sectionIndex+1);
        }
    }

    private void addParticipantToSectionInProject(FlowLayout sectionViewSubContainer, List<Participant> selectedParticipants) {
        if(sectionViewSubContainer.getChildCount()<1) {
            allSectionHasParticipant = false;
            return;
        }
        for(int participantIndex=0; participantIndex<sectionViewSubContainer.getChildCount(); participantIndex++){
            View rootContainerView = sectionViewSubContainer.getChildAt(participantIndex);
            TextView textViewNameParticipant = (TextView) rootContainerView.findViewById(R.id.textViewItemParticipantName);
            String participantName = (String) textViewNameParticipant.getText();
            RealmResults<Participant> participant = realm.where(Participant.class)
                    .equalTo("participantName",participantName)
                    .findAll();
            selectedParticipants.add(participant.first());
        }
    }

    private void showDialogNotAssignAllParticipant() {
        AlertDialog.Builder dialogErrorBuilder = new AlertDialog.Builder(this);
        dialogErrorBuilder.setMessage("Please assign all participant to any section.");
        dialogErrorBuilder.setCancelable(false);
        dialogErrorBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog,int which){
                    dialog.dismiss();
                }
        });
        dialogErrorBuilder.show();
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
