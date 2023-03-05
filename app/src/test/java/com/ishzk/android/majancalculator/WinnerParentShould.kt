package com.ishzk.android.majancalculator

import com.ishzk.android.majancalculator.domain.FannFu
import com.ishzk.android.majancalculator.domain.WinnerParent
import junit.framework.TestCase.assertEquals
import org.junit.Test

class WinnerParentShould {
    private val parent = WinnerParent()

    @Test
    fun parent_tsumo_point_is_2000_all_when_3fann30fu(){
        val result = parent.receiveTsumoPoints(FannFu(3, 30))
        assertEquals(2000, result.childPayment)
    }

    @Test
    fun parent_tsumo_point_is_3900_all_when_4fann30fu(){
        val result = parent.receiveTsumoPoints(FannFu(4, 30))
        assertEquals(3900, result.childPayment)
    }

    @Test
    fun parent_tsumo_point_is_4000_all_when_4fann40fu(){
        val result = parent.receiveTsumoPoints(FannFu(4, 40))
        assertEquals(4000, result.childPayment)
    }

    @Test
    fun parent_tsumo_point_is_6000_all_when_6fann30fu(){
        val result = parent.receiveTsumoPoints(FannFu(6, 30))
        assertEquals(6000, result.childPayment)
    }

    @Test
    fun parent_tsumo_point_is_8000_all_when_8fann30fu(){
        val result = parent.receiveTsumoPoints(FannFu(8, 30))
        assertEquals(8000, result.childPayment)
    }

    @Test
    fun parent_tsumo_point_is_12000_all_when_11fann30fu(){
        val result = parent.receiveTsumoPoints(FannFu(11, 30))
        assertEquals(12000, result.childPayment)
    }

    @Test
    fun parent_tsumo_point_is_16000_all_when_14fann30fu(){
        val result = parent.receiveTsumoPoints(FannFu(14, 30))
        assertEquals(16000, result.childPayment)
    }

    @Test
    fun parent_ron_point_is_5800_when_3fann30fu(){
        val result = parent.receiveRonPoint(FannFu(3, 30))
        assertEquals(5800, result.payment)
    }

    @Test
    fun parent_ron_point_is_11600_when_4fann30fu(){
        val result = parent.receiveRonPoint(FannFu(4, 30))
        assertEquals(11600, result.payment)
    }

    @Test
    fun parent_ron_point_is_12000_when_4fann40fu(){
        val result = parent.receiveRonPoint(FannFu(4, 40))
        assertEquals(12000, result.payment)
    }

    @Test
    fun parent_ron_point_is_18000_when_6fann30fu(){
        val result = parent.receiveRonPoint(FannFu(6, 30))
        assertEquals(18000, result.payment)
    }

    @Test
    fun parent_ron_point_is_24000_when_8fann30fu(){
        val result = parent.receiveRonPoint(FannFu(8, 30))
        assertEquals(24000, result.payment)
    }

    @Test
    fun parent_ron_point_is_36000_when_11fann30fu(){
        val result = parent.receiveRonPoint(FannFu(11, 30))
        assertEquals(36000, result.payment)
    }

    @Test
    fun parent_ron_point_is_48000_when_14fann30fu(){
        val result = parent.receiveRonPoint(FannFu(14, 30))
        assertEquals(48000, result.payment)
    }
}