package com.cayzerok.core

import com.cayzerok.guns.reloadGun
import com.cayzerok.ui.*
import com.cayzerok.render.*
import com.cayzerok.world.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

object mainWindow {
    var window:Long = 0
    var isFullSchreen = false
    var monitor:Long = if (isFullSchreen) {glfwGetPrimaryMonitor()} else {0}
    var width = if (isFullSchreen){glfwGetVideoMode(glfwGetPrimaryMonitor())!!.width()} else {1280}
    var height = if (isFullSchreen){glfwGetVideoMode(glfwGetPrimaryMonitor())!!.height()} else {720}
}

var red = 0f
val shader = Shader("shader")

fun coreStart() {
    if (!glfwInit()) {
        throw Exception("GLFW_INIT_ERROR")
    }
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
    mainWindow.window = glfwCreateWindow(mainWindow.width, mainWindow.height, "Kotline Miami", mainWindow.monitor, 0)
    glfwShowWindow(mainWindow.window)
    glfwMakeContextCurrent(mainWindow.window)
    input = Input(mainWindow.window)
    GL.createCapabilities()
    glEnable(GL_TEXTURE_2D)
    glEnable(GL_BLEND)
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    glDisable(GL_DEPTH_TEST)
    glfwSetInputMode(mainWindow.window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN)
    layerList[0].setTile(TileList.grass0.id, 0, 0)
    initialize()
    shader.bind()
    while (!glfwWindowShouldClose(mainWindow.window)) {
        Statistics.readFrameRate()
        glClearColor(red + 0.8f, 0.8f, 0.6f, 1f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        getInput(mainWindow.window)
        input.update()
        mainLoop()
        firstRenderLoop()
        secondRenderLoop()
        thirdRenderLoop()
        reloadGun()
        glfwSwapBuffers(mainWindow.window)
    }
    World.saveWays()
    player.save()
    layerList.forEach { it.saveWorld() }
}
