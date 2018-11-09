package com.cayzerok.objects

import com.cayzerok.render.*

object backround {
    val shader = Shader()
    //val texture = Texture()
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

        //texture.bind()
        shader.bind()
        model.renderIt()
        //texture.unbind()
    }
    fun initTexture() {
        model.setModel(vertices, texCoords, indices)
        //texture.decodePNG("background.png")
        shader.init("shader")
    }
}