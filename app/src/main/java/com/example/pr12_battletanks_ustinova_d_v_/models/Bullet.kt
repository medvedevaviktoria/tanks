package com.example.pr12_battletanks_ustinova_d_v_.models

import android.view.View
import com.example.pr12_battletanks_ustinova_d_v_.enums.Direction

data class Bullet (
    val view: View,
    val direction: Direction,
    val tank: Tank,
    var canMoveFurther: Boolean = true
) {

}