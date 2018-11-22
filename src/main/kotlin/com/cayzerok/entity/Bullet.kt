package com.cayzerok.entity

import com.cayzerok.core.channel
import com.cayzerok.core.mainCamera
import com.cayzerok.core.shader
import com.cayzerok.core.stabileFloat
import com.cayzerok.render.EntityModel
import com.cayzerok.render.Texture
import com.cayzerok.world.World
import kotlinx.coroutines.*
import org.joml.Matrix4f
import org.joml.Vector3f

class Bullet(val position: Vector3f, val angle:Matrix4f){
    val tex = Texture("waypoint")
    val vertices = floatArrayOf(
            -0.1f, 0.1f, 0f, // TOP LEFT 0
            0.1f, 0.1f, 0f, // TOP RIGHT 1
            0.1f, -0.1f, 0f, // BOTTOM RIGHT 2
            -0.1f, -0.1f, 0f)// BOTTOM LEFT 3

    val texture = floatArrayOf(
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f)

    val indices = intArrayOf(
            0, 1, 2,
            2, 3, 0)
    val model = EntityModel(vertices,texture,indices)

    fun move(x:Float,y:Float,z:Float) {
        position.add(stabileFloat(x), stabileFloat(y), stabileFloat(z))
        try {
            if (World.getWay((-position.x/ World.scale/2+0.5f).toInt(),(position.y/ World.scale/2+0.5f).toInt())!!)
                position.add(-stabileFloat(x), -stabileFloat(y), -stabileFloat(z))
        }catch (e:Throwable) {}
    }

    fun renderIt() {
        val bulletPos = Matrix4f().translate(Vector3f(-position.x/ World.scale, -position.y/ World.scale, 0f))
        val target = Matrix4f()
        mainCamera.getProjection().mul(World.projection, target)
        target.mul(bulletPos).scale(1f)
        shader.setUniform("sampler", 0f)
        shader.setUniform("projection",angle)
        tex.bind(0)
        model.renderIt()
    }
}


fun main() = runBlocking {
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello,")
}