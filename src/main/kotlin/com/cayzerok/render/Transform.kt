package com.cayzerok.render

import com.cayzerok.core.cursorPos
import org.joml.Matrix4f
var playerAngle = 0f

class Transform {
    fun getProjection(target: Matrix4f): Matrix4f {
        val angle = Math.atan2((-cursorPos.y-player.position.y).toDouble(), (-cursorPos.x-player.position.x).toDouble())+Math.toRadians(90.0)
        target.rotate(angle.toFloat(),0f,0f,1f)
        return target
    }
}