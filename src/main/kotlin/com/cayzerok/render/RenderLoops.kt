package com.cayzerok.render

import com.cayzerok.core.*
import com.cayzerok.entity.Player
import com.cayzerok.world.*

val render = TileRenderer()
val player = Player()
fun firstRenderLoop() {
    for (y in 0 until World.height)
        for (x in 0 until World.width)
            render.renderTile(TileList.waterTile.id,x.toFloat(),-y.toFloat(), shader, worldProj, mainCamera)
}

fun secondRenderLoop() {
    World.renderIt()
}

fun thirdRenderLoop() {

    player.renderIt()
//    render.renderTile(TileList.testTile.id,
//            -mainCamera.camTarget.x/100,
//            -mainCamera.camTarget.y/100,
//            shader, World.worldProj, mainCamera)
}