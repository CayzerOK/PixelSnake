package com.cayzerok.entity

import com.cayzerok.core.*
import com.cayzerok.render.*
import com.cayzerok.world.*
import org.joml.Vector3f
import org.joml.Matrix4f
import java.io.File
import com.cayzerok.world.World


class Player {
    var invTile = 1
    var invTileAngle = 0.0
    val playerTex = Texture("player")
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
    val playerModel = EntityModel(vertices,texture,indices)
    var position = Vector3f(-100f,100f,0f)
    private val transform = Transform()


    fun move(x:Float,y:Float,z:Float) {
        position.add(stabileFloat(x), stabileFloat(y), stabileFloat(z))
        try {
            if (World.getWay((-position.x/World.scale/2+0.5f).toInt(),(position.y/World.scale/2+0.5f).toInt())!!)
                position.add(-stabileFloat(x), -stabileFloat(y), -stabileFloat(z))
        }catch (e:Throwable) {}

    }

    fun save() {
        val posString = gson.toJson(position)
        File(assets+"levels/player.lvl").writeText(posString)
    }
    fun load() {
        position.set(gson.fromJson(File(assets+"levels/player.lvl").readText(),Vector3f::class.java))
    }

    fun renderIt() {

        val playerPos = Matrix4f().translate(Vector3f(-position.x/World.scale, -position.y/World.scale, 0f))
        val target = Matrix4f()

        mainCamera.getProjection().mul(World.projection, target)
        target.mul(playerPos).scale(1f)

        shader.setUniform("sampler", 0f)
        shader.setUniform("projection",transform.getProjection(target))

        playerTex.bind(0)
        playerModel.renderIt()
    }

}