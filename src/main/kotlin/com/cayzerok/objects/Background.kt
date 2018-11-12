package com.cayzerok.objects

import com.cayzerok.core.mainCamera
import com.cayzerok.render.*
import org.joml.Vector3f

object backround {
    val shader = Shader()
    val texture = Texture()
    val model = EntityModel()
    val vertices:FloatArray = floatArrayOf(
            -1f,1f,0f,  //0
            1f,1f,0f,   //1
            1f,-1f,0f,  //2
            -1f,-1f,0f  //3
    )
    val texCoords:FloatArray = floatArrayOf(
            0f,0f,
            1f,0f,
            1f,1f,
            0f,1f
    )
    val indices:IntArray = intArrayOf(
            0,1,2,
            2,3,0
    )
    fun renderIt(){

        texture.bind(0)
        shader.bind()
        shader.setUniform("sampler", 0f)
        shader.setUniform("projection", mainCamera.getProjection().mul(projection))
        model.renderIt()
        texture.unbind()
    }
    fun initTexture() {
        mainCamera.setPosition(-200f,-2f,1f)

        model.setModel(vertices, texCoords, indices)
        texture.decodePNG("background.png")
        shader.init("shader")
    }
}