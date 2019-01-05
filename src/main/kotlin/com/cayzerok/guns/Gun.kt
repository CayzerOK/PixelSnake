package com.cayzerok.guns

import com.cayzerok.core.cursorPos
import com.cayzerok.core.mainCamera
import com.cayzerok.core.random
import com.cayzerok.core.shader
import com.cayzerok.render.*
import com.cayzerok.ui.Timer
import com.cayzerok.world.World
import org.joml.Matrix4f
import org.joml.Vector3f
import kotlin.math.cos
import kotlin.math.sin

val gunTex = Texture("gun")

class Gun {
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

    var y = 0f
    var x = 0f
    var isShooting = false

    private val transform = Transform()

    fun renderIt(angle:Float,position: Vector3f) {
         y = 0.92f * sin(angle)
         x = 0.92f * cos(angle)

        val playerPos = Matrix4f().translate(Vector3f(-position.x/World.scale-y, -position.y/World.scale+x, 0f))
        val target = Matrix4f()

        mainCamera.getProjection().mul(World.projection, target)
        target.mul(playerPos).scale(1f)

        shader.setUniform("sampler", 0f)
        shader.setUniform("projection",transform.getProjection(target, angle))
        gunTex.bind(0)
        gunModel.renderIt()
    }
    val reloadTimer = Timer(250)
    var cells = 30
    var reload = false




    var reloadBar = Bar(9,"reloadbar",10)

    fun reloadGun() {
        if (reload) {
            reloadTimer.calculate()
            if (reloadTimer.points>=10) {
                cells = 30
                reload = false
                reloadTimer.`break`()
            } else render.renderTile(reloadBar.getTex(reloadTimer.points)!!,
                    cursorPos.x/(World.scale*2),
                    cursorPos.y/(World.scale*2), 0.3f)
        }
    }

}

fun shoot() {
    playerList.forEach { player ->
        if (player.HP > 0 && player.gun.isShooting && !player.gun.reload) {
            player.gun.isShooting = false
            val bullet = player.BulletArray.firstOrNull { it.avalible }
            if (bullet != null) {
                bullet.avalible = false
                bullet.angle = (player.angle - Math.toRadians(90.0 + random.nextDouble(-1.0, 1.0))).toFloat()
                bullet.coords = Vector3f(player.position.x + player.gun.y * World.scale * 2f, player.position.y - player.gun.x * World.scale * 2f, 0f)
                player.gun.cells--
                if (player.gun.cells < 1) {
                    player.gun.reload = true
                }
            }

        }

    }
}