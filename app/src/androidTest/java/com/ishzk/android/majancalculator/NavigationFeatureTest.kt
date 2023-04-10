package com.ishzk.android.majancalculator

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaDrawerInteractions.openDrawer
import com.adevinta.android.barista.interaction.BaristaMenuClickInteractions.clickMenu
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationFeatureTest {
    val mActivityRule = ActivityScenarioRule(MainActivity::class.java)
        @Rule get

    @Test
    fun to_wait_hand_fragment(){
        openDrawer()
        clickMenu(R.id.waitHandFragment)

        assertDisplayed("fragment_wait_hand")
    }

    @Test
    fun to_calculator_fragment(){
        openDrawer()
        clickMenu(R.id.waitHandFragment)

        openDrawer()
        clickMenu(R.id.calculatorFragment)

        assertDisplayed("fragment_calculator")
    }

    @Test
    fun to_hand_point_fragment(){
        openDrawer()
        clickMenu(R.id.handPointFragment)

        assertDisplayed("fragment_hand_point")
    }
}