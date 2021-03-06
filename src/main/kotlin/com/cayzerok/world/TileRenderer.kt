package com.cayzerok.world

import com.cayzerok.core.*
import com.cayzerok.render.*
import org.joml.Matrix4f
import org.joml.Vector3f
import java.util.HashMap


class TileRenderer {
    private val tileTextures: HashMap<String, Texture>
    private val tileModel: EntityModel

    init {
        tileTextures = HashMap()
        val vertices = floatArrayOf(
                -1f, 1f, 0f,    // TOP LEFT 0
                1f, 1f, 0f,    // TOP RIGHT 1
                1f, -1f, 0f,   // BOTTOM RIGHT 2
                -1f, -1f, 0f)  // BOTTOM LEFT 3

        val texture = floatArrayOf(
                0f, 0f,
                1f, 0f,
                1f, 1f,
                0f, 1f)

        val indices = intArrayOf(
                0, 1, 2,
                2, 3, 0)

        tileModel = EntityModel(vertices, texture, indices)

        for (i in 0 until tiles.size) {
            if (tiles[i] != null) {
                if (!tileTextures.containsKey(tiles[i]!!.texture)) {
                    val tex = tiles[i]!!.texture
                    tileTextures[tex] = Texture(tex)
                }
            }
        }
    }

    fun renderTile(tile: Int, x: Float, y: Float, size:Float = 1f, angle:Double = 0.0) {
        if (x in (-mainCamera.camPosition.x / (World.scale*2)) - 5..(-mainCamera.camPosition.x / (World.scale*2)) + 5)
            if (y in (-mainCamera.camPosition.y / (World.scale*2)) - 3..(-mainCamera.camPosition.y / (World.scale*2)) + 3) {
                if (tileTextures.containsKey(tiles[tile]!!.texture)) tileTextures[tiles[tile]!!.texture]!!.bind(0)

                val tilePos = Matrix4f().translate(Vector3f((x * 2), (y * 2), 0f))
                val target = Matrix4f()

                mainCamera.getProjection().mul(World.projection, target)
                target.mul(tilePos).scale(size).rotate(Math.toRadians(angle).toFloat(),0f,0f,1f)

                shader.setUniform("sampler", 0f)
                shader.setUniform("projection", target)

                tileModel.renderIt()
            }
    }

    fun renderTile(barTex:Texture, x: Float, y: Float, size:Float = 1f, angle:Double = 0.0) {
        if (x in (-mainCamera.camPosition.x / (World.scale*2)) - 5..(-mainCamera.camPosition.x / (World.scale*2)) + 5)
            if (y in (-mainCamera.camPosition.y / (World.scale*2)) - 3..(-mainCamera.camPosition.y / (World.scale*2)) + 3) {

                val tilePos = Matrix4f().translate(Vector3f((x * 2), (y * 2), 0f))
                val target = Matrix4f()

                mainCamera.getProjection().mul(World.projection, target)
                target.mul(tilePos).scale(size).rotate(Math.toRadians(angle).toFloat(),0f,0f,1f)

                shader.setUniform("sampler", 0f)
                shader.setUniform("projection", target)
                barTex.bind(0)
                tileModel.renderIt()
            }
    }
}