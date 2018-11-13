package com.cayzerok.objects

import com.cayzerok.render.EntityModel
import com.cayzerok.render.Shader
import com.cayzerok.render.Texture
import com.cayzerok.render.projection

object centerDot {
    val texture = Texture("background")

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
    val model = EntityModel(vertices, texCoords, indices)

    fun renderIt(){
        texture.bind(0)
        model.renderIt()
        texture.unbind()
    }
}