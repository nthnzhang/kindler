package com.example.wijih.a310;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;

@RunWith(AndroidJUnit4.class)
public class SwipingActivityTest {

    @Rule
    public ActivityTestRule<SwipingActivity> activityTestRule = new ActivityTestRule<>(SwipingActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test1() {
        // onData(is(instanceOf(String.class)), is("Harry Potter"));
    }

    @After
    public void tearDown() throws Exception {

    }
}
