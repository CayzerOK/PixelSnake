package com.cayzerok.render

import com.cayzerok.core.cursorPos
import com.cayzerok.core.getLength
import com.cayzerok.core.invLayer
import com.cayzerok.entity.Bullet
import com.cayzerok.entity.Player
import com.cayzerok.entity.player
import com.cayzerok.world.TileList
import com.cayzerok.world.TileRenderer
import com.cayzerok.world.World
import com.cayzerok.world.layerList

val render = TileRenderer()

val playerList = Array(8) {Player()}

fun firstRenderLoop() {
    for (y in 0 until World.height) {
        for (x in 0 until World.width) {
            render.renderTile(TileList.waterTile.id, x.toFloat(), -y.toFloat())
        }
    }

    layerList.forEach { it.renderIt() }
}

fun secondRenderLoop() {
    playerList.forEach { player ->
        player.renderIt()
        player.BulletArray.forEach {
            if (getLength(it.coords, player.position)>World.scale/2)
                it.avalible = true
            if (!it.avalible)
                it.avalible = Bullet.renderIt(it)
        }
        if (player.HP > 0)
            player.gun.renderIt(player.angle,player.position)
    }

    render.renderTile(TileList.aim.id,
            cursorPos.x/(World.scale*2),
            cursorPos.y/(World.scale*2), 0.3f)
}

fun thirdRenderLoop() {

//    Light.renderLight(Texture("light"), 10f,-10f,2f)

    for (i in 0..invLayer) {
        render.renderTile(TileList.waypoint.id,
                cursorPos.x/(World.scale*2)+0.6f+0.02f*i,
                cursorPos.y/(World.scale*2)+0.2f+0.02f*i, 0.3f)
    }

    render.renderTile(player.invTile,
            cursorPos.x/(World.scale*2)+0.2f,
            cursorPos.y/(World.scale*2)+0.2f, 0.3f, player.invTileAngle)
}