package com.cayzerok.objects

import com.cayzerok.render.EntityModel
import com.cayzerok.render.Texture

class Entity() {
    val texture = Texture()
    val model = EntityModel()

    fun renderIt(){
        texture.bind(0)
        model.renderIt()
        texture.unbind()
    }
    fun initTexture(vertices:FloatArray, texCoords:FloatArray,indices: IntArray, textureName:String) {
        model.setModel(vertices,texCoords, indices)
        texture.decodePNG(textureName)
    }
}