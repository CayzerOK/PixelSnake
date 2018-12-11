package com.cayzerok.guns

import com.cayzerok.core.*
import com.cayzerok.render.*
import com.cayzerok.ui.Timer
import com.cayzerok.world.World
import org.joml.Matrix4f
import org.joml.Vector3f
import kotlin.math.cos
import kotlin.math.sin

val gunTex = Texture("gun")
val minigunTex = Texture("minigun")

object Gun {
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

    var y =0f
    var x = 0f

    private val transform = Transform()

    fun renderIt() {
         y = 0.92f * sin(player.angle)
         x = 0.92f * cos(player.angle)

        val playerPos = Matrix4f().translate(Vector3f(-player.position.x/World.scale-y, -player.position.y/World.scale+x, 0f))
        val target = Matrix4f()

        mainCamera.getProjection().mul(World.projection, target)
        target.mul(playerPos).scale(1f)

        shader.setUniform("sampler", 0f)
        shader.setUniform("projection",transform.getProjection(target, player.angle))
        gunTex.bind(0)
        gunModel.renderIt()
    }

}

val reloadTimer = Timer(250)
val gunTimer = Timer(100)
private var lastpoints = -1
var cells = 30
var reload = false
var mustShoot = false

fun shoot() {
    if (mustShoot) {
        gunTimer.calculate()
        if (lastpoints != gunTimer.points && !reload) {
            val bullet = player.BulletArray.filter { it.avalible }.first()
            bullet.avalible = false
            bullet.angle = (player.angle - Math.toRadians(90.0 + random.nextDouble(-1.0, 1.0))).toFloat()
            bullet.coords = Vector3f(player.position.x+Gun.y*World.scale*2f, player.position.y-Gun.x*World.scale*2f, 0f)
            lastpoints = gunTimer.points
            cells--
            if (cells<1) {
                reload = true
            }

        }
    } else {
        lastpoints = -1
        gunTimer.`break`()
    }
}

var reloadBar = Bar(9,"reloadbar",9)

fun reloadGun() {
    if (reload) {
        reloadTimer.calculate()
        if (reloadTimer.points>=9) {
            cells = 30
            reload = false
            reloadTimer.`break`()
        } else render.renderTile(reloadBar.getTex(reloadTimer.points)!!,
                cursorPos.x/(World.scale*2),
                cursorPos.y/(World.scale*2), 0.3f)
    }
}