package com.cayzerok.core

import com.cayzerok.ui.Statistics
import io.ktor.network.selector.ActorSelectorManager
import io.ktor.network.sockets.ConnectedDatagramSocket
import io.ktor.network.sockets.aSocket
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.lwjgl.glfw.GLFW.glfwTerminate
import java.net.InetSocketAddress
import java.util.*

data class outputCell(
        val user:UUID,
        val data:Array<Any>
        )

const val speedMultiple = 1
//val path = mainCamera::class.java.protectionDomain.codeSource.location.path.dropLast(14)
const val path = "./src/main/resources/"

var clientSocket: ConnectedDatagramSocket? = null

@ExperimentalCoroutinesApi
@KtorExperimentalAPI
fun main(args: Array<String>){
    clientSocket = aSocket(ActorSelectorManager(Dispatchers.IO)).udp().connect(InetSocketAddress("127.0.0.1", 8080))
    println(path)
    try {
        coreStart()
    } finally {
        glfwTerminate()
    }
}

fun stableFloat(number:Float): Float {
    return number*Statistics.frameTime*speedMultiple/100
}