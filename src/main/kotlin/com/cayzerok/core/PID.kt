package com.cayzerok.core

import org.joml.Vector3f

object camPID {
    fun stabileCam(target: Vector3f) {
        val xP= (target.x-mainCamera.camPosition.x)/5
        val yP= (target.y-mainCamera.camPosition.y)/5
        mainCamera.camPosition.add(stabileFloat(xP), stabileFloat(yP),0f)
    }
}
