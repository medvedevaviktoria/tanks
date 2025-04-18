package com.example.pr12_battletanks_ustinova_d_v_

object GameCore {
    @Volatile
    private var isPlay = false

    fun startOrPauseTheGame() {
        isPlay = !isPlay
    }

    fun isPlaying() = isPlay

    fun pauseTheGame() {
        isPlay = false
    }
}