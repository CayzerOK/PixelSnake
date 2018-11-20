package com.cayzerok.entity

import com.cayzerok.core.*
import com.cayzerok.render.*
import com.cayzerok.world.*
import org.joml.Vector3f
import org.joml.Matrix4f
import org.joml.Vector2f
import java.io.File
import com.cayzerok.collision.AABB
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
    var position = Vector3f(0f,0f,0f)
    private val transform = Transform()
    val bBox = AABB(Vector2f(position.x, position.y), Vector2f(1f,1f))

    fun collideWithTiles() {

        val boxes = arrayOfNulls<Vector2f>(25)
        for (i in 0..4) {
            for (j in 0..4) {
                boxes[i + j * 5] = World.getTileBB((position.x/200+0.5f-5/2)+i, (-position.y/200+0.5f-5/2)+j)
            }
        }
    }




    fun move(x:Float,y:Float,z:Float) {
        position.add(stabileFloat(x), stabileFloat(y), stabileFloat(z))
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