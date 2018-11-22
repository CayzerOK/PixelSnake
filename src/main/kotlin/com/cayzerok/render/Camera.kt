package com.cayzerok.render

import com.cayzerok.core.*
import com.cayzerok.entity.Player
import org.joml.Matrix4f
import org.joml.Vector3f
class Camera(width:Int,height:Int) {
    var camPosition = Vector3f(0f,0f,0f)
    private var camProjection = Matrix4f().setOrtho2D(-width/2f, width/2f, -height/2f, height/2f)

    fun move (x:Float,y:Float,z:Float) {
        camPosition.add(Vector3f(stabileFloat(x), stabileFloat(y), stabileFloat(z)))
    }
    fun getProjection(): Matrix4f {
        return camProjection.translate(camPosition,Matrix4f())
    }
}