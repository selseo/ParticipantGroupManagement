package com.example.katesudal.participantgroupmanagement;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.widget.Button;

import com.example.katesudal.participantgroupmanagement.Activity.AddSectionNameActivity;
import com.example.katesudal.participantgroupmanagement.Model.Participant;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import io.realm.Realm;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Created by katesuda.l on 09/12/2559.
 */

public class CreateProjectTest {
    Realm realm;

    @Rule
    public ActivityTestRule mainActivityRule = new ActivityTestRule<>(AddSectionNameActivity.class);

    @Before
    public void setUp() {
        Realm.init(mainActivityRule.getActivity());
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

        Participant participant1 = new Participant(1, "Anna", "Female", "Staff");
        Participant participant2 = new Participant(2, "Bobby", "Male", "Participant");
        Participant participant3 = new Participant(3, "Catherine", "Female", "Participant");
        Participant participant4 = new Participant(4, "David", "Male", "Staff");
        Participant participant5 = new Participant(5, "Emma", "Female", "Participant");

        realm.beginTransaction();
        realm.copyToRealm(participant1);
        realm.copyToRealm(participant2);
        realm.copyToRealm(participant3);
        realm.copyToRealm(participant4);
        realm.copyToRealm(participant5);
        realm.commitTransaction();
    }

    @After
    public void tearDown() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Test
    public void showNotAssignAllParticipantDialog() throws Exception {
        SystemClock.sleep(1000);
        onView(withId(R.id.editTextProjectName)).perform(typeText("Project"));
        onView(withId(R.id.editTextSectionName)).perform(typeText("Thai"));
        onView(withId(R.id.buttonAddSectionName)).perform(click());
        onView(withId(R.id.editTextSectionName)).perform(typeText("Science"));
        onView(withId(R.id.buttonAddSectionName)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.buttonNextToCreateProject)).perform(click());
        onView(withId(R.id.buttonCreateProject)).perform(click());
        onView(withText("Please assign all participant to any section.")).check(matches(isDisplayed()));
        onView(allOf(
                instanceOf(Button.class),
                withText("OK")))
                .perform(click());
        onView(withId(R.id.buttonCreateProject)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonCancelCreateProject)).perform(click());
        onView(withId(R.id.buttonAddSectionName)).check(matches(isDisplayed()));
    }

    @Test
    public void createProjectSuccess() throws Exception{
        SystemClock.sleep(1000);
        onView(withId(R.id.editTextProjectName)).perform(typeText("Project"));
        onView(withId(R.id.editTextSectionName)).perform(typeText("Thai"));
        onView(withId(R.id.buttonAddSectionName)).perform(click());
        onView(withId(R.id.editTextSectionName)).perform(typeText("Science"));
        onView(withId(R.id.buttonAddSectionName)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.buttonNextToCreateProject)).perform(click());
        UiDevice device;
        device = UiDevice.getInstance(getInstrumentation());
        List<UiObject2> objItemNames = device.findObjects(
                By.res("com.example.katesudal.participantgroupmanagement","textViewItemParticipantName"));
        List<UiObject2> objSections = device.findObjects(
                By.res("com.example.katesudal.participantgroupmanagement","layoutGroupSelectedParticipants"));
//        for(int objItemNamesIndex=0;objItemNamesIndex<objItemNames.size();objItemNamesIndex++){
//            if(objItemNamesIndex<objSections.size())
//                objItemNames.get(objItemNamesIndex)
//                        .drag(objSections
//                                .get(objItemNamesIndex)
//                                .getVisibleCenter());
//            else
//                objItemNames.get(objItemNamesIndex)
//                        .drag(objSections
//                                .get(0)
//                                .getVisibleCenter());
//            int p = objItemNamesIndex%objSections.size();
//        }
        for(int objItemNamesIndex=0; objItemNamesIndex<objItemNames.size() ; objItemNamesIndex++){
            objItemNames
                    .get(objItemNamesIndex)
                    .drag(objSections
                            .get(objItemNamesIndex % objSections.size())
                            .getVisibleCenter());
        }
        onView(withId(R.id.buttonCreateProject)).perform(click());
        onView(withText("Preoject : Project\n" +
                "Thai : Anna, Catherine, David, Emma\n" +
                "Science : Bobby"));
    }

    @Ignore
    @Test
    public void someSectionHasNoParticipant() throws Exception{
        SystemClock.sleep(1000);
        onView(withId(R.id.editTextProjectName)).perform(typeText("Project"));
        onView(withId(R.id.editTextSectionName)).perform(typeText("Thai"));
        onView(withId(R.id.buttonAddSectionName)).perform(click());
        onView(withId(R.id.editTextSectionName)).perform(typeText("Science"));
        onView(withId(R.id.buttonAddSectionName)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.buttonNextToCreateProject)).perform(click());
        UiDevice device;
        device = UiDevice.getInstance(getInstrumentation());
        List<UiObject2> objItemNames = device.findObjects(
                By.res("com.example.katesudal.participantgroupmanagement","textViewItemParticipantName"));
        List<UiObject2> objSections = device.findObjects(
                By.res("com.example.katesudal.participantgroupmanagement","layoutGroupSelectedParticipants"));
        for(int objItemNamesIndex=0;objItemNamesIndex<objItemNames.size();objItemNamesIndex++){
            objItemNames.get(objItemNamesIndex)
                    .drag(objSections
                            .get(0)
                            .getVisibleCenter());
        }
        onView(withId(R.id.buttonCreateProject)).perform(click());
        onView(withText("All section should have participant.")).check(matches(isDisplayed()));
        onView(allOf(
                instanceOf(Button.class),
                withText("OK")))
                .perform(click());
        onView(withId(R.id.buttonCreateProject)).check(matches(isDisplayed()));
        onView(withId(R.id.buttonCancelCreateProject)).perform(click());
        onView(withId(R.id.buttonAddSectionName)).check(matches(isDisplayed()));
    }
}
