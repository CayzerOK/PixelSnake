
import de.matthiasmann.twl.utils.PNGDecoder
import java.io.IOException
import org.lwjgl.opengl.GL11
import org.lwjgl.BufferUtils
import java.io.FileInputStream
import java.io.BufferedInputStream

object Texturise {
    val id = GL11.glGenTextures()

    fun decodePNG(path:String) {
        try {
            BufferedInputStream(FileInputStream(path)).use { `is` ->
                //Create the PNGDecoder object and decode the texture to a buffer
                val decoder = PNGDecoder(`is`)
                val width = decoder.getWidth()
                val height = decoder.getHeight()
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
    fun bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id)
    }
    fun unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
    }
}
