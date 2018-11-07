package com.example.wijih.a310;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChooseLoginRegistrationActivityUITest {
    @Rule
    public ActivityTestRule<ChooseLoginRegistrationActivity> mActivityRule = new ActivityTestRule<>(ChooseLoginRegistrationActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testLoginButton() {
        onView(withText("LOGIN"))
                .perform(click());

        // Verify that we have really clicked on the icon by checking the TextView content.
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    @Test
    public void testRegisterButton() {
        onView(withText("REGISTER"))
                .perform(click());

        // Verify that we have really clicked on the icon by checking the TextView content.
        onView(withId(R.id.number)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {

    }

}