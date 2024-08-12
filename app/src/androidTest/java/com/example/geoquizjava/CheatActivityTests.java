package com.example.geoquizjava;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CheatActivityTests {

    @Rule
    public ActivityScenarioRule<QuizActivity> activityRule =
            new ActivityScenarioRule<>(QuizActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.geoquizjava", appContext.getPackageName());
    }

    @Test
    public void getNextQuestion() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String text1 = appContext.getResources().getString(R.string.question_australia);
        String text2 = appContext.getResources().getString(R.string.question_oceans);
        onView(withId(R.id.questionTextView)).check(matches(withText(text1)));
        onView(withId(R.id.nextButton)).perform(click());
        onView(withId(R.id.questionTextView)).check(matches(withText(text2)));
    }

    @Test
    public void questionIsShown() {
        onView(withId(R.id.questionTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void cheatMenuButtonIsShown() {
    }

    @Test
    public void answerTrueButtonIsShown() {
    }

    @Test
    public void answerFalseButtonIsShown() {
    }

}