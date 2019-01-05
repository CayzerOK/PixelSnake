package com.cayzerok.entity

import com.cayzerok.AABB.AABB
import com.cayzerok.AABB.getBBoxes
import com.cayzerok.core.mainCamera
import com.cayzerok.core.shader
import com.cayzerok.core.stableFloat
import com.cayzerok.render.EntityModel
import com.cayzerok.render.Texture
import com.cayzerok.render.playerList
import com.cayzerok.world.World
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import kotlin.math.cos
import kotlin.math.sin


data class BulletNote(
        var coords:Vector3f,
        var angle:Float,
        var avalible:Boolean
)

object Bullet {
    private val tex = Texture("bullet")
    private val vertices = floatArrayOf(
            -1f, 1f, 0f, // TOP LEFT 0
            1f, 1f, 0f, // TOP RIGHT 1
            1f, -1f, 0f, // BOTTOM RIGHT 2
            -1f, -1f, 0f)// BOTTOM LEFT 3

    private val texture = floatArrayOf(
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f)

    private val indices = intArrayOf(
            0, 1, 2,
            2, 3, 0)
    private val model = EntityModel(vertices,texture,indices)
    var bAABB = AABB(Vector2f(),Vector2f())

    fun renderIt(bullet:BulletNote): Boolean {
        val y = 400 * sin(bullet.angle)
        val x = 400 * cos(bullet.angle)
        val boxes = getBBoxes(bullet.coords)
        bAABB = AABB(Vector2f(bullet.coords.x,bullet.coords.y),Vector2f(0.06f*World.scale,0.06f*World.scale))

        bullet.coords.add(stableFloat(x), stableFloat(y), stableFloat(0f))
        try {
            boxes.forEach {
                if (it != null)
                    if (bAABB.isIntersecting(it)) {
                        return true
                    }
            }
            playerList.forEach {
                if (bAABB.isIntersecting(it.playerAABB)) { return true }
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