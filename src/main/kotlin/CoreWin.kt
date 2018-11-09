import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import java.io.File
import javax.imageio.ImageIO



fun coreStart() {
    if (!glfwInit()) {
        throw Exception("GLFW_INIT_ERR")
    }
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
    val mainWin = glfwCreateWindow(25*winMultiple, 25*winMultiple, "Snake Bizzare Adventure", 0, 0)
    glfwShowWindow(mainWin)
    glfwMakeContextCurrent(mainWin)
    GL.createCapabilities()
    glEnable(GL_TEXTURE_2D)
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

    val model = Model()
    model.setModel(bacVertices,background)

    Texturise.decodePNG("./src/main/resources/pidor3.png")

    while (!glfwWindowShouldClose(mainWin)) {
        getInput(mainWin)
        glfwPollEvents()
        model.renderIt()
        mainLoop()
        glfwSwapBuffers(mainWin)
    }
}