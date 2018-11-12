package com.cayzerok.render

import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.vulkan.AMDNegativeViewportHeight
import java.text.FieldPosition

class Camera {
    private var camPosition: Vector3f? = null
    private var camProjection:Matrix4f? = null

    fun use(width:Int,height:Int) {
        camPosition = Vector3f(0f,0f,0f)
        camProjection = Matrix4f().setOrtho2D(-width/2f, width/2f, -height/2f, height/2f)
    }

    fun setPosition (x:Float,y:Float,z:Float) {
        camPosition = Vector3f(x,y,z)
    }

    fun addPosition (x:Float,y:Float,z:Float) {
        camPosition!!.add(Vector3f(x,y,z))
    }

    fun getPosition(): Vector3f? {return camPosition}
    fun getProjection(): Matrix4f {
        var target = Matrix4f()
        val pos = Matrix4f().setTranslation(camPosition)
        target = camProjection!!.mul(pos,target)
        return target
    }
}