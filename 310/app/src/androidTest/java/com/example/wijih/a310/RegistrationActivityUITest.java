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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegistrationActivityUITest {
    @Rule
    public ActivityTestRule<RegistrationActivity> mActivityRule = new ActivityTestRule<>(RegistrationActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testRegistrationBlockingAbility() {
        onView(withId(R.id.email)).perform(typeText("avalidemail@email.com"));
        onView(withId(R.id.username)).perform(typeText("thisuseralreadyexists"));
        onView(withId(R.id.number)).perform(typeText("123456"));
        onView(withId(R.id.password)).perform(typeText("notactuallyvalid!"));
        onView(withId(R.id.register)).perform(click());
        onView(withId(R.id.register)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {

    }
}
