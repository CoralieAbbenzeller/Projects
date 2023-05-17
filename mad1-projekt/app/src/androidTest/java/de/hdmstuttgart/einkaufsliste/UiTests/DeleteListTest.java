package de.hdmstuttgart.einkaufsliste.UiTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.hdmstuttgart.einkaufsliste.R;
import de.hdmstuttgart.einkaufsliste.activities.HomeActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DeleteListTest {

    //needs to be executed somewhere after the CreateListTest.class

    @Rule
    public ActivityScenarioRule<HomeActivity> activityScenarioRule
            = new ActivityScenarioRule<>(HomeActivity.class);

    @Test
    public void DeleteListTest() {

        onView(ViewMatchers.withId(R.id.homeRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.deleteButton))
                .perform(click());

        onView(withText("Formula1 Dinner"))
                .check(doesNotExist());

    }
}
