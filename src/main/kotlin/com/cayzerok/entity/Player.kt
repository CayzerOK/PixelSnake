package com.cayzerok.entity

import com.cayzerok.core.*
import com.cayzerok.render.*
import com.cayzerok.world.worldProj
import org.joml.Vector3f
import com.cayzerok.world.World



class Player() {
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

    fun update(delta:Float, world:World) {

    }

    fun renderIt() {
        val target = mainCamera.getProjection()
        target.mul(worldProj)

        shader.bind()
        shader.setUniform("sampler", 0f)
        shader.setUniform("projection", worldProj)
        playerTex.bind(0)
        playerModel.renderIt()
    }

}