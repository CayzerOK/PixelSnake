package com.cayzerok.ui

object Statistics {
    var lastFrame: Float = 0.0f
    var thisFrame: Float = 0.0f
    var frameTime: Float = 0.0f
    var milis: Float = 0.0f
    private var frames: Int = 0
    var frameRate: Int = 0

    fun readFrameRate() {
        lastFrame = thisFrame
        thisFrame = System.nanoTime() * 0.000001.toFloat()
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



class Timer(val pointRate:Int) {
    var points = 0
    var lastFrame: Float = 0f
    var thisFrame: Float = 0f
    var frameTime: Float = 0f
    var milis: Float = 0f
    private var frames: Int = 0
    var frameRate: Int = 0

    fun calculate() {
        lastFrame = thisFrame
        thisFrame = System.nanoTime() * 0.000001.toFloat()
        frameTime = thisFrame - lastFrame
        milis += frameTime
        frames++
        if (milis >= pointRate) {
            points ++
            milis = 0.0f
            frameRate = frames
            frames = 0
        }
    }
    fun `break`() {
        points = 0
        lastFrame = 0f
        thisFrame = 0f
        frameTime = 0f
        frameRate = 0
        milis = 0f
        frames = 0
    }
}