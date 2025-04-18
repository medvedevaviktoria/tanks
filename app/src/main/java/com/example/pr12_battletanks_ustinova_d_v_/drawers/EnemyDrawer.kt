package com.example.pr12_battletanks_ustinova_d_v_.drawers

import android.icu.lang.UProperty
import android.widget.FrameLayout
import com.example.pr12_battletanks_ustinova_d_v_.CELL_SIZE
import com.example.pr12_battletanks_ustinova_d_v_.Utils.checkIfChanceBiggerThanRandom
import com.example.pr12_battletanks_ustinova_d_v_.Utils.drawElement
import com.example.pr12_battletanks_ustinova_d_v_.binding
import com.example.pr12_battletanks_ustinova_d_v_.enums.CELL_TANKS_SIZE
import com.example.pr12_battletanks_ustinova_d_v_.enums.Direction
import com.example.pr12_battletanks_ustinova_d_v_.enums.Material
import com.example.pr12_battletanks_ustinova_d_v_.models.Coordinate
import com.example.pr12_battletanks_ustinova_d_v_.models.Element
import com.example.pr12_battletanks_ustinova_d_v_.models.Tank

private const val  MAX_ENEMY_AMOUNT = 20


class EnemyDrawer(
    private val container: FrameLayout,
    private val elements: MutableList<Element>
    ) {
    private val respawnList: List<Coordinate>
    private var enemyAmount = 0
    private var currentCoordinate: Coordinate
    val tanks = mutableListOf<Tank>()
    lateinit var bulletDrawer: BulletDrawer

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


    private fun drawEnemy() {
        var index = respawnList.indexOf(currentCoordinate) + 1
        if (index == respawnList.size) {
            index = 0
        }
        currentCoordinate = respawnList[index]
        val enemyTank = Tank(
            Element(
            material = Material.ENEMY_TANK,
            coordinate = currentCoordinate
            ), Direction.DOWN,
            this
        )
        enemyTank.element.drawElement(container)
        tanks.add(enemyTank)
    }


    fun moveEnemyTanks() {
        Thread(Runnable {
            while (true) {
                goThroughAllTanks()
                Thread.sleep(400)
            }
        }).start()
    }


    private fun goThroughAllTanks() {
            tanks.toList().forEach {
                it.move(it.direction,container,elements)
                if (checkIfChanceBiggerThanRandom(10))
                {
                    bulletDrawer.addNewBulletForTank(it)
                }
            }
    }


    fun startEnemyCreation() {
        Thread(Runnable {
            while (enemyAmount < MAX_ENEMY_AMOUNT) {
                drawEnemy()
                enemyAmount++
                Thread.sleep(3000)
            }
        }).start()
    }



    fun removeTank(tankIndex: Int) {
        if (tankIndex < 0) return
        tanks.removeAt(tankIndex)
    }
}
