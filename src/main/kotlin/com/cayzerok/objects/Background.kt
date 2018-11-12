package com.cayzerok.objects

import com.cayzerok.core.mainCamera
import com.cayzerok.core.stabileFloat
import com.cayzerok.render.*
import com.cayzerok.ui.Statistics
import org.joml.Vector3f

fun drawBackground() {
    val bacShader = Shader()
    bacShader.setUniform("time", stabileFloat(Statistics.frameTime))
    bacShader.init("colorSea")
}