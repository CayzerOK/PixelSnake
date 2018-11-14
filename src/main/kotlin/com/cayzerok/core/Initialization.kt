package com.cayzerok.core

import com.cayzerok.render.Camera
import com.cayzerok.world.*
import org.joml.Matrix4f

val mainCamera = Camera(mainWindow.width,mainWindow.height)
var input = Input(0)

fun initialize() {
    shader.init()
    World.setTile(TileList.grassTile,0,1)
}