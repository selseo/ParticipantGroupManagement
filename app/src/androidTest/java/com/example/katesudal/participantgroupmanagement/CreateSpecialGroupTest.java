package com.example.katesudal.participantgroupmanagement;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;

import com.example.katesudal.participantgroupmanagement.Activity.ManageSpecialGroup;
import com.example.katesudal.participantgroupmanagement.Model.Participant;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.realm.Realm;

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

public class CreateSpecialGroupTest {
    Realm realm;

    @Rule
    public ActivityTestRule mainActivityRule = new ActivityTestRule<>(ManageSpecialGroup.class);

    @Before
    public void setUp(){
        Realm.init(mainActivityRule.getActivity());
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

        Participant participant1 = new Participant(1,"Anna","Female","Staff");
        Participant participant2 = new Participant(2,"Bobby","Male","Participant");

        realm.beginTransaction();
        realm.copyToRealm(participant1);
        realm.copyToRealm(participant2);
        realm.commitTransaction();
    }

    @After
    public void tearDown(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Test
    public void showNoAddedParticipantDialog() throws Exception{
        SystemClock.sleep(1000);
        onView(withId(R.id.editTextSpecialGroupName)).perform(typeText("Food-Morning-Monday"));
        closeSoftKeyboard();
        onView(withId(R.id.buttonAddSpecialGroupName)).perform(click());
        onView(withId(R.id.buttonCreateSpecialGroup)).perform(click());
        onView(withText("Please select participant at least one.")).check(matches(isDisplayed()));
        onView(allOf(
                instanceOf(Button.class),
                withText("OK")))
                .perform(click());
        onView(withId(R.id.buttonCreateSpecialGroup)).check(matches(isDisplayed()));
    }

}
