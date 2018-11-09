package com.cayzerok.core

import com.cayzerok.ui.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*



fun coreStart() {
    if (!glfwInit()) {
        throw Exception("GLFW_INIT_ERR")
    }
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
    val mainWin = glfwCreateWindow(25* winMultiple, 25* winMultiple, "Snake Bizzare Adventure", 0, 0)
    glfwShowWindow(mainWin)
    glfwMakeContextCurrent(mainWin)
    GL.createCapabilities()
    glEnable(GL_TEXTURE_2D)

    while (!glfwWindowShouldClose(mainWin)) {
        Statistics.frameStart = System.currentTimeMillis()
        getInput(mainWin)
        glfwPollEvents()
        mainLoop()
        glfwSwapBuffers(mainWin)
        Statistics.frameEnd = System.currentTimeMillis()
        Statistics.ReadFrameRate()
    }
}