package com.example.katesudal.participantgroupmanagement;

import android.support.test.rule.ActivityTestRule;
import android.widget.Button;

import com.example.katesudal.participantgroupmanagement.Activity.AddSectionNameActivity;

import org.junit.Rule;
import org.junit.Test;

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

    @Rule
    public ActivityTestRule mainActivityRule = new ActivityTestRule<>(AddSectionNameActivity.class);

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
    public void addInvalidProjectName() throws Exception{
        onView(withId(R.id.editTextProjectName)).perform(typeText("-MondayPM"));
        onView(withId(R.id.buttonNextToCreateProject)).perform(click());
        onView(withText("Project Name should begin with alphabet or number. \nPlease try another.")).check(matches(isDisplayed()));
        onView(allOf(
                instanceOf(Button.class),
                withText("OK")))
                .perform(click());
        onView(withId(R.id.buttonCancelCreateSectionName)).check(matches(isDisplayed()));
    }



}
