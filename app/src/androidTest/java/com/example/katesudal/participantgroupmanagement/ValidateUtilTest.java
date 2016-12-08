package com.example.katesudal.participantgroupmanagement;

import android.support.test.rule.ActivityTestRule;

import com.example.katesudal.participantgroupmanagement.Activity.MainActivity;
import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.Project;
import com.example.katesudal.participantgroupmanagement.Util.ValidateUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.realm.Realm;

public class ValidateUtilTest {
    Realm realm;
    @Rule
    public ActivityTestRule mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        Realm.init(mainActivityRule.getActivity());
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Test
    public void duplicateParticipantName() throws Exception {
        Participant participant = new Participant();
        participant.setParticipantName("Participant");
        participant.setParticipantID(1);
        realm.beginTransaction();
        realm.copyToRealm(participant);
        realm.commitTransaction();

        ValidateUtil validateUtil = new ValidateUtil();
        Assert.assertTrue(validateUtil.isDuplicateParticipantName("Participant"));
    }

    @Test
    public void notDuplicateParticipantName() throws Exception {
        Participant participant = new Participant();
        participant.setParticipantName("Participant");
        participant.setParticipantID(1);
        participant.setParticipantType("Staff");
        participant.setParticipantSex("Male");
        realm.beginTransaction();
        realm.copyToRealm(participant);
        realm.commitTransaction();

        ValidateUtil validateUtil = new ValidateUtil();
        Assert.assertFalse(validateUtil.isDuplicateParticipantName("Anna"));
    }

    @Test
    public void duplicateProjectName() throws Exception{
        Project project = new Project();
        project.setProjectID(1);
        project.setProjectName("Project");
        realm.beginTransaction();
        realm.copyToRealm(project);
        realm.commitTransaction();

        ValidateUtil validateUtil = new ValidateUtil();
        Assert.assertTrue(validateUtil.isDuplicateProjectName("Project"));
    }

    @Test
    public void notDuplicateProjectName() throws Exception{
        Project project = new Project();
        project.setProjectID(1);
        project.setProjectName("Project");
        realm.beginTransaction();
        realm.copyToRealm(project);
        realm.commitTransaction();

        ValidateUtil validateUtil = new ValidateUtil();
        Assert.assertFalse(validateUtil.isDuplicateProjectName("Teaching"));
    }


}