package com.cayzerok.entity

import com.cayzerok.core.*
import com.cayzerok.render.*
import com.cayzerok.world.World
import org.joml.Matrix4f
import org.joml.Vector3f
import kotlin.math.cos
import kotlin.math.sin


data class BulletNote(
        var coords:Vector3f,
        var angle:Float,
        var avalible:Boolean
)

object Bullet {
    val tex = Texture("bullet")
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

    fun renderIt(bullet:BulletNote): Boolean {
        val y = 400 * sin(bullet.angle)
        val x = 400 * cos(bullet.angle)

        bullet.coords.add(stabileFloat(x), stabileFloat(y), stabileFloat(0f))
        try {
            when {
                World.getWay((-bullet.coords.x / World.scale / 2 + 0.5f).toInt(),
                        (bullet.coords.y / World.scale / 2 + 0.5f).toInt())!! -> {return true}
                bullet.coords.x < mainCamera.camPosition.x-mainWindow.width/2 -> {return true}
                bullet.coords.y < mainCamera.camPosition.y-mainWindow.height/2 -> {return true}
                bullet.coords.x > mainCamera.camPosition.x+mainWindow.width/2 -> {return true}
                bullet.coords.y > mainCamera.camPosition.y+mainWindow.height/2 -> {return true}
                getLength(bullet.coords, enemy.position) < 1.5f -> {
                    enemy.HP-=10
                    if (enemy.HP<0) enemy.position = Vector3f(-100f,-100f,0f)
                    return true
                }
            }
        } catch (e:ArrayIndexOutOfBoundsException) {}

        val bulletPos = Matrix4f().translate(Vector3f(-bullet.coords.x / World.scale, -bullet.coords.y / World.scale, 0f))
        val target = Matrix4f()
        mainCamera.getProjection().mul(World.projection, target)
        target.mul(bulletPos).scale(0.7f).rotate(bullet.angle+Math.toRadians(180.0).toFloat(), 0f, 0f, 1f)
        shader.setUniform("sampler", 0f)
        shader.setUniform("projection", target)
        tex.bind(0)
        model.renderIt()
        return false
    }
}