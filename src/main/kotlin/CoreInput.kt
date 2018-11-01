import org.lwjgl.glfw.GLFW.*
fun getInput(window:Long) {
    if (glfwGetMouseButton(window,0) == 1) {
        glfwSetWindowShouldClose(window,true)
    }
}