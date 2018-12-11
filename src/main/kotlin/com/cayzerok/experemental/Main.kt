package com.cayzerok.experemental


import com.cayzerok.core.path
import com.cayzerok.guns.reloadGun
import com.cayzerok.render.firstRenderLoop
import com.cayzerok.render.secondRenderLoop
import com.cayzerok.render.thirdRenderLoop
import com.cayzerok.ui.Statistics
import com.cayzerok.world.TileList
import com.cayzerok.world.layerList
import com.google.gson.Gson
import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.aSocket
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import java.net.InetSocketAddress
import java.util.*

val shouldClose = false
val gson = Gson()

data class Cell(
    val user:UUID,
    val data:Array<Any?>
)



@ObsoleteCoroutinesApi
@KtorExperimentalAPI
fun main(args: Array<String>) = runBlocking {
    val server = aSocket(ActorSelectorManager(Dispatchers.IO)).udp().connect(InetSocketAddress("127.0.0.1", 8080))
    println(path)
    val input = launch { getInput(server) }
    val output = launch { sendOutput(server) }

    if (!GLFW.glfwInit()) {
        throw Exception("GLFW_INIT_ERROR")
    }
    GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE)
    mainWindow.window = GLFW.glfwCreateWindow(mainWindow.width, mainWindow.height, "Kotline Miami", mainWindow.monitor, 0)
    GLFW.glfwShowWindow(mainWindow.window)
    GLFW.glfwMakeContextCurrent(mainWindow.window)
    com.cayzerok.experemental.input = Input(mainWindow.window)
    GL.createCapabilities()
    GLFW.glfwSwapInterval(0)
    GL11.glEnable(GL11.GL_TEXTURE_2D)
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GL11.glDisable(GL11.GL_DEPTH_TEST)
    GLFW.glfwSetInputMode(mainWindow.window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN)
    layerList[0].setTile(TileList.grass0.id, 0, 0)
    com.cayzerok.core.initialize()
    shader.bind()
    val game = launch {
        while (!GLFW.glfwWindowShouldClose(mainWindow.window)) {
            Statistics.readFrameRate()
            GL11.glClearColor(red + 0.8f, 0.8f, 0.6f, 1f)
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
            getInput(mainWindow.window)
            com.cayzerok.experemental.input.update()
            mainLoop()
            firstRenderLoop()
            secondRenderLoop()
            thirdRenderLoop()
            reloadGun()
            GLFW.glfwSwapBuffers(mainWindow.window)
        }
    }
    input.join()
    game.join()
    output.join()
}