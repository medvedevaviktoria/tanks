package com.example.pr12_battletanks_ustinova_d_v_.models

import android.icu.number.NumberFormatter.UnitWidth
import com.example.pr12_battletanks_ustinova_d_v_.enums.Material

data class Element(
    val viewId:Int,
    val material: Material,
    val coordinate: Coordinate,
    val width: Int,
    val height: Int
)
{

}