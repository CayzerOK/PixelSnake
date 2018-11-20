package com.cayzerok.core

import com.cayzerok.ui.*
import com.cayzerok.render.*
import com.cayzerok.world.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import java.io.FileNotFoundException

object mainWindow {
    var isFullSchreen = false
    val monitor:Long = if (isFullSchreen) {glfwGetPrimaryMonitor()} else {0}
    val width = if (isFullSchreen){glfwGetVideoMode(glfwGetPrimaryMonitor())!!.width()} else {720}
    val height = if (isFullSchreen){glfwGetVideoMode(glfwGetPrimaryMonitor())!!.height()} else {480}
}

var red = 0f
val shader = Shader("shader")
var window:Long = 0
fun coreStart() {
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
    initialize()
    Land1.setTile(TileList.grass0.id,0,0)
    shader.bind()
    try {
        player.load()
    }catch (e:FileNotFoundException) {}

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
        glfwSwapBuffers(window)
    }
    player.save()
    Land1.saveWorld()
    Land2.saveWorld()
}