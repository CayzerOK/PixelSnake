package com.cayzerok.core

import com.cayzerok.render.player
import com.cayzerok.world.TileList
import com.cayzerok.world.World
import org.joml.Vector3f

import java.awt.Font

val font = Font("Times New Roman",Font.BOLD,24)

fun mainLoop() {
    val camTarget = Vector3f(-(cursorPos.x-player.position.x)/2, -(cursorPos.y-player.position.y)/2,0f)
    PID.smoothCam(camTarget)
    World.correctCamera()
    //World.setTile(TileList.stoneTile, ((-player.position.x/World.scale)/2+0.5).toInt(),((player.position.y/World.scale)/2+0.5).toInt())
}