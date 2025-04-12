package com.example.pr12_battletanks_ustinova_d_v_.models

import android.icu.number.NumberFormatter.UnitWidth
import android.view.View
import com.example.pr12_battletanks_ustinova_d_v_.enums.Material

data class Element constructor(
    val viewId:Int = View.generateViewId(),
    val material: Material,
    var coordinate: Coordinate,
    val width: Int,
    val height: Int
)