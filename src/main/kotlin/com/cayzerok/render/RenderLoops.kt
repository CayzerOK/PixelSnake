package com.cayzerok.render

import com.cayzerok.core.*
import com.cayzerok.entity.Player
import com.cayzerok.ui.Statistics
import com.cayzerok.world.*
import org.lwjgl.opengl.GL11

val render = TileRenderer()
val player = Player()
fun firstRenderLoop() {
    for (y in 0 until World.height)
        for (x in 0 until World.width)
            render.renderTile(TileList.grassTile.id,x.toFloat(),-y.toFloat())
}

fun secondRenderLoop() {
    World.renderIt()

    player.renderIt()



    render.renderTile(TileList.aim.id,
            cursorPos.x/100,
            cursorPos.y/100, 0.3f)

//    render.renderTile(TileList.stoneTile.id,
//            -player.position.x/2/World.scale-aimVec.x*2+1,
//            player.position.y/2/World.scale-aimVec.y,
//            0.4f)
}

fun thirdRenderLoop() {
    GL11.glBegin(GL11.GL_LINES)
    GL11.glVertex2f(0f,0f)
    GL11.glVertex2f(aimVec.x, aimVec.y)

    GL11.glEnd()
}