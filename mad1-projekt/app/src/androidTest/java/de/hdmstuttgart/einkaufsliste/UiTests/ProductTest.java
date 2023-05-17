package de.hdmstuttgart.einkaufsliste.UiTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.hdmstuttgart.einkaufsliste.R;
import de.hdmstuttgart.einkaufsliste.activities.HomeActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProductTest {

    @Rule
    public ActivityScenarioRule<HomeActivity> activityScenarioRule
            = new ActivityScenarioRule<>(HomeActivity.class);

    //Needs to be executed directly after the CreateListTest.class
    //Adds a product to a list and checks if it is shown in the RecyclerView correctly

    @Test
    public void AddProductTest() {

        onView(ViewMatchers.withId(R.id.homeRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.addButton))
                .perform(click());

        onView(withId(R.id.product))
                .perform(replaceText("Chips"));

        onView(withId(R.id.submitButtonAdd))
                .perform(click());

        activityScenarioRule.getScenario().close();

        ActivityScenario.launch(HomeActivity.class);

        onView(withId(R.id.homeRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.listRecyclerView))
                .check(matches(atPosition(0, hasDescendant(withText("Chips")))));

    }

    //Checks if a checked product is correctly saved and displayed after closing and reopening the ListFragment
    @Test
    public void CheckProductTest() {

        onView(withId(R.id.homeRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.checkbox)).check(matches(isNotChecked())).perform(click());

        onView(withId(R.id.backButton))
                .perform(click());

        onView(withId(R.id.homeRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.checkbox)).check(matches(isChecked()));

    }


    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}



