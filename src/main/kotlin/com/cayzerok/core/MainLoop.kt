package com.cayzerok.core

import com.cayzerok.render.player
import com.cayzerok.world.TileList
import com.cayzerok.world.World
import org.joml.Vector3f

import java.awt.Font

fun mainLoop() {
    input.updateScroll()
    val camTarget = Vector3f(-(cursorPos.x-player.position.x)/2, -(cursorPos.y-player.position.y)/2,0f)
    PID.smoothCam(camTarget)
    World.correctCamera()
}