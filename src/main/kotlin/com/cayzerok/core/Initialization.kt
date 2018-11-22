package com.cayzerok.core

import com.cayzerok.render.Camera
import com.cayzerok.render.player
import com.cayzerok.world.*
import kotlinx.coroutines.channels.Channel
import org.joml.Vector3f
import java.io.FileNotFoundException

val mainCamera = Camera(mainWindow.width,mainWindow.height)
var input = Input(0)
var cursorPos = Vector3f()
var showWays = false

val channel = Channel<Boolean>()

fun initialize() {
    World.loadWays()
    try {
        player.load()
    }catch (e: FileNotFoundException) {}
    shader.init()
    Land1.loadWorld()
    Land2.loadWorld()
}