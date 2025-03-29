package com.example.pr12_battletanks_ustinova_d_v_.drawers

import android.widget.FrameLayout
import com.example.pr12_battletanks_ustinova_d_v_.CELL_SIZE
import com.example.pr12_battletanks_ustinova_d_v_.Utils.drawElement
import com.example.pr12_battletanks_ustinova_d_v_.enums.CELL_TANKS_SIZE
import com.example.pr12_battletanks_ustinova_d_v_.enums.Material
import com.example.pr12_battletanks_ustinova_d_v_.models.Coordinate
import com.example.pr12_battletanks_ustinova_d_v_.models.Element

private const val  MAX_ENEMY_AMOUNT = 20


class EnemyDrawer(private val container: FrameLayout) {
    private val respawnList: List<Coordinate>
    private var enemyAmount = 0
    private var currentCoordinate: Coordinate

    init {
        respawnList = getRespawnList()
        currentCoordinate = respawnList[0]
    }

    private fun getRespawnList(): List<Coordinate> {
        val respawnList = mutableListOf<Coordinate>()
        respawnList.add(Coordinate(0,0))
        respawnList.add(
            Coordinate(
                0,
                    ((container.width - container.width % CELL_SIZE) / CELL_SIZE -
                            (container.width - container.width % CELL_SIZE) / CELL_SIZE % 2) *
                            CELL_SIZE / 2 - CELL_SIZE * CELL_TANKS_SIZE
            )
        )
        respawnList.add(
            Coordinate(
                0,
                (container.width - container.width % CELL_SIZE) - CELL_SIZE * CELL_TANKS_SIZE
            )
        )
        return respawnList
    }


    private fun drawEnemy(elements: MutableList<Element>) {
        var index = respawnList.indexOf(currentCoordinate) + 1
        if (index == respawnList.size) {
            index = 0
        }
        currentCoordinate = respawnList[index]
        val enemyTankElement = Element(
            material = Material.ENEMY_TANK,
            coordinate = currentCoordinate,
            width = Material.ENEMY_TANK.width,
            height = Material.ENEMY_TANK.height,
        )
        enemyTankElement.drawElement(container)
        elements.add(enemyTankElement)
    }

    fun startEnemyDrawing(elements: MutableList<Element>) {
        Thread(Runnable {
            while (enemyAmount < MAX_ENEMY_AMOUNT) {
                drawEnemy(elements)
                enemyAmount++
                Thread.sleep(3000)
            }
        }).start()
    }
}
