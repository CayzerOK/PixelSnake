package com.cayzerok.core

import com.cayzerok.render.player
import com.cayzerok.world.TileList
import com.cayzerok.world.World
import org.joml.Vector3f

import java.awt.Font

val font = Font("Times New Roman",Font.BOLD,24)
fun mainLoop() {
    input.updateScroll()
    val camTarget = Vector3f(-(cursorPos.x-player.position.x)/2, -(cursorPos.y-player.position.y)/2,0f)
//    val camTarget = correct(cursorPos, player.position)
    PID.smoothCam(camTarget)
    World.correctCamera()
}



private fun correct(cursor:Vector3f, player:Vector3f): Vector3f {
    val const =1f/(1+1/2)
    val oa = player.mul(1/2f,Vector3f())
    val ob = Vector3f(-cursor.x/2,-cursor.y/2,0f)
    val target = ob.add(oa,Vector3f())
    return target.mul(const, Vector3f())
}