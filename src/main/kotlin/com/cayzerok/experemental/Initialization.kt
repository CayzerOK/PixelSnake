package com.cayzerok.experemental

import com.cayzerok.render.Camera
import com.cayzerok.world.World
import com.cayzerok.world.layerList
import org.joml.Vector3f

val mainCamera = Camera(mainWindow.width, mainWindow.height)
var input = Input(0)
var cursorPos = Vector3f()
var showWays = false

fun initialize() {
    World.loadWays()
    com.cayzerok.experemental.shader.init()
    layerList.forEach { it.loadWorld() }
}