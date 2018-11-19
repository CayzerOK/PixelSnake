package com.cayzerok.render

import com.cayzerok.core.*
import com.cayzerok.entity.Player
import com.cayzerok.ui.Statistics
import com.cayzerok.world.*
import org.lwjgl.opengl.GL11

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

}

fun secondRenderLoop() {
    player.renderIt()
    render.renderTile(TileList.aim.id,
            cursorPos.x/100,
            cursorPos.y/100, 0.3f)
}

fun thirdRenderLoop() {
    render.renderTile(player.invTile,
            cursorPos.x/100+0.2f,
            cursorPos.y/100+0.2f, 0.3f, player.invTileAngle)
}