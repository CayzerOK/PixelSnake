package com.cayzerok.render

import com.cayzerok.core.cursorPos
import org.joml.Matrix4f

class Transform {
    fun getProjection(target: Matrix4f, angle:Float): Matrix4f {
        target.rotate(angle,0f,0f,1f)
        return target
    }
}