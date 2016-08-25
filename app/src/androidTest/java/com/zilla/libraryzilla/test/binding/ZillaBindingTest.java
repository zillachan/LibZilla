package com.zilla.libraryzilla.test.binding;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.test.db.po.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Description: test ZillaBinding
 *
 * @author Zilla
 * @version 1.0
 * @date 2016-08-25
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ZillaBindingTest {
    public static final String STRING_TO_BE_TYPED = "Espresso";

    /**
     * A JUnit {@link Rule @Rule} to launch your activity under test. This is a replacement
     * for {@link ActivityInstrumentationTestCase2}.
     * <p>
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link Before @Before} method.
     * <p>
     * {@link ActivityTestRule} will create and launch of the activity for you and also expose
     * the activity under test. To get a reference to the activity you can use
     * the {@link ActivityTestRule#getActivity()} method.
     */
    @Rule
    public ActivityTestRule<BindingActivity> mActivityRule = new ActivityTestRule<>(
            BindingActivity.class);

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        User user = mActivityRule.getActivity().getUser();
        onView(withId(R.id.user_id)).check(matches(withText(user.getId() + "")));
        onView(withId(R.id.user_name)).check(matches(withText(user.getName())));
        onView(withId(R.id.user_email)).check(matches(withText(user.getEmail())));
        onView(withId(R.id.user_address)).check(matches(withText(user.getAddress())));
//                .perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
//        onView(withId(R.id.changeTextBt)).perform(click());
//
//        // Check that the text was changed.
//        onView(withId(R.id.textToBeChanged)).check(matches(withText(STRING_TO_BE_TYPED)));
    }

    @Test
    public void changeText_newActivity() {
        // Type text and then press the button.
//        onView(withId(R.id.editTextUserInput)).perform(typeText(STRING_TO_BE_TYPED),
//                closeSoftKeyboard());
//        onView(withId(R.id.activityChangeTextBtn)).perform(click());
//
//        // This view is in a different Activity, no need to tell Espresso.
//        onView(withId(R.id.show_text_view)).check(matches(withText(STRING_TO_BE_TYPED)));
    }
}
