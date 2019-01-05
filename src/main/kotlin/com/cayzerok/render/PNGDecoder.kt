package com.cayzerok.render

import com.cayzerok.core.path
import de.matthiasmann.twl.utils.PNGDecoder
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.GL_TEXTURE0
import org.lwjgl.opengl.GL13.glActiveTexture
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.IOException

class Texture(asset:String) {
    val id = glGenTextures()
    var width:Int = 0
    var height:Int = 0

    init {
        try {
            BufferedInputStream(FileInputStream(path+"textures/"+asset+".png")).use { `is` ->
                val decoder = PNGDecoder(`is`)
                width = decoder.width
                height = decoder.height
                val pixelData = BufferUtils.createByteBuffer(4 * width * height)
                decoder.decode(pixelData, 4 * width, PNGDecoder.Format.RGBA)
                pixelData.flip()
                glBindTexture(GL11.GL_TEXTURE_2D, id)
                glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixelData)
                glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT)
                glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT)
                glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)
                glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
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
}
