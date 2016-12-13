package com.example.katesudal.participantgroupmanagement;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.widget.Button;

import com.example.katesudal.participantgroupmanagement.Activity.ManageSpecialGroup;
import com.example.katesudal.participantgroupmanagement.Model.Participant;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import io.realm.Realm;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
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
    public void setUp() {
        Realm.init(mainActivityRule.getActivity());
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

        Participant participant1 = new Participant(1, "Anna", "Female", "Staff");
        Participant participant2 = new Participant(2, "Bobby", "Male", "Participant");

        realm.beginTransaction();
        realm.copyToRealm(participant1);
        realm.copyToRealm(participant2);
        realm.commitTransaction();
    }

    @After
    public void tearDown() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Test
    public void showNoAddedParticipantDialog() throws Exception {
        SystemClock.sleep(1000);
        onView(withId(R.id.editTextSpecialGroupName)).perform(typeText("Food"));
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

    @Test
    public void createSpecialGroupSuccess() throws Exception{
        SystemClock.sleep(1000);
        onView(withId(R.id.editTextSpecialGroupName)).perform(typeText("Food"));
        closeSoftKeyboard();
        onView(withId(R.id.buttonAddSpecialGroupName)).perform(click());
        UiDevice device;
        device = UiDevice.getInstance(getInstrumentation());
        UiObject objItemAnna = device.findObject(new UiSelector()
                .className("android.widget.LinearLayout")
                .childSelector(new UiSelector()
                        .text("Anna")));
        UiObject objItemBobby = device.findObject(new UiSelector()
                .className("android.widget.LinearLayout")
                .childSelector(new UiSelector()
                        .text("Bobby")));
        UiObject objGroupFood = device.findObject(new UiSelector()
                .resourceId("com.example.katesudal.participantgroupmanagement:id/layoutNewSpecialGroup"));
        objItemBobby.dragTo(objGroupFood,3);
        objItemAnna.dragTo(objGroupFood,3);
        onView(withId(R.id.buttonCreateSpecialGroup)).perform(click());
        onView(withText("Food")).check(matches(isDisplayed()));
        onView(withId(R.id.iconEditProject)).perform(click());
        SystemClock.sleep(500);
        onView(allOf(
                withId(R.id.textViewItemParticipantName),
                withText("Anna"),
                isDescendantOfA(
                        withId(R.id.layoutNewSpecialGroup)
                ))).check(matches(isDisplayed()));
        onView(allOf(
                withId(R.id.textViewItemParticipantName),
                withText("Bobby"),
                isDescendantOfA(
                        withId(R.id.layoutNewSpecialGroup)
                ))).check(matches(isDisplayed()));
    }
}
