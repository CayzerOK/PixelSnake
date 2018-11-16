package com.cayzerok.render

import org.joml.*
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20.*
import java.nio.FloatBuffer



class EntityModel(vertices: FloatArray, texCoords: FloatArray, indices:IntArray){
    var vID: Int? = null
    var drawCount: Int? = null
    var iDrawCount: Int? = null
    var tID: Int? = null
    var iID: Int? = null

    init {
        drawCount = vertices.size
        iDrawCount = indices.size
        vID = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vID!!)
        glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW)

        tID = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, tID!!)
        glBufferData(GL_ARRAY_BUFFER, createBuffer(texCoords), GL_STATIC_DRAW)

        iID = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,iID!!)
        val iBuffer = BufferUtils.createIntBuffer(indices.size)
        iBuffer.put(indices)
        iBuffer.flip()
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, iBuffer, GL_STATIC_DRAW)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }
    protected fun finalize() {
        GL15.glDeleteBuffers(tID!!)
        GL15.glDeleteBuffers(vID!!)
        GL15.glDeleteBuffers(iID!!)
    }
    fun renderIt() {
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)

        glBindBuffer(GL_ARRAY_BUFFER, vID!!)
        glVertexAttribPointer(0,3, GL11.GL_FLOAT,false,0,0)

        glBindBuffer(GL_ARRAY_BUFFER, tID!!)
        glVertexAttribPointer(1,2, GL11.GL_FLOAT,false,0,0)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iID!!)
        glDrawElements(GL_TRIANGLES, iDrawCount!!, GL_UNSIGNED_INT,0)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)

        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
    }
    private fun createBuffer(data:FloatArray):FloatBuffer {
        val buffer = BufferUtils.createFloatBuffer(drawCount!!)
        buffer.put(data)
        buffer.flip()
        return buffer
    }
}