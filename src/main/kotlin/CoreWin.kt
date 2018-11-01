import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import java.io.File
import javax.imageio.ImageIO

val background = ImageIO.read(File("./src/resources/pidor.png"))
val pixelsRaw = background!!.getRGB(0,0, background.width, background.height, null,0, background.width)

fun coreStart() {
    if (!glfwInit()) {
        throw Exception("GLFW_INIT_ERR")
    }
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
    val mainWin = glfwCreateWindow(25*winMultiple, 25*winMultiple, "Snake Bizzare Adventure", 0, 0)
    glfwShowWindow(mainWin)
    glfwMakeContextCurrent(mainWin)
    GL.createCapabilities()
    Table.texture()
    glEnable(GL_TEXTURE_2D)
    while (!glfwWindowShouldClose(mainWin)) {
        getInput(mainWin)
        glfwPollEvents()
        mainLoop()
        glfwSwapBuffers(mainWin)
    }
}