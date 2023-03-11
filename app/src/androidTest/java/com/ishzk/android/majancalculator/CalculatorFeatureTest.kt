package com.ishzk.android.majancalculator

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaRadioButtonInteractions.clickRadioButtonItem
import com.adevinta.android.barista.interaction.BaristaSpinnerInteractions.clickSpinnerItem
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorFeatureTest {

    val mActivityRule = ActivityScenarioRule(MainActivity::class.java)
    @Rule get

    @Test
    fun show_ron_child_3fann25fu_result(){
        clickSpinnerItem(R.id.fannSpinner, 2)
        clickSpinnerItem(R.id.fuSpinner, 1)
        clickOn(R.id.resultButton)

        assertDisplayed(R.id.resultText, "3200")
    }

    @Test
    fun show_ron_child_3fann30fu_result(){
        clickSpinnerItem(R.id.fannSpinner, 2)
        clickSpinnerItem(R.id.fuSpinner, 2)
        clickOn(R.id.resultButton)

        assertDisplayed(R.id.resultText, "3900")
    }

    @Test
    fun show_ron_parent_3fann30fu_result(){
        clickRadioButtonItem(R.id.calculateSwtichGroup, R.id.parentChildSwitch)

        clickSpinnerItem(R.id.fannSpinner, 2)
        clickSpinnerItem(R.id.fuSpinner, 2)
        clickOn(R.id.resultButton)

        assertDisplayed(R.id.resultText, "5800")
    }

    @Test
    fun show_tsumo_child_3fann30fu_result(){
        clickRadioButtonItem(R.id.calculateSwtichGroup, R.id.tsumoSwitch)

        clickSpinnerItem(R.id.fannSpinner, 2)
        clickSpinnerItem(R.id.fuSpinner, 2)
        clickOn(R.id.resultButton)

        assertDisplayed(R.id.resultText, "1000 - 2000")
    }

    @Test
    fun show_tsumo_parent_3fann30fu_result(){
        clickRadioButtonItem(R.id.calculateSwtichGroup, R.id.parentChildSwitch)
        clickRadioButtonItem(R.id.calculateSwtichGroup, R.id.tsumoSwitch)

        clickSpinnerItem(R.id.fannSpinner, 2)
        clickSpinnerItem(R.id.fuSpinner, 2)
        clickOn(R.id.resultButton)

        assertDisplayed(R.id.resultText, "2000 all")
    }
}