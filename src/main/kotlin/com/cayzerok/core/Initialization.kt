package com.cayzerok.core

import com.cayzerok.render.Camera
import com.cayzerok.world.*
import org.joml.Vector3f

val mainCamera = Camera(mainWindow.width,mainWindow.height)
var input = Input(0)
var aimVec = Vector3f()
var cursorPos = Vector3f()

fun initialize() {
    shader.init()
    for (i in 4..7)
        for (j in 3..6)
            World.setTile(TileList.stoneTile,i,j)
}