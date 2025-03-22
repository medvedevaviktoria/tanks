package com.example.pr12_battletanks_ustinova_d_v_.enums

enum class Material(
    val tankCanGoThrough: Boolean,
    val bulletCanGoThrough: Boolean,
    val simpleBulletCanDestroy: Boolean
) {
    EMPTY(true, true, true),
    BRICK(false, false, true),
    CONCRETE(false, false, false),
    GRASS(true, true, false),
}