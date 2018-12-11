package com.cayzerok.core

import com.cayzerok.experemental.mainWindow
import com.cayzerok.guns.reloadGun
import com.cayzerok.render.Shader
import com.cayzerok.render.firstRenderLoop
import com.cayzerok.render.secondRenderLoop
import com.cayzerok.render.thirdRenderLoop
import com.cayzerok.ui.Statistics
import com.cayzerok.world.TileList
import com.cayzerok.world.World
import com.cayzerok.world.layerList
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
    glfwSwapInterval(0)
    glEnable(GL_TEXTURE_2D)
    glEnable(GL_BLEND)
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    glDisable(GL_DEPTH_TEST)
    glfwSetInputMode(mainWindow.window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN)
    layerList[0].setTile(TileList.grass0.id, 0, 0)
    initialize()
    com.cayzerok.experemental.shader.bind()
    while (!glfwWindowShouldClose(mainWindow.window)) {
        networkLoop()
        Statistics.readFrameRate()
        glClearColor(com.cayzerok.experemental.red + 0.8f, 0.8f, 0.6f, 1f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        getInput(mainWindow.window)
        input.update()
        com.cayzerok.experemental.mainLoop()
        firstRenderLoop()
        secondRenderLoop()
        thirdRenderLoop()
        reloadGun()
        glfwSwapBuffers(mainWindow.window)
    }
    World.saveWays()
    layerList.forEach { it.saveWorld() }
}
