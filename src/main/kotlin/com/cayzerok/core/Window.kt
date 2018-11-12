package com.cayzerok.core

import com.cayzerok.ui.*
import com.cayzerok.render.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*

object mainWindow{
    val width = 1600
    val height = 900
}
val mainCamera = Camera()


fun coreStart() {
    if (!glfwInit()) {
        throw Exception("GLFW_INIT_ERROR")
    }
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
    val win = glfwCreateWindow(mainWindow.width, mainWindow.height, "Snake Bizzare Adventure", 0, 0)
    glfwShowWindow(win)
    glfwMakeContextCurrent(win)
    GL.createCapabilities()
    glEnable(GL_TEXTURE_2D)
    mainCamera.use(mainWindow.width,mainWindow.height)
    initialize()
    while (!glfwWindowShouldClose(win)) {
        glClearColor(0f, 0f, 0f, 0f)
        glClearDepth(1.0)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        Statistics.frameStart = System.nanoTime()/1000000.toDouble()
        getInput(win)
        glfwPollEvents()
        mainLoop()
        firstRenderLoop()
        secondRenderLoop()
        thirdRenderLoop()
        glfwSwapBuffers(win)
        Statistics.frameEnd = System.nanoTime()/1000000.toDouble()
        Statistics.ReadFrameRate()
    }
}