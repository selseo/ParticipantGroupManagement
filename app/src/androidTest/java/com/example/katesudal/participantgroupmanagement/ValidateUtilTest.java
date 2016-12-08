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
import io.realm.RealmList;

public class ValidateUtilTest {
    @Rule
    public ActivityTestRule mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void duplicateParticipantName() throws Exception {
        Realm.init(mainActivityRule.getActivity());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

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
    public void duplicateProjectName() throws Exception{
        Realm.init(mainActivityRule.getActivity());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

        Project project = new Project();
        project.setProjectID(1);
        project.setProjectName("Project");
        realm.beginTransaction();
        realm.copyToRealm(project);
        realm.commitTransaction();

        ValidateUtil validateUtil = new ValidateUtil();
        Assert.assertTrue(validateUtil.isDuplicateProjectName("Project"));
    }


}