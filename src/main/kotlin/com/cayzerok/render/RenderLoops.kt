package com.cayzerok.render

import com.cayzerok.core.*
import com.cayzerok.entity.Bullet
import com.cayzerok.entity.Player
import com.cayzerok.guns.Gun
import com.cayzerok.guns.reloadBar
import com.cayzerok.guns.reloadTimer
import com.cayzerok.lightning.Light
import com.cayzerok.world.*

val render = TileRenderer()
val player = Player("player")
val enemy = Player("enemy")

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
    enemy.renderIt()

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