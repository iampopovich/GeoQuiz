package com.example.geoquizjava;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class QuizActivityTests {

    @Rule
    public ActivityScenarioRule<QuizActivity> activityRule = new ActivityScenarioRule<>(QuizActivity.class);

    public Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Test
    public void useAppContext() {
        assertEquals("com.example.geoquizjava", appContext.getPackageName());
    }

    @Test
    public void getNextQuestion() {
        String text1 = appContext.getResources().getString(R.string.question_australia);
        String text2 = appContext.getResources().getString(R.string.question_oceans);
        onView(withId(R.id.questionTextView)).check(matches(withText(text1)));
        onView(withId(R.id.nextButton)).perform(click());
        onView(withId(R.id.questionTextView)).check(matches(withText(text2)));
    }

    @Test
    public void questionIsShown() {
        String text1 = appContext.getResources().getString(R.string.question_australia);
        onView(withId(R.id.questionTextView)).check(matches(withText(text1)));
    }

    @Test
    public void cheatMenuButtonIsShown() {
        onView(withId(R.id.cheatButton)).check(matches(isDisplayed()));
    }

    @Test
    public void answerTrueButtonIsShown() {
        onView(withId(R.id.trueButton)).check(matches(isDisplayed()));
    }

    @Test
    public void answerFalseButtonIsShown() {
        onView(withId(R.id.falseButton)).check(matches(isDisplayed()));
    }
}