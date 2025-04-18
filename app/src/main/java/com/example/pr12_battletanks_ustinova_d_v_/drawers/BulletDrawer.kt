package com.example.pr12_battletanks_ustinova_d_v_.drawers

import android.app.Activity
import android.view.GestureDetector
import android.view.View
import android.widget.ActionMenuView
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.pr12_battletanks_ustinova_d_v_.CELL_SIZE
import com.example.pr12_battletanks_ustinova_d_v_.R
import com.example.pr12_battletanks_ustinova_d_v_.Utils.checkViewCanMoveThroughBorder
import com.example.pr12_battletanks_ustinova_d_v_.Utils.getElementByCoordinates
import com.example.pr12_battletanks_ustinova_d_v_.Utils.getTankByCoordinates
import com.example.pr12_battletanks_ustinova_d_v_.Utils.getViewCoordinate
import com.example.pr12_battletanks_ustinova_d_v_.Utils.runOnUiThread
import com.example.pr12_battletanks_ustinova_d_v_.enums.Direction
import com.example.pr12_battletanks_ustinova_d_v_.enums.Material
import com.example.pr12_battletanks_ustinova_d_v_.models.Bullet
import com.example.pr12_battletanks_ustinova_d_v_.models.Coordinate
import com.example.pr12_battletanks_ustinova_d_v_.models.Element
import com.example.pr12_battletanks_ustinova_d_v_.models.Tank

private const val BULLET_HEIGHT = 15
private const val BULLET_WIDTH = 15


