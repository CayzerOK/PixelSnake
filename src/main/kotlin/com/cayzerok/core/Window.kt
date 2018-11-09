package com.cayzerok.core

import com.cayzerok.objects.*
import com.cayzerok.ui.*
import com.cayzerok.render.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*



fun coreStart() {
    if (!glfwInit()) {
        throw Exception("GLFW_INIT_ERROR")
    }
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)
    val mainWin = glfwCreateWindow(16* winMultiple, 9* winMultiple, "Snake Bizzare Adventure", 0, 0)
    glfwShowWindow(mainWin)
    glfwMakeContextCurrent(mainWin)
    GL.createCapabilities()
    glEnable(GL_TEXTURE_2D)
    initialize()
    while (!glfwWindowShouldClose(mainWin)) {
        Statistics.frameStart = System.currentTimeMillis()
        getInput(mainWin)
        glfwPollEvents()
        mainLoop()
        firstRenderLoop()
        secondRenderLoop()
        thirdRenderLoop()
        glfwSwapBuffers(mainWin)
        Statistics.frameEnd = System.currentTimeMillis()
        Statistics.ReadFrameRate()
    }
}