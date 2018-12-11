package com.cayzerok.experemental

import io.ktor.network.sockets.ConnectedDatagramSocket
import io.ktor.network.sockets.Datagram
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withTimeout
import kotlinx.io.core.BytePacketBuilder
import kotlinx.io.core.readUTF8Line
import kotlinx.io.core.writeFully
import org.lwjgl.glfw.GLFW

val packet = BytePacketBuilder()
val outChannel = Channel<Cell>()

suspend fun sendOutput(server: ConnectedDatagramSocket) {
    while (!GLFW.glfwWindowShouldClose(mainWindow.window)) {
        withTimeout(1000) {
            val outData = outChannel.receive()
            val output = server.outgoing
            val outJSON = gson.toJson(outData)
            packet.writeFully(outJSON.toByteArray())
        }
    }
}

val inChannel = Channel<Datagram>()

suspend fun getInput(server:ConnectedDatagramSocket) {
    while (!GLFW.glfwWindowShouldClose(mainWindow.window)) {
        withTimeout(1000) {
            val input = server.incoming.receive()
            val inputString = input.packet.readUTF8Line()
            println(inputString)
        }
    }
}