class BulletDrawer(
    private val container: FrameLayout,
    private val elements:MutableList<Element>,
    private val enemyDrawer: EnemyDrawer
    ) {

    init {
        moveAllBullets()
    }

    private val allBullets = mutableListOf<Bullet>()

    fun addNewBulletForTank(tank: Tank) {
        val view = container.findViewById<View>(tank.element.viewId) ?: return
        if (tank.alreadyHasBullet()) return
        allBullets.add(Bullet(createBullet(view, tank.direction), tank.direction, tank))
    }

    private fun Tank.alreadyHasBullet(): Boolean =
        allBullets.firstOrNull {it.tank == this} != null

    private fun moveAllBullets() {
        Thread(Runnable {
            while (true) {
                interactWithAllBullets()
                Thread.sleep(30)
            }
        }).start()
    }

    private fun interactWithAllBullets() {
        allBullets.toList().forEach { bullet ->
            val view = bullet.view
            if (bullet.canBulletGoFurther()) {
                when (bullet.direction) {
                    Direction.UP -> (view.layoutParams as FrameLayout.LayoutParams).topMargin -= BULLET_HEIGHT
                    Direction.DOWN -> (view.layoutParams as FrameLayout.LayoutParams).topMargin += BULLET_HEIGHT
                    Direction.LEFT -> (view.layoutParams as FrameLayout.LayoutParams).leftMargin -= BULLET_HEIGHT
                    Direction.RIGHT -> (view.layoutParams as FrameLayout.LayoutParams).leftMargin += BULLET_HEIGHT
                }
                chooseBehaviorInTermsOfDirections(bullet)
                container.runOnUiThread {
                    container.removeView(view)
                    container.addView(view)
                }
            } else {
                stopBullet(bullet)
            }
            bullet.stopIntersectingBullets()
        }
        val removingList = allBullets.filter { !it.canMoveFurther }
        removingList.forEach {
            stopBullet(it)
            container.runOnUiThread {
                container.removeView(it.view)
            }
        }
        removeInconsistentBullets()
    }

    private fun removeInconsistentBullets() {
        val removingList = allBullets.filter { !it.canMoveFurther }
        removingList.forEach {
            container.runOnUiThread {
                container.removeView(it.view)
            }
        }
        allBullets.removeAll(removingList)
    }

    private fun Bullet.stopIntersectingBullets() {
        val bulletCoordinate = this.view.getViewCoordinate()
        for (bulletInList in allBullets) {
            val coordinateList = bulletInList.view.getViewCoordinate()
            if (this == bulletInList) {
                continue
            }
            if (coordinateList == bulletCoordinate) {
                stopBullet(this)
                stopBullet(bulletInList)
                return
            }
        }
    }

    private fun Bullet.canBulletGoFurther() =
        this.view.checkViewCanMoveThroughBorder(this.view.getViewCoordinate())
                && this.canMoveFurther


    private fun chooseBehaviorInTermsOfDirections(bullet: Bullet) {
        when (bullet.direction) {
            Direction.DOWN, Direction.UP -> {
                compareCollections(getCoordinatesForTopOrBottomDirection(bullet), bullet)
            }
            Direction.LEFT, Direction.RIGHT -> {
                compareCollections(getCoordinatesForLeftOrRightDirection(bullet),bullet)
            }
        }
    }

    private fun compareCollections(detectedCoordinateList: List<Coordinate>, bullet: Bullet) {
        for (coordinate in detectedCoordinateList) {
            var element = getElementByCoordinates(coordinate, elements)
            if (element== null) {
                element = getTankByCoordinates(coordinate, enemyDrawer.tanks)
            }
            if (element == bullet.tank.element) {
                continue
            }
            removeElementsAndStopBullet(element, bullet)

        }

    }

    private fun removeElementsAndStopBullet(element: Element?, bullet: Bullet) {
        if (element != null) {
            if (element.material.bulletCanGoThrough) {
                return
            }
            if (bullet.tank.element.material == Material.ENEMY_TANK
                && element.material == Material.ENEMY_TANK
                ) {
                stopBullet(bullet)
                return
            }
            if (element.material.simpleBulletCanDestroy) {
                stopBullet(bullet)
                removeView(element)
                elements.remove(element)
                removeTank(element)
            } else {
                stopBullet(bullet)
            }
        }
    }


    private fun removeTank(element: Element) {
        val tanksElements = enemyDrawer.tanks.map {it.element}
        val tankIndex = tanksElements.indexOf(element)
        enemyDrawer.removeTank(tankIndex)
    }

    private fun stopBullet(bullet: Bullet) {
        bullet.canMoveFurther = false
    }

    private fun removeView(element: Element) {
        val activity = container.context as Activity
        activity.runOnUiThread {
            container.removeView(activity.findViewById(element.viewId))
        }
    }


    private fun getCoordinatesForTopOrBottomDirection(bullet: Bullet): List<Coordinate> {
        val bulletCoordinate = bullet.view.getViewCoordinate()
        val leftCell = bulletCoordinate.left - bulletCoordinate.left % CELL_SIZE
        val rightCell = leftCell + CELL_SIZE
        val topCoordinate = bulletCoordinate.top - bulletCoordinate.top % CELL_SIZE
        return listOf(
            Coordinate(topCoordinate,leftCell),
            Coordinate(topCoordinate,rightCell)
        )
    }

    private fun getCoordinatesForLeftOrRightDirection(bullet: Bullet): List<Coordinate> {
        val bulletCoordinate = bullet.view.getViewCoordinate()
        val topCell = bulletCoordinate.top - bulletCoordinate.top % CELL_SIZE
        val bottomCell = topCell + CELL_SIZE
        val leftCoordinate = bulletCoordinate.left - bulletCoordinate.left % CELL_SIZE
        return listOf(
            Coordinate(topCell, leftCoordinate),
            Coordinate(bottomCell,leftCoordinate)
        )
    }



    private fun createBullet(myTank: View, currentDirection: Direction) : ImageView {
        return ImageView(container.context)
            .apply {
                this.setImageResource(R.drawable.bullet)
                this.layoutParams = FrameLayout.LayoutParams(BULLET_WIDTH, BULLET_HEIGHT)
                val bulletCoordinate = getBulletCoordinates(this, myTank, currentDirection)
                (this.layoutParams as FrameLayout.LayoutParams).topMargin = bulletCoordinate.top
                (this.layoutParams as FrameLayout.LayoutParams).leftMargin = bulletCoordinate.left
                this.rotation = currentDirection.rotation
            }
    }

    private fun getBulletCoordinates(
        bullet: ImageView,
        myTank: View,
        currentDirection: Direction
    ): Coordinate {
        val tankLeftTopCoordinate = Coordinate(myTank.top, myTank.left)
        return when (currentDirection) {
            Direction.UP -> Coordinate(
                top = tankLeftTopCoordinate.top - bullet.layoutParams.height,
                left = getDistanceToMiddleOfTank(tankLeftTopCoordinate.left, bullet.layoutParams.width)
            )
            Direction.DOWN -> Coordinate(
                    top = tankLeftTopCoordinate.top + myTank.layoutParams.height,
                    left = getDistanceToMiddleOfTank(tankLeftTopCoordinate.left, bullet.layoutParams.width)
                )
            Direction.LEFT -> Coordinate(
                    top = getDistanceToMiddleOfTank(tankLeftTopCoordinate.top, bullet.layoutParams.height),
                    left = tankLeftTopCoordinate.left - bullet.layoutParams.width
            )

            Direction.RIGHT -> Coordinate(
                    top = getDistanceToMiddleOfTank(tankLeftTopCoordinate.top, bullet.layoutParams.height),
                    left = tankLeftTopCoordinate.left + myTank.layoutParams.width
            )
        }
        return tankLeftTopCoordinate
    }

    private fun getDistanceToMiddleOfTank(startCoordinate: Int, bulletSize: Int): Int {
        return startCoordinate + (CELL_SIZE - bulletSize / 2)
    }


}