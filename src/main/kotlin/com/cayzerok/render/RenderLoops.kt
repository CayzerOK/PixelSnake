package com.cayzerok.render

import com.cayzerok.core.*
import com.cayzerok.entity.Player
import com.cayzerok.world.*

val render = TileRenderer()
val player = Player()

fun firstRenderLoop() {
    for (y in 0 until World.height) {
        for (x in 0 until World.width) {
            render.renderTile(TileList.waterTile.id, x.toFloat(), -y.toFloat())
        }
    }
    Land1.renderIt()
    Land2.renderIt()
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
    //render.renderTile(TileList.aim.id,-player.position.x/World.scale/2, -player.position.y/World.scale/2)
    player.renderIt()
    render.renderTile(TileList.aim.id,
            cursorPos.x/(World.scale*2),
            cursorPos.y/(World.scale*2), 0.3f)
}

fun thirdRenderLoop() {
    render.renderTile(player.invTile,
            cursorPos.x/(World.scale*2)+0.2f,
            cursorPos.y/(World.scale*2)+0.2f, 0.3f, player.invTileAngle)
    //render.renderTile(TileList.aim.id, -player.position.x, -player.position.y,0f)




}