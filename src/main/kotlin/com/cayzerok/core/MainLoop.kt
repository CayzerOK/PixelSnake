package com.cayzerok.core

import com.cayzerok.guns.shoot
import com.cayzerok.render.enemy
import com.cayzerok.render.player
import com.cayzerok.world.TileList
import com.cayzerok.world.World
import org.joml.Vector3f

import java.awt.Font
fun mainLoop() {
    input.updateScroll()
    player.angle = (Math.atan2((-cursorPos.y- player.position.y).toDouble(),
            (-cursorPos.x-player.position.x).toDouble())+Math.toRadians(90.0)).toFloat()
    val camTarget = Vector3f(-(cursorPos.x-player.position.x)/2,
            -(cursorPos.y-player.position.y)/2,0f)
    PID.smoothCam(camTarget)
    World.correctCamera()
    shoot()
}


fun getLength(tar1:Vector3f, tar2:Vector3f): Float {
    val vec = tar1.sub(tar2,Vector3f())
    return vec.length()/World.scale*2
}