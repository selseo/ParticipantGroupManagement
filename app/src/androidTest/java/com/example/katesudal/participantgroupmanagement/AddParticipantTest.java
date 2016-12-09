package com.example.katesudal.participantgroupmanagement;

import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;

import com.example.katesudal.participantgroupmanagement.Activity.AddParticipantActivity;

import org.hamcrest.Matcher;
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
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Created by katesuda.l on 09/12/2559.
 */

public class AddParticipantTest {
    Realm realm;

    @Rule
    public ActivityTestRule mainActivityRule = new ActivityTestRule<>(AddParticipantActivity.class);

    @Before
    public void setUp(){
        Realm.init(mainActivityRule.getActivity());
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Test
    public void showInValidParticipantNameDialog() throws Exception{
        onView(withId(R.id.radioMale)).perform(click());
        onView(withId(R.id.radioParticipant)).perform(click());
        onView(withId(R.id.buttonAddParticipant)).perform(click());
        onView(withText("Participant Name should begin with letter and contain only letter or number. \nPlease try another.")).check(matches(isDisplayed()));
        onView(allOf(
                instanceOf(Button.class),
                withText("OK")))
                .perform(click());
        onView(withId(R.id.buttonAddParticipant)).check(matches(isDisplayed()));
    }

    @Test
    public void showUncheckedGenderDialog() throws Exception{
        onView(withId(R.id.editTextParticipantName)).perform(typeText("Anna"));
        closeSoftKeyboard();
        onView(withId(R.id.radioParticipant)).perform(click());
        onView(withId(R.id.buttonAddParticipant)).perform(click());
        onView(withText("Gender or Type should be selected.")).check(matches(isDisplayed()));
        onView(allOf(
                instanceOf(Button.class),
                withText("OK")))
                .perform(click());
        onView(withId(R.id.buttonAddParticipant)).check(matches(isDisplayed()));
    }

    @Test
    public void showUncheckedTypeDialog() throws Exception{
        onView(withId(R.id.editTextParticipantName)).perform(typeText("Anna"));
        closeSoftKeyboard();
        onView(withId(R.id.radioFemale)).perform(click());
        onView(withId(R.id.buttonAddParticipant)).perform(click());
        onView(withText("Gender or Type should be selected.")).check(matches(isDisplayed()));
        onView(allOf(
                instanceOf(Button.class),
                withText("OK")))
                .perform(click());
        onView(withId(R.id.buttonAddParticipant)).check(matches(isDisplayed()));
    }

    @Test
    public void showUncheckedGenderAndTypeDialog() throws Exception{
        onView(withId(R.id.editTextParticipantName)).perform(typeText("Anna"));
        closeSoftKeyboard();
        onView(withId(R.id.buttonAddParticipant)).perform(click());
        onView(withText("Gender or Type should be selected.")).check(matches(isDisplayed()));
        onView(allOf(
                instanceOf(Button.class),
                withText("OK")))
                .perform(click());
        onView(withId(R.id.buttonAddParticipant)).check(matches(isDisplayed()));
    }


}
