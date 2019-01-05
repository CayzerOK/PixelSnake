package com.cayzerok.entity

import com.cayzerok.AABB.AABB
import com.cayzerok.AABB.getBBoxes
import com.cayzerok.core.mainCamera
import com.cayzerok.core.shader
import com.cayzerok.core.stableFloat
import com.cayzerok.guns.Gun
import com.cayzerok.render.*
import com.cayzerok.world.World
import com.cayzerok.world.World.h
import com.cayzerok.world.World.w
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import java.util.*

var player:Player = Player()
val playerTex = Texture("player")
val enemyTex = Texture("enemy")
class Player {
    var isEnemy = true
    var uuid: UUID? = null
    var mustShoot = false
    var moveKeys = BooleanArray(4) {false}
    var HP = 100
    val hpBar = Bar(20,"hpbar")
    var BulletArray = Array(30) {BulletNote(Vector3f(),0f, true)}
    var invTile = 1
    var invTileAngle = 0.0
    var angle = 0f
    var targetAngle = 0f

    val gun = Gun()

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
    var position = Vector3f(-300f, -300f, 0f)
    var playerAABB = AABB(Vector2f(position.x,position.y), Vector2f(World.scale*2/3,World.scale*2/3))
    private val transform = Transform()

    fun setPos(pos:Vector3f) {
        position = pos
        playerAABB.center = Vector2f(position.x,position.y)
    }

    fun move(x: Float, y: Float, z: Float) {
        if (HP > 0) {

            position.add(stableFloat(x), stableFloat(y), stableFloat(z))

            if (position.x > World.scale + 0.5f)
                position.x = World.scale + 0.5f
            if (position.x < w + World.scale - 0.5f)
                position.x = w + World.scale - 0.5f
            if (position.y < -World.scale + 0.5f)
                position.y = -World.scale + 0.5f
            if (position.y > h - World.scale - 0.5f)
                position.y = h - World.scale - 0.5f

            val boxes = getBBoxes(position)

            playerAABB.center = Vector2f(position.x, position.y)
            try {
                boxes.forEach {
                    if (it != null)
                        if (playerAABB.isIntersecting(it)) {
                            position.add(-stableFloat(x), -stableFloat(y), -stableFloat(z))
                            playerAABB.center = Vector2f(position.x, position.y)
                        }
                }
                playerList.forEach {
                    if (this != it) {
                        if (playerAABB.isIntersecting(it.playerAABB)) {
                            position.add(-stableFloat(x), -stableFloat(y), -stableFloat(z))
                            playerAABB.center = Vector2f(position.x, position.y)
                        }
                    }
                }

            } catch (e: Throwable) {
                println("AABB ERROR")
            }
        }
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
            if (isEnemy) enemyTex.bind(0)
            else playerTex.bind(0)
            playerModel.renderIt()
            if (HP<100) {
                shader.setUniform("projection", transform.getProjection(target, -angle))
                hpBar.getTex(HP)?.bind(0)
                barModel.renderIt()
            }
        }
    }
}