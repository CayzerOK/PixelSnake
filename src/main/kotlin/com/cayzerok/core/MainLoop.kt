package com.cayzerok.core

import com.cayzerok.entity.player
import com.cayzerok.guns.shoot
import com.cayzerok.render.playerList
import com.cayzerok.world.World
import org.joml.Vector3f

fun mainLoop() {
    playerList.forEach { player ->
        if (player.moveKeys[0]) player.move(0f,-30f,0f)
        if (player.moveKeys[1]) player.move(30f,0f,0f)

        if (player.moveKeys[2]) player.move(0f,30f,0f)
        if (player.moveKeys[3]) player.move(-30f,0f,0f)

        //player.angle += stableFloat(PID.smoothAngle(player.targetAngle,player.angle))
    }

    input.updateScroll()
    player.angle = (Math.atan2((-cursorPos.y-player.position.y).toDouble(),
            (-cursorPos.x-player.position.x).toDouble())+Math.toRadians(90.0)).toFloat()
    val camTarget = Vector3f(
            -(cursorPos.x-player.position.x)/2,
            -(cursorPos.y-player.position.y)/2,0f)
    PID.smoothCam(camTarget)
    World.correctCamera()
    shoot()
}


fun getLength(tar1:Vector3f, tar2:Vector3f): Float {
    val vec = tar1.sub(tar2,Vector3f())
    return vec.length()/World.scale*2
}