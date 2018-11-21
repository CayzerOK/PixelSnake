package com.cayzerok.core

import com.cayzerok.render.Camera
import com.cayzerok.render.player
import com.cayzerok.world.*
import org.joml.Vector3f
import java.io.FileNotFoundException

val mainCamera = Camera(mainWindow.width,mainWindow.height)
var input = Input(0)
var cursorPos = Vector3f()
var showWays = false

fun initialize() {
    World.loadWays()
    try {
        player.load()
    }catch (e: FileNotFoundException) {}
    shader.init()
    Land1.loadWorld()
    Land2.loadWorld()
}