package com.cayzerok.core

import com.cayzerok.entity.player
import com.cayzerok.network.*
import com.cayzerok.render.firstRenderLoop
import com.cayzerok.render.playerList
import com.cayzerok.render.secondRenderLoop
import com.cayzerok.render.thirdRenderLoop
import com.cayzerok.ui.Statistics
import com.cayzerok.world.TileList
import com.cayzerok.world.layerList
import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.Datagram
import io.ktor.network.sockets.aSocket
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.*
import kotlinx.io.core.readBytes
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import java.net.InetSocketAddress



//val serverAddress = InetSocketAddress("195.208.253.195", 3335)
val serverAddress = InetSocketAddress("localhost",9090)
//val path = File(MainWindow::class.java.protectionDomain.codeSource.location.toURI()).path.dropLast(14)
val path = "./src/main/resources/"


@ObsoleteCoroutinesApi
@KtorExperimentalAPI
fun main(args: Array<String>) = runBlocking {
    val client = aSocket(ActorSelectorManager(Dispatchers.IO)).udp().connect(serverAddress)
    println(path)
//    while (serverAddress.port == 3335) {
//        val connect = client.outgoing
//        packet.writeByte(random.nextInt(10,122).toByte())
//        connect.send(Datagram(packet.build(), serverAddress))
//        delay(1000)
//    }
    if (!GLFW.glfwInit()) {
        throw Exception("GLFW_INIT_ERROR")
    }
    GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE)
    MainWindow.window = GLFW.glfwCreateWindow(MainWindow.width, MainWindow.height, "Kotline Miami", MainWindow.monitor, 0)
    GLFW.glfwShowWindow(MainWindow.window)
    GLFW.glfwMakeContextCurrent(MainWindow.window)
    input = Input(MainWindow.window)
    GL.createCapabilities()
    GLFW.glfwSwapInterval(0)
    GL11.glEnable(GL11.GL_TEXTURE_2D)
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    GL11.glDisable(GL11.GL_DEPTH_TEST)
    GL11.glScalef(2f,2f,2f)
    GLFW.glfwSetInputMode(MainWindow.window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN)
    layerList[0].setTile(TileList.waterTile.id, 0, 0)
    initialize()
    shader.bind()

    while (player.uuid == null) {
        try {
            withTimeout(2000) {
                val connect = client.outgoing
                packet.writeByte(0)
                connect.send(Datagram(packet.build(), serverAddress))
                player = playerList.first { it.uuid == null }
                val init = client.incoming.receive().packet.readBytes()
                player.uuid = getUUIDFromBytes(init.copyOfRange(1, 17))
                if (init.size > 16)
                    for (index in 1 until init.size / 16)
                        addChar(init.copyOfRange(1 + index * 16, 1 + index * 16 + 16))
                player.isEnemy = false
            }
        }catch (e:TimeoutCancellationException) {}
    }
    println("Connected. UUID: ${player.uuid}")
    playerList[4].setPos(Vector3f(-200f, 300f, 0f))

    val netInput = launch(Dispatchers.IO) { getInput(client) }
    val netOutput = launch(Dispatchers.IO) { sendOutput(client) }
    val network = launch(Dispatchers.IO) { networkLoop() }
    val game = launch {
        while (!GLFW.glfwWindowShouldClose(MainWindow.window)) {
            Statistics.readFrameRate()
            GL11.glClearColor(red + 0.8f, 0.8f, 0.6f, 1f)
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
            getInput(MainWindow.window)
            input.update()
            mainLoop()
            firstRenderLoop()
            secondRenderLoop()
            thirdRenderLoop()
            playerList.forEach { it.gun.reloadGun() }
            GLFW.glfwSwapBuffers(MainWindow.window) }
        outChannel.send(Cell(player.uuid!!, byteArrayOf(5)))
        network.cancel()
        netInput.cancel()
        netOutput.cancel() }
    network.join()
    netInput.join()
    game.join()
    netOutput.join()
    layerList.forEach { it.saveWorld() }
}


fun stableFloat(number:Float): Float {
    return number*Statistics.frameTime/100000000.0f
}