import org.lwjgl.opengl.GL11.*
import org.lwjgl.BufferUtils
import jdk.nashorn.internal.objects.ArrayBufferView.buffer



object Table{
    val id = glGenTextures()
    val width:Int = background.width
    val height:Int = background.height
    fun texture() {
        println("$width X $height")
        val pixels = BufferUtils.createByteBuffer(width * height * 4)
        for (x in 0..width-1) {
            for (y in 0..height-1) {
                val pixel = pixelsRaw[x * width + y]
                pixels.put((pixel shr 16 and 0xFF).toByte())     // Red component
                pixels.put((pixel shr 8 and 0xFF).toByte())      // Green component
                pixels.put((pixel and 0xFF).toByte())               // Blue component
                pixels.put((pixel shr 24 and 0xFF).toByte())    // Alpha component. Only for RGBA
            }
        }
        glBindTexture(GL_TEXTURE_2D, id)
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST.toFloat())
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST.toFloat())

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height,0, GL_RGBA, GL_UNSIGNED_BYTE, pixels)
    }

    fun bind() {
        glBindTexture(GL_TEXTURE_2D, id)
    }
}