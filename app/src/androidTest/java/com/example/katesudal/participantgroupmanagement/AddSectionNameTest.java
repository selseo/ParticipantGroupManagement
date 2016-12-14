package com.example.katesudal.participantgroupmanagement;

import android.support.test.rule.ActivityTestRule;
import android.widget.Button;

import com.example.katesudal.participantgroupmanagement.Activity.AddSectionNameActivity;
import com.example.katesudal.participantgroupmanagement.Model.Participant;
import com.example.katesudal.participantgroupmanagement.Model.Project;
import com.example.katesudal.participantgroupmanagement.Model.Section;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.realm.Realm;
import io.realm.RealmList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by katesuda.l on 13/12/2559.
 */

public class AddSectionNameTest {
    Realm realm;

    @Rule
    public ActivityTestRule mainActivityRule = new ActivityTestRule<>(AddSectionNameActivity.class);

    @Before
    public void setUp(){
        Realm.init(mainActivityRule.getActivity());
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @After
    public void tearDown(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Test
    public void addInvalidSectionName() throws Exception{
        onView(withId(R.id.editTextSectionName)).perform(typeText("_Thai"));
        onView(withId(R.id.buttonAddSectionName)).perform(click());
        onView(withText("Section Name should begin with letter or number. \nPlease try another.")).check(matches(isDisplayed()));
        onView(allOf(
                instanceOf(Button.class),
                withText("OK")))
                .perform(click());
        onView(withId(R.id.buttonCancelCreateSectionName)).perform(click());
    }

    @Test
    public void addDuplicateSectionName() throws Exception{
        onView(withId(R.id.editTextSectionName)).perform(typeText("Thai"));
        onView(withId(R.id.buttonAddSectionName)).perform(click());
        onView(withId(R.id.editTextSectionName)).perform(typeText("Thai"));
        onView(withId(R.id.buttonAddSectionName)).perform(click());
        onView(withText("This Section Name is Duplicate. \nPlease try another.")).check(matches(isDisplayed()));
        onView(allOf(
                instanceOf(Button.class),
                withText("OK")))
                .perform(click());
        onView(withId(R.id.buttonCancelCreateSectionName)).check(matches(isDisplayed()));
    }

    @Test
    public void createInvalidProjectName() throws Exception{
        onView(withId(R.id.editTextProjectName)).perform(typeText("-MondayPM"));
        onView(withId(R.id.buttonNextToCreateProject)).perform(click());
        onView(withText("Project Name should begin with alphabet or number. \nPlease try another.")).check(matches(isDisplayed()));
        onView(allOf(
                instanceOf(Button.class),
                withText("OK")))
                .perform(click());
        onView(withId(R.id.buttonCancelCreateSectionName)).check(matches(isDisplayed()));
    }


    @Test
    public void createDuplicateProjectName() throws Exception{
        Participant participant1 = new Participant(1,"One","Male","Staff");
        Participant participant2 = new Participant(2,"Two","Female","Staff");
        Section sectionThai = new Section(new RealmList<Participant>(),1,"Thai");
        sectionThai.getParticipantIDs().add(participant1);
        sectionThai.getParticipantIDs().add(participant2);
        Project project = new Project(1,"MondayPM",new RealmList<Section>());
        project.getSectionIDs().add(sectionThai);
        realm.beginTransaction();
        realm.copyToRealm(project);
        realm.commitTransaction();

        onView(withId(R.id.editTextProjectName)).perform(typeText("MondayPM"));
        onView(withId(R.id.buttonNextToCreateProject)).perform(click());
        onView(withText("This Project Name is Duplicate. \nPlease try another.")).check(matches(isDisplayed()));
        onView(allOf(
                instanceOf(Button.class),
                withText("OK")))
                .perform(click());
        onView(withId(R.id.buttonCancelCreateSectionName)).check(matches(isDisplayed()));
    }





}
