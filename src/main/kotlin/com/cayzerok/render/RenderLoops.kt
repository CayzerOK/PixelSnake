package com.cayzerok.render

import com.cayzerok.core.cursorPos
import com.cayzerok.core.invLayer
import com.cayzerok.core.mainCamera
import com.cayzerok.core.showWays
import com.cayzerok.entity.Bullet
import com.cayzerok.entity.Player
import com.cayzerok.guns.Gun
import com.cayzerok.world.TileList
import com.cayzerok.world.TileRenderer
import com.cayzerok.world.World
import com.cayzerok.world.layerList

val render = TileRenderer()

val enemyList = Array<Player>(7) {Player("enemy")}
val player = Player("player")

fun firstRenderLoop() {
    for (y in 0 until World.height) {
        for (x in 0 until World.width) {
            render.renderTile(TileList.waterTile.id, x.toFloat(), -y.toFloat())
        }
    }
    layerList.forEach { it.renderIt() }
    if(showWays) {
        for (y in 0 until World.height) {
            for (x in 0 until World.width) {
                if (World.wayMap[x+y*World.width]!!)
                    render.renderTile(TileList.waypoint.id, x.toFloat(), -y.toFloat())
            }
        }
    }

}

fun secondRenderLoop() {
    enemyList.forEach { it.renderIt() }

    player.BulletArray.forEach {
        if (it.avalible == false)
            it.avalible = Bullet.renderIt(it)
    }
    player.renderIt()
    Gun.renderIt()

    for (ind in 0..invLayer) {
        render.renderTile(TileList.waypoint.id,
                -mainCamera.camPosition.x/(World.scale*2)+200/World.scale/2+ind/World.scale,
                -mainCamera.camPosition.y/(World.scale*2)-200/World.scale/2+ind/World.scale,
                0.3f)
    }

    render.renderTile(TileList.aim.id,
            cursorPos.x/(World.scale*2),
            cursorPos.y/(World.scale*2), 0.3f)
}

fun thirdRenderLoop() {

    //Light.renderLight(Texture("light"), 10f,-10f,2f)


    render.renderTile(player.invTile,
            cursorPos.x/(World.scale*2)+0.2f,
            cursorPos.y/(World.scale*2)+0.2f, 0.3f, player.invTileAngle)
}