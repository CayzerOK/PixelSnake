package com.cayzerok.render

import org.lwjgl.opengl.GL13.*
import de.matthiasmann.twl.utils.PNGDecoder
import java.io.IOException
import org.lwjgl.opengl.GL11
import org.lwjgl.BufferUtils
import java.io.FileInputStream
import java.io.BufferedInputStream

val assets = "./resources/"

class Texture(asset:String) {
    val id = GL11.glGenTextures()
    var width:Int = 0
    var height:Int = 0

    init {
        try {
            BufferedInputStream(FileInputStream(assets+asset+".png")).use { `is` ->
                val decoder = PNGDecoder(`is`)
                width = decoder.getWidth()
                height = decoder.getHeight()
                val pixelData = BufferUtils.createByteBuffer(4 * width * height)
                decoder.decode(pixelData, 4 * width, PNGDecoder.Format.RGBA)
                pixelData.flip()
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, id)
                GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixelData)
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT)
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT)
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    fun bind(sampler:Int) {
        if(sampler in 0..31){
            glActiveTexture(GL_TEXTURE0+sampler)
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id)
        }
    }
    fun unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
    }
}
