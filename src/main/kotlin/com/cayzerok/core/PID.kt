package com.cayzerok.core

import com.cayzerok.ui.Statistics
import org.joml.Vector3f
import java.lang.Math.*

object camPID {
    var xP:Float = 0f
    var yP:Float = 0f

    fun stabileVec3(target: Vector3f) {

        if(mainCamera.camPosition.x<target.x) { xP+stabileFloat(10f)}
        if(mainCamera.camPosition.x>target.x) { xP-stabileFloat(10f)}
        if(mainCamera.camPosition.y<target.y) { yP+stabileFloat(10f)}
        if(mainCamera.camPosition.y>target.y) { yP-stabileFloat(10f)}

        mainCamera.camPosition.add(xP,yP,0f)

    }
}
