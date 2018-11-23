package com.cayzerok.entity

import com.cayzerok.core.*
import com.cayzerok.render.*
import com.cayzerok.world.World
import org.joml.Matrix4f
import org.joml.Vector3f
import kotlin.math.cos
import kotlin.math.sin


data class BulletNote(
        val coords:Vector3f,
        val angle:Float
)

val BulletList = mutableListOf<BulletNote>()

object Bullet{
    val tex = Texture("waypoint")
    val vertices = floatArrayOf(
            -1f, 1f, 0f, // TOP LEFT 0
            1f, 1f, 0f, // TOP RIGHT 1
            1f, -1f, 0f, // BOTTOM RIGHT 2
            -1f, -1f, 0f)// BOTTOM LEFT 3

    val texture = floatArrayOf(
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f)

    val indices = intArrayOf(
            0, 1, 2,
            2, 3, 0)
    val model = EntityModel(vertices,texture,indices)


    fun renderIt(target:Vector3f, angle: Float, index:Int) {
        val y = 100*sin(angle)
        val x = 100*cos(angle)

        target.add(stabileFloat(x), stabileFloat(y), stabileFloat(0f))
        try {
            if (World.getWay((-target.x/ World.scale/2+0.5f).toInt(),(target.y/ World.scale/2+0.5f).toInt())!!)
                target.add(-stabileFloat(x), -stabileFloat(y), -stabileFloat(0f))
        } catch (e:Throwable) {}

        val bulletPos = Matrix4f().translate(Vector3f(-target.x/ World.scale, -target.y/ World.scale, 0f))
        val target = Matrix4f()
        mainCamera.getProjection().mul(World.projection, target)
        target.mul(bulletPos).scale(1f)
        shader.setUniform("sampler", 0f)
        shader.setUniform("projection",angle)
        tex.bind(0)
        model.renderIt()
    }
}