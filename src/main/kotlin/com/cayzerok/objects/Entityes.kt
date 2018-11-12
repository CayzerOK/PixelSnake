package com.cayzerok.objects

import com.cayzerok.render.EntityModel
import com.cayzerok.render.Texture

object centerDot {
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
        model.renderIt()
        texture.unbind()
    }
    fun init() {
        model.setModel(vertices, texCoords, indices)
        texture.decodePNG("background.png")
    }
}