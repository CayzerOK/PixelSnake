package com.cayzerok.entity

import com.cayzerok.AABB.AABB
import com.cayzerok.AABB.getBBoxes
import com.cayzerok.core.mainCamera
import com.cayzerok.experemental.shader
import com.cayzerok.core.stableFloat
import com.cayzerok.render.*
import com.cayzerok.world.World
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import java.util.*

class Player(val name:String) {
    var uuid: UUID? = null

    var moveKeys = BooleanArray(4) {false}

    var HP = 100
    val hpBar = Bar(20,"hpbar")

    var BulletArray = Array(30) {BulletNote(Vector3f(),0f, true)}
    var invTile = 1
    var invTileAngle = 0.0
    val playerTex = Texture(name)
    var angle = 0f

    val vertices = floatArrayOf(
            -1f, 1f, 0f,  // TOP LEFT 0
            1f, 1f, 0f,   // TOP RIGHT 1
            1f, -1f, 0f,  // BOTTOM RIGHT 2
            -1f, -1f, 0f) // BOTTOM LEFT 3

    val texture = floatArrayOf(
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f)

    val indices = intArrayOf(
            0, 1, 2,
            2, 3, 0)
    val barModel = EntityModel(vertices, texture, indices)
    val playerModel = EntityModel(vertices, texture, indices)
    var position = Vector3f(-200f, 100f, 0f)
    var playerAABB = AABB(Vector2f(position.x,position.y), Vector2f(World.scale*2/3,World.scale*2/3))
    private val transform = Transform()

    fun setPos(pos:Vector3f) {
        position = pos
        playerAABB.center = Vector2f(position.x,position.y)
    }

    fun move(x: Float, y: Float, z: Float) {

        position.add(stableFloat(x), stableFloat(y), stableFloat(z))
        val boxes = getBBoxes(position)
        playerAABB.center = Vector2f(position.x,position.y)
        try {
            boxes.forEach {
                if (it != null)
                    if (playerAABB.isIntersecting(it)) {
                        position.add(-stableFloat(x), -stableFloat(y), -stableFloat(z))
                        playerAABB.center = Vector2f(position.x, position.y)
                    }
            }
            enemyList.forEach {
                if (player.playerAABB.isIntersecting(it.playerAABB)) {
                    position.add(-stableFloat(x), -stableFloat(y), -stableFloat(z))
                    playerAABB.center = Vector2f(position.x, position.y)
                }
            }

        } catch (e: Throwable) { println("AABB ERROR") }
    }

    fun renderIt() {
        if (HP > 0) {
            val playerPos = Matrix4f().translate(Vector3f(
                    -position.x / World.scale,
                    -position.y / World.scale,
                    0f))
            val target = Matrix4f()

            mainCamera.getProjection().mul(World.projection, target)
            target.mul(playerPos).scale(1f)

            shader.setUniform("sampler", 0f)
            shader.setUniform("projection", transform.getProjection(target, angle))
            playerTex.bind(0)
            playerModel.renderIt()
            if (HP<100) {
                hpBar.getTex(HP)?.bind(0)
                barModel.renderIt()
            }
        }
    }
}