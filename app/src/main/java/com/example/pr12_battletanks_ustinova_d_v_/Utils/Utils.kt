package com.example.pr12_battletanks_ustinova_d_v_.Utils

import android.view.View
import com.example.pr12_battletanks_ustinova_d_v_.binding
import com.example.pr12_battletanks_ustinova_d_v_.models.Coordinate

fun View.checkViewCanMoveThroughBorder(coordinate: Coordinate) : Boolean {
    return  coordinate.top >= 0 &&
            coordinate.top + this.height <= binding.container.height &&
            coordinate.left >= 0 &&
            coordinate.left + this.width <= binding.container.width

}