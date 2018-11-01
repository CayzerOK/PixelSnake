import org.lwjgl.glfw.GLFW.glfwTerminate
val winMultiple:Int = 15
val snakeDirect:Int = 0
val snake = mutableListOf(Section(1,1), Section(1,2),Section(1,3))
data class Section(
        val x:Int,
        val y:Int
)
fun main(args: Array<String>) {
    try {
        coreStart()
    } finally {
        glfwTerminate()
    }
}