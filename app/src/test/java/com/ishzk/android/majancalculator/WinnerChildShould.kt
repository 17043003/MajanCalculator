package com.ishzk.android.majancalculator

import com.ishzk.android.majancalculator.domain.FannFu
import com.ishzk.android.majancalculator.domain.WinnerChild
import junit.framework.TestCase
import org.junit.Test

class WinnerChildShould {
    private val child = WinnerChild()

    @Test
    fun child_tsumo_points_are_1000_2000_when_3fann30fu(){
        val result = child.receiveTsumoPoints(FannFu(3, 30))
        TestCase.assertEquals(1000, result.childPayment)
        TestCase.assertEquals(2000, result.parentPayment)
    }

    @Test
    fun child_tsumo_points_are_2000_3900_when_4fann30fu(){
        val result = child.receiveTsumoPoints(FannFu(4, 30))
        TestCase.assertEquals(2000, result.childPayment)
        TestCase.assertEquals(3900, result.parentPayment)
    }

    @Test
    fun child_tsumo_points_are_2000_4000_when_4fann40fu(){
        val result = child.receiveTsumoPoints(FannFu(4, 40))
        TestCase.assertEquals(2000, result.childPayment)
        TestCase.assertEquals(4000, result.parentPayment)
    }

    @Test
    fun child_tsumo_points_are_3000_6000_when_6fann30fu(){
        val result = child.receiveTsumoPoints(FannFu(6, 30))
        TestCase.assertEquals(3000, result.childPayment)
        TestCase.assertEquals(6000, result.parentPayment)
    }

    @Test
    fun child_tsumo_points_are_4000_8000_when_8fann30fu(){
        val result = child.receiveTsumoPoints(FannFu(8, 30))
        TestCase.assertEquals(4000, result.childPayment)
        TestCase.assertEquals(8000, result.parentPayment)
    }

    @Test
    fun child_tsumo_points_are_6000_12000_when_11fann30fu(){
        val result = child.receiveTsumoPoints(FannFu(11, 30))
        TestCase.assertEquals(6000, result.childPayment)
        TestCase.assertEquals(12000, result.parentPayment)
    }

    @Test
    fun child_tsumo_points_are_8000_16000_when_14fann30fu(){
        val result = child.receiveTsumoPoints(FannFu(14, 30))
        TestCase.assertEquals(8000, result.childPayment)
        TestCase.assertEquals(16000, result.parentPayment)
    }
}