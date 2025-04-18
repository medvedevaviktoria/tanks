package com.example.pr12_battletanks_ustinova_d_v_

import android.app.Activity
import android.view.View
import android.view.animation.AnimationUtils
import com.example.pr12_battletanks_ustinova_d_v_.activities.SCORE_REQUEST_CODE
import com.example.pr12_battletanks_ustinova_d_v_.activities.ScoreActivity
import com.example.pr12_battletanks_ustinova_d_v_.activities.binding

class GameCore(private val activity: Activity) {
    @Volatile
    private var isPlay = false
    private var isPlayerOrBaseDestroyed = false
    private var isPlayerWin = false

    fun startOrPauseTheGame() {
        isPlay = !isPlay
    }

    fun isPlaying() = isPlay && isPlayerOrBaseDestroyed && !isPlayerWin

    fun pauseTheGame() {
        isPlay = false
    }

    fun playerWon(score: Int) {
        isPlayerWin = true
        activity.startActivityForResult(
            ScoreActivity.createIntent(activity, score),
            SCORE_REQUEST_CODE
        )
    }

    fun destroyPlayerOrBase() {
        isPlayerOrBaseDestroyed = true
        pauseTheGame()
        animateEndGame()
    }

    private fun animateEndGame() {
        activity.runOnUiThread {
            binding.gameOverText.visibility = View.VISIBLE
            val slideUp = AnimationUtils.loadAnimation(activity, R.anim.slide_up)
            binding.gameOverText.startAnimation(slideUp)
        }
    }
}