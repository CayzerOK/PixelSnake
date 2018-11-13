package com.cayzerok.render

import com.cayzerok.core.mainCamera
import com.cayzerok.core.shader
import com.cayzerok.world.*

val render = TileRenderer()

fun firstRenderLoop() {
    for (x in -20..20)
        for (y in -20..20)
            render.renderTile(waterTile,x.toFloat(),y.toFloat(), shader, projection, mainCamera)
}

fun secondRenderLoop() {
    for (x in 0..5)
        for (y in 0..1)
            render.renderTile(grassTile,x.toFloat(),y.toFloat(), shader, projection, mainCamera)
    for (x in 0..6)
        for (y in 2..3)
            render.renderTile(stoneTile,x.toFloat(),y.toFloat(), shader, projection, mainCamera)
}

fun thirdRenderLoop() {
    render.renderTile(testTile, -mainCamera.camPosition.x/100, -mainCamera.camPosition.y/100, shader, projection, mainCamera)
}