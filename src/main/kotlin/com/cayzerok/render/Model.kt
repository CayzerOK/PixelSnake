package com.cayzerok.render

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15.*
import java.nio.FloatBuffer

class EntityModel{
    var vID: Int? = null
    var drawCount: Int? = null
    var tID: Int? = null

    fun setModel(vertices: FloatArray, texCoords: FloatArray) {
        drawCount = vertices.size
        vID = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vID!!)
        glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW)

        tID = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, tID!!)
        glBufferData(GL_ARRAY_BUFFER, createBuffer(texCoords), GL_STATIC_DRAW)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    fun renderIt() {
        glEnableClientState(GL_VERTEX_ARRAY)
        glEnableClientState(GL_TEXTURE_COORD_ARRAY)

        glBindBuffer(GL_ARRAY_BUFFER, vID!!)
        glVertexPointer(3, GL_FLOAT, 0, 0)

        glBindBuffer(GL_ARRAY_BUFFER, tID!!)
        glTexCoordPointer(2, GL11.GL_FLOAT,0,0)


        glDrawArrays(GL_TRIANGLES, 0, drawCount!!)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glDisableClientState(GL_VERTEX_ARRAY)
        glDisableClientState(GL_TEXTURE_COORD_ARRAY)
    }
    private fun createBuffer(data:FloatArray):FloatBuffer {

        val buffer = BufferUtils.createFloatBuffer(drawCount!!)
        buffer.put(data)
        buffer.flip()
        return buffer
    }
}