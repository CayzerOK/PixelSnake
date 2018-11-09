package com.cayzerok.ui

object Statistics {
    var frameStart: Long? = null
    var frameEnd: Long? = null
    var frameTime: Long? = null
    private var timeBuffer: Long = 0
    private var currentframeRate: Int = 0
    var frameRate: Int = 0

    fun ReadFrameRate() {
        frameTime = frameEnd!! - frameStart!!
        timeBuffer = timeBuffer + frameTime!!
        currentframeRate++
        if (timeBuffer >= 1000) {
            timeBuffer = 0
            frameRate = currentframeRate
            currentframeRate = 0
            //println("FPS: $frameRate")
            //println("FrameTime: $frameTime")
        }
    }
}
