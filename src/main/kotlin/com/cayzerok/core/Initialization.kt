package com.cayzerok.core

import com.cayzerok.render.Camera
import com.cayzerok.world.World
import com.cayzerok.world.layerList
import org.joml.Vector3f

val mainCamera = Camera(MainWindow.width, MainWindow.height)
var input = Input(0)
var cursorPos = Vector3f()

fun initialize() {
    shader.init()
    layerList.forEach { it.loadWorld() }
    println(World.scale)
}