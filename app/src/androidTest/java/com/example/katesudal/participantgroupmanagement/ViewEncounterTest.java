package com.example.katesudal.participantgroupmanagement;

import android.content.Intent;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import com.example.katesudal.participantgroupmanagement.Activity.ViewEncounterActivity;
import com.example.katesudal.participantgroupmanagement.Model.PairEncounter;
import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.Section;
import com.example.katesudal.participantgroupmanagement.Model.SpecialGroup;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Created by katesuda.l on 08/12/2559.
 */

public class ViewEncounterTest {
    Realm realm;
    List<PairEncounter> pairEncounters;

    @Rule
    public ActivityTestRule mainActivityRule = new ActivityTestRule<>(ViewEncounterActivity.class);

    @Before
    public void setUp(){
        Realm.init(mainActivityRule.getActivity());
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

        int startID = 1;

        RealmList<Participant> participants = new RealmList<>();
        participants.add(new Participant(1,"Anna","Female","Participant"));
        participants.add(new Participant(2,"Bobby","Male","Staff"));
        participants.add(new Participant(3,"Carl","Male","Participant"));

        RealmList<Section> sections = new RealmList<>();
        sections.add(new Section(participants,startID,"Thai"));
        realm.beginTransaction();
        realm.copyToRealm(sections);
        realm.commitTransaction();

        RealmList<SpecialGroup> specialGroups = new RealmList<>();
        SpecialGroup specialGroup = new SpecialGroup();
        specialGroup.setSpecialGroupID(startID);
        specialGroup.setSpecialGroupName("Evening");
//        specialGroup.setParticipantIDs(participants);
        specialGroup.setParticipantIDs(new RealmList<Participant>());
        specialGroup.getParticipantIDs().add(participants.get(0));
        specialGroup.getParticipantIDs().add(participants.get(1));
        specialGroups.add(specialGroup);
        realm.beginTransaction();
        realm.insertOrUpdate(specialGroups);
        realm.commitTransaction();

    }

    @Test
    public void encounterCalculate()throws Exception{
        onView(allOf(
                instanceOf(TextView.class),
                withText("2"),
                hasSibling(withText("Anna")),
                hasSibling(withText("Bobby"))))
                .check(matches(isDisplayed()));
        onView(allOf(
                instanceOf(TextView.class),
                withText("1"),
                hasSibling(withText("Anna")),
                hasSibling(withText("Carl"))))
                .check(matches(isDisplayed()));
    }

}
