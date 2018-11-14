package com.cayzerok.core

import com.cayzerok.ui.*
import com.cayzerok.render.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

object mainWindow {
    var isFullSchreen = false
    val monitor:Long = if (isFullSchreen) {glfwGetPrimaryMonitor()} else {0}
    val width = if (isFullSchreen){glfwGetVideoMode(glfwGetPrimaryMonitor())!!.width()} else {720}
    val height = if (isFullSchreen){glfwGetVideoMode(glfwGetPrimaryMonitor())!!.height()} else {480}
}

var red = 0f
val shader = Shader("shader")
fun coreStart() {
    if (!glfwInit()) {
        throw Exception("GLFW_INIT_ERROR")
    }

    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
    val win = glfwCreateWindow(mainWindow.width, mainWindow.height, "Snake Bizzare Adventure", mainWindow.monitor, 0)
    glfwShowWindow(win)
    glfwMakeContextCurrent(win)
    input = Input(win)
    GL.createCapabilities()
    glEnable(GL_TEXTURE_2D)
    glEnable(GL_BLEND)
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    initialize()
    shader.bind()

    while (!glfwWindowShouldClose(win)) {
        Statistics.readFrameRate()
        glClearColor(red+0.8f, 0.8f, 0.6f, 1f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        getInput(win)
        input.update()
        mainLoop()
        firstRenderLoop()
        secondRenderLoop()
        thirdRenderLoop()
        glfwSwapBuffers(win)
    }
}