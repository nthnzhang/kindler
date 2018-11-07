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
public class ProfileActivityUITest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testAddBookButton() {
        onView(withId(R.id.email)).perform(typeText("avalidemail@email.com"));
        onView(withId(R.id.password)).perform(typeText("validpassword"));
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.frame)).check(matches(isDisplayed()));
        onView(withId(R.id.toMatches)).perform(click());
        onView(withId(R.id.editProfileBtn)).check(matches(isDisplayed()));

        onView(withText("ADD BOOK"))
                .perform(click());
        onView(withId(R.id.bookTitle))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testEditProfileButton() {
        onView(withId(R.id.email)).perform(typeText("avalidemail@email.com"));
        onView(withId(R.id.password)).perform(typeText("validpassword"));
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.frame)).check(matches(isDisplayed()));
        onView(withId(R.id.toMatches)).perform(click());
        onView(withId(R.id.editProfileBtn)).check(matches(isDisplayed()));

        onView(withText("EDIT PROFILE"))
                .perform(click());
        onView(withId(R.id.usernameEdit))
                .check(matches(isDisplayed()));
    }


    @Test
    public void testYourBooksButton() {
        onView(withId(R.id.email)).perform(typeText("avalidemail@email.com"));
        onView(withId(R.id.password)).perform(typeText("validpassword"));
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.frame)).check(matches(isDisplayed()));
        onView(withId(R.id.toMatches)).perform(click());
        onView(withId(R.id.editProfileBtn)).check(matches(isDisplayed()));

        onView(withText("YOUR BOOKS"))
                .perform(click());
        onView(withId(R.id.verticalBooksView))
                .check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {

    }

}
