package com.cayzerok.lightning

import com.cayzerok.core.mainCamera
import com.cayzerok.core.shader
import com.cayzerok.render.EntityModel
import com.cayzerok.render.Texture
import com.cayzerok.world.World
import org.joml.Matrix4f
import org.joml.Vector3f

object Light{
    val vertices = floatArrayOf(
            0f,0f,0f,
            -1f,1f,0f,
            0f,1f,0f,
            1f,1f,0f)

    val texture = floatArrayOf(
            -0.5f, -0.5f,
            0f, 0f,
            0f,-0.5f,
            -0.5f,0f)

    val indices = intArrayOf(
            0,1,2,
            0,2,3)

    private val tileModel = EntityModel(vertices, texture, indices)

    fun renderLight(lightTex: Texture, x: Float, y: Float, size:Float = 1f, angle:Double = 0.0) {
        if (x in (-mainCamera.camPosition.x / (World.scale*2)) - 5..(-mainCamera.camPosition.x / (World.scale*2)) + 5)
            if (y in (-mainCamera.camPosition.y / (World.scale*2)) - 3..(-mainCamera.camPosition.y / (World.scale*2)) + 3) {

                val tilePos = Matrix4f().translate(Vector3f((x * 2), (y * 2), 0f))
                val target = Matrix4f()

                mainCamera.getProjection().mul(World.projection, target)
                target.mul(tilePos).scale(size).rotate(Math.toRadians(angle).toFloat(),0f,0f,1f)

                shader.setUniform("sampler", 0f)
                shader.setUniform("projection", target)
                lightTex.bind(0)
                tileModel.renderIt()
            }
    }
}