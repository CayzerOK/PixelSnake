package com.cayzerok.core

import com.cayzerok.entity.shoot
import com.cayzerok.ui.*
import com.cayzerok.render.*
import com.cayzerok.world.*
import kotlinx.coroutines.runBlocking
import org.joml.Matrix4f
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import java.io.FileNotFoundException

object mainWindow {
    var isFullSchreen = false
    val monitor:Long = if (isFullSchreen) {glfwGetPrimaryMonitor()} else {0}
    val width = if (isFullSchreen){glfwGetVideoMode(glfwGetPrimaryMonitor())!!.width()} else {1280}
    val height = if (isFullSchreen){glfwGetVideoMode(glfwGetPrimaryMonitor())!!.height()} else {720}
}

var red = 0f
val shader = Shader("shader")
var window:Long = 0
fun coreStart() = runBlocking {
    if (!glfwInit()) {
        throw Exception("GLFW_INIT_ERROR")
    }
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
    window = glfwCreateWindow(mainWindow.width, mainWindow.height, "Snake Bizzare Adventure", mainWindow.monitor, 0)
    glfwShowWindow(window)
    glfwMakeContextCurrent(window)
    input = Input(window)
    GL.createCapabilities()
    glEnable(GL_TEXTURE_2D)
    glEnable(GL_BLEND)
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    glDisable(GL_DEPTH_TEST)
    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN)
    Land1.setTile(TileList.grass0.id,0,0)
    initialize()
    shader.bind()
    shoot(Vector3f(), Matrix4f())
    while (!glfwWindowShouldClose(window)) {
        Statistics.readFrameRate()
        glClearColor(red+0.8f, 0.8f, 0.6f, 1f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        getInput(window)
        input.update()
        mainLoop()
        firstRenderLoop()
        secondRenderLoop()
        thirdRenderLoop()
        channel.send(true)
        glfwSwapBuffers(window)
    }
    World.saveWays()
    player.save()
    Land1.saveWorld()
    Land2.saveWorld()
}