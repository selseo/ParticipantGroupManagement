package com.example.katesudal.participantgroupmanagement.Activity;

import android.content.ClipData;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.Project;
import com.example.katesudal.participantgroupmanagement.PreferencesService;
import com.example.katesudal.participantgroupmanagement.R;

import io.realm.Realm;
import io.realm.RealmResults;

public class CreateProjectActivity extends AppCompatActivity {
    private ViewGroup layoutUnselectedParticipantName;
    private Realm realm;
    private int number = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        Project project = (Project) PreferencesService.getPreferences("Project",Project.class,this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        setContentView(R.layout.activity_create_project);
        layoutUnselectedParticipantName = (ViewGroup) findViewById(R.id.layoutUnselectedParticipantName);
        createParticipantNameItem(realm,layoutUnselectedParticipantName,inflater);
        setOnDragListener();
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

    private void createSectionLayout(){

    }

    private void setOnDragListener(){
        layoutUnselectedParticipantName.setOnDragListener(new OnDragItem());
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
                    View viewState = (View) dragEvent.getLocalState();
                    ViewGroup viewgroup = (ViewGroup) viewState.getParent();
                    viewgroup.removeView(viewState);
                    ViewGroup containView = (ViewGroup) view;
                    containView.addView(viewState);
                    viewState.setVisibility(View.VISIBLE);
                    break;
                }
                case DragEvent.ACTION_DRAG_ENDED: {
                    return (true);
                }
                default:
                    break;
            }
            return true;
        }
    }
}
