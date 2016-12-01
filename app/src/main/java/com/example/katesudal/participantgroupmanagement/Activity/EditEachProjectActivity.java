package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.ClipData;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.katesudal.participantgroupmanagement.FlowLayout;
import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.Project;
import com.example.katesudal.participantgroupmanagement.R;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class EditEachProjectActivity extends AppCompatActivity implements View.OnClickListener{
    int projectID;
    Realm realm;
    private LinearLayout buttonSaveEditedProject;
    private LinearLayout buttonCancelEditProject;
    private LinearLayout layoutEditProject;
    private ViewGroup layoutGroupSelectedParticipants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        setContentView(R.layout.activity_edit_each_project);
        projectID = getIntent().getExtras().getInt("projectID");
        layoutEditProject = (LinearLayout) findViewById(R.id.layoutEditProject);
        buttonSaveEditedProject = (LinearLayout) findViewById(R.id.buttonSaveEditedProject);
        buttonCancelEditProject = (LinearLayout) findViewById(R.id.buttonCancelEditProject);
        buttonSaveEditedProject.setOnClickListener(this);
        buttonCancelEditProject.setOnClickListener(this);
        buttonSaveEditedProject.setOnDragListener(new OnDragItem());
        buttonCancelEditProject.setOnDragListener(new OnDragItem());
        createSectionWithParticipantItem(inflater,layoutEditProject);
    }

    private void createSectionWithParticipantItem(LayoutInflater inflater, LinearLayout layout) {
        Project project = realm.where(Project.class)
                .equalTo("projectID",projectID)
                .findFirst();
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
            createParticipantItemInSection(project.getSectionIDs().get(sectionIndex).getParticipantIDs(),layoutGroupSelectedParticipants,inflater);
        }
    }

    private void createParticipantItemInSection(List<Participant> participants,ViewGroup itemLayout, LayoutInflater inflater) {
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
        if(view.getId()==R.id.buttonSaveEditedProject){
            saveEditProject();
        }
        if(view.getId()==R.id.buttonCancelEditProject){
            onBackPressed();
        }
    }

    private void saveEditProject() {
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
