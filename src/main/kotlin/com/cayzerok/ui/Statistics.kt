package com.cayzerok.ui

object Statistics {
    var lastFrame: Float = 0.0f
    var thisFrame: Float = 0.0f
    var frameTime:Float = 0.0f
    var milis: Float = 0.0f
    private var frames: Int = 0
    var frameRate: Int = 0

    fun readFrameRate() {
        lastFrame = thisFrame
        thisFrame = System.nanoTime()*0.000001.toFloat()
        frameTime = thisFrame - lastFrame
        milis += frameTime
        frames++
        if (milis >= 1000) {
            milis = 0.0f
            frameRate = frames
            frames = 0
            println("FPS: $frameRate")
            println("FrameTime: $frameTime")
        }
    }
}
