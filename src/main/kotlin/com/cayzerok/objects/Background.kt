package com.cayzerok.objects

import com.cayzerok.render.EntityModel
import com.cayzerok.render.Texturise

object backround{
    val model = EntityModel()
    val bacVertices:FloatArray = floatArrayOf(
            -1f,1f,0f,
            1f,1f,0f,
            1f,-1f,0f,

            1f,-1f,0f,
            -1f,-1f,0f,
            -1f,1f,0f)
    val background:FloatArray = floatArrayOf(
            0f,0f,
            1f,0f,
            1f,1f,

            1f,1f,
            0f,1f,
            0f,0f
    )
    fun renderIt(){
        model.setModel(bacVertices,background)
        Texturise.decodePNG("background.png")
        model.renderIt()
    }
}