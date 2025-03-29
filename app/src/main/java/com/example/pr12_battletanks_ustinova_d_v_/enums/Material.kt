package com.example.pr12_battletanks_ustinova_d_v_.enums

import android.icu.number.NumberFormatter.UnitWidth
import com.example.pr12_battletanks_ustinova_d_v_.R


const val CELL_SIMPLE_ELEMENT = 1
const val CELL_EAGLE_WIDTH = 4
const val CELL_EAGLE_HEIGHT = 3

enum class Material(
    val tankCanGoThrough: Boolean,
    val bulletCanGoThrough: Boolean,
    val simpleBulletCanDestroy: Boolean,
    val canExistOnlyOne: Boolean,
    val width: Int,
    val height: Int,
    val image: Int
) {
    EMPTY(
        true,
        true,
        true,
        false,
        0,
        0,
        0
        ),
    BRICK(
        false,
        false,
        true,
        false,
        CELL_SIMPLE_ELEMENT,
        CELL_SIMPLE_ELEMENT,
        R.drawable.brick
        ),
    CONCRETE(
        false,
        false,
        false,
        false,
        CELL_SIMPLE_ELEMENT,
        CELL_SIMPLE_ELEMENT,
        R.drawable.concrete
        ),
    GRASS(
        true,
        true,
        false,
        false,
        CELL_SIMPLE_ELEMENT,
        CELL_SIMPLE_ELEMENT,
        R.drawable.grass
        ),
    EAGLE(
        false,
        false,
        true,
        true,
        CELL_EAGLE_WIDTH,
        CELL_EAGLE_HEIGHT,
        R.drawable.eagle
        ),
}