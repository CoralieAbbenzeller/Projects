package de.hdmstuttgart.einkaufsliste.UiTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static de.hdmstuttgart.einkaufsliste.UiTests.ProductTest.atPosition;

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
public class CreateListTest {

    @Rule
    public ActivityScenarioRule<HomeActivity> activityScenarioRule
            = new ActivityScenarioRule<>(HomeActivity.class);

    @Test
    public void createListTest() {

        onView(withId(R.id.createButton))
                .perform(click());

        onView(withId(R.id.listNameTextCreate))
                .perform(replaceText("Formula1 Dinner"), closeSoftKeyboard());

        onView(withId(R.id.saveButtonCreate))
                .perform(click());

        onView(withId(R.id.homeRecyclerView))
                .check(matches(atPosition(0, hasDescendant(withText("Formula1 Dinner")))));
    }
}
