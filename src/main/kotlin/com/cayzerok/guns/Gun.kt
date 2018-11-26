package com.cayzerok.guns

import com.cayzerok.core.mainCamera
import com.cayzerok.core.path
import com.cayzerok.core.shader
import com.cayzerok.core.stabileFloat
import com.cayzerok.render.EntityModel
import com.cayzerok.render.Texture
import com.cayzerok.render.Transform
import com.cayzerok.render.player
import com.cayzerok.world.World
import org.joml.Matrix4f
import org.joml.Vector3f
import kotlin.math.cos
import kotlin.math.sin

class Gun(tex: String) {
    val gunTex = Texture(tex)
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

    val gunModel = EntityModel(vertices,texture,indices)

    private val transform = Transform()

    fun renderIt() {
        val y = 1 * sin(player.angle)
        val x = 1 * cos(player.angle)

        val playerPos = Matrix4f().translate(Vector3f(-player.position.x-x/ World.scale, -player.position.y-y/ World.scale, 0f))
        val target = Matrix4f()

        mainCamera.getProjection().mul(World.projection, target)
        target.mul(playerPos).scale(1f)

        shader.setUniform("sampler", 0f)
        shader.setUniform("projection",transform.getProjection(target))
        gunTex.bind(0)
        gunModel.renderIt()
    }

}