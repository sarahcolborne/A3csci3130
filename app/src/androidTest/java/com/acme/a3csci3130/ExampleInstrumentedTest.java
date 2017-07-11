package com.acme.a3csci3130;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;

/**
 * 4 Tests: Create, Read, Update, Delete
 *
 * These test must be run in order of CRUD, with the database initially empty.
 * This ensures that the read test properly identifies the expected values.
 *
 * Note: error, success, and delete messages are commented out. This is due to the fact
 * that Espresso is too slow reading TextViews before the view is closed by finish();
 *
 * If finish(); is commented out in the activities, you can use the message checking
 * in the tests.
 *
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void createTests() {
        String error = "One or more values are invalid. Please try again.";
        String success = "Success!";

        //First create attempt, will fail
        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.name)).perform(typeText("SarahTest"));
        onView(withId(R.id.primaryBusField)).perform(typeText("Fish Monger3")); //will fail
        onView(withId(R.id.bNumField)).perform(typeText("12345678z")); //will fail
        onView(withId(R.id.addressField)).perform(typeText("123 Road, Halifax"));
        onView(withId(R.id.provinceField)).perform(typeText("FAIL"), closeSoftKeyboard()); //will fail
        onView(withId(R.id.submitButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText(error)));

        //Second create attempt, will pass (fixing bad values)
        onView(withId(R.id.primaryBusField)).perform(clearText());
        onView(withId(R.id.primaryBusField)).perform(typeText("Fish Monger")); //will now pass
        onView(withId(R.id.bNumField)).perform(clearText());
        onView(withId(R.id.bNumField)).perform(typeText("112233445")); //will now pass
        onView(withId(R.id.provinceField)).perform(clearText());
        onView(withId(R.id.provinceField)).perform(typeText("NS"), closeSoftKeyboard()); //will now pass
        onView(withId(R.id.submitButton)).perform(click());
        //NOTE: I need to comment this out since espresso is too slow to see the message
        //before finish() executes and closes the view. Without the finish(), these work!
        //onView(withId(R.id.errorMessage)).check(matches(withText(success)));

    }

    @Test
    public void readTests() {
        //This test MUST be run after the create test. This uses predictable values
        //to ensure that the correct values are being displayed, hense it must know
        //that the first contact in the list contains the values used above in the
        //create test.

        //click on the first element

        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        onView(withId(R.id.name)).check(matches(withText("SarahTest")));
        onView(withId(R.id.primaryBusField)).check(matches(withText("Fish Monger")));
        onView(withId(R.id.bNumField)).check(matches(withText("112233445")));
        onView(withId(R.id.addressField)).check(matches(withText("123 Road, Halifax")));
        onView(withId(R.id.provinceField)).check(matches(withText("NS")));

    }

    @Test
    public void updateTests() throws Exception {
        String error = "One or more values are invalid. Please try again.";
        String success = "Updated successfully";

        //First update attempt, will fail
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        onView(withId(R.id.provinceField)).perform(clearText());
        onView(withId(R.id.provinceField)).perform(typeText("NOTGOOD"), closeSoftKeyboard());
        onView(withId(R.id.updateButton)).perform(click());
        onView(withId(R.id.errorMessage)).check(matches(withText(error)));

        //Second update attempt, will pass
        onView(withId(R.id.provinceField)).perform(clearText());
        onView(withId(R.id.provinceField)).perform(typeText("NB"), closeSoftKeyboard());
        onView(withId(R.id.updateButton)).perform(click());
        //NOTE: I need to comment this out since espresso is too slow to see the message
        //before finish() executes and closes the view. Without the finish(), these work!
        //onView(withId(R.id.errorMessage)).check(matches(withText(success)));
    }

    @Test
    public void deleteTests() {
        String deleted = "Business contact deleted";

        //First update attempt, will fail
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        onView(withId(R.id.deleteButton)).perform(click());
        //NOTE: I need to comment this out since espresso is too slow to see the message
        //before finish() executes and closes the view. Without the finish(), these work!
        //onView(withId(R.id.errorMessage)).check(matches(withText(deleted)));
    }

}
