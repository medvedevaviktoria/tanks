package com.example.pr12_battletanks_ustinova_d_v_.enums

import android.icu.number.NumberFormatter.UnitWidth
import com.example.pr12_battletanks_ustinova_d_v_.R


const val CELL_SIMPLE_ELEMENT = 1
const val CELL_EAGLE_WIDTH = 4
const val CELL_EAGLE_HEIGHT = 3
const val CELL_TANKS_SIZE = 2

enum class Material(
    val tankCanGoThrough: Boolean,
    val bulletCanGoThrough: Boolean,
    val simpleBulletCanDestroy: Boolean,
    val elementsAmountOnScreen: Int,
    val width: Int,
    val height: Int,
    val image: Int?
) {
    EMPTY(
        true,
        true,
        true,
        0,
        0,
        0,
        null
    ),
    BRICK(
        false,
        false,
        true,
        0,
        CELL_SIMPLE_ELEMENT,
        CELL_SIMPLE_ELEMENT,
        R.drawable.brick
    ),
    CONCRETE(
        false,
        false,
        false,
        0,
        CELL_SIMPLE_ELEMENT,
        CELL_SIMPLE_ELEMENT,
        R.drawable.concrete
    ),
    GRASS(
        true,
        true,
        false,
        0,
        CELL_SIMPLE_ELEMENT,
        CELL_SIMPLE_ELEMENT,
        R.drawable.grass
    ),
    EAGLE(
        false,
        false,
        true,
        1,
        CELL_EAGLE_WIDTH,
        CELL_EAGLE_HEIGHT,
        R.drawable.eagle
    ),
    ENEMY_TANK(
        false,
        false,
        true,
        0,
        CELL_TANKS_SIZE,
        CELL_TANKS_SIZE,
        R.drawable.enemy_tank
    ),
    PLAYER_TANK(
    false,
    false,
    true,
    0,
    CELL_TANKS_SIZE,
    CELL_TANKS_SIZE,
    R.drawable.tank
    )
}