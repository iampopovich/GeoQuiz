package com.example.takblet;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.navigation.fragment.NavHostFragment;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import java.util.Objects;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class QuizFragmentTest {
  @Rule
  public ActivityScenarioRule<MainActivity> activityScenarioRule =
      new ActivityScenarioRule<>(MainActivity.class);

  @Test
  public void checkAnswerButtonIsVisible() {
    // given
    activityScenarioRule
        .getScenario()
        .onActivity(
            activity ->
                NavHostFragment.findNavController(
                        Objects.requireNonNull(
                            activity
                                .getSupportFragmentManager()
                                .findFragmentById(R.id.nav_host_fragment_activity_main)))
                    .navigate(R.id.navigation_quiz));

    // then
    onView(withId(R.id.trueButton)).check(matches(isDisplayed()));
    onView(withId(R.id.falseButton)).check(matches(isDisplayed()));
    onView(withId(R.id.nextButton)).check(matches(isDisplayed()));
    onView(withId(R.id.cheatButton)).check(matches(isDisplayed()));
  }


  @Test
  public void completeQuiz(){
    // given
    activityScenarioRule
        .getScenario()
        .onActivity(
            activity ->
                NavHostFragment.findNavController(
                        Objects.requireNonNull(
                            activity
                                .getSupportFragmentManager()
                                .findFragmentById(R.id.nav_host_fragment_activity_main)))
                    .navigate(R.id.navigation_stats));
    // then
    onView(withId(R.id.correctAnswersText)).check(matches(isDisplayed()));
    onView(withId(R.id.incorrectAnswersText)).check(matches(isDisplayed()));
    onView(withId(R.id.cheatsUsedText)).check(matches(isDisplayed()));
  }
}
