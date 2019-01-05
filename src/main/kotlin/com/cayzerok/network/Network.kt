package com.cayzerok.network

import com.cayzerok.core.serverAddress
import com.cayzerok.entity.player
import io.ktor.network.sockets.ConnectedDatagramSocket
import io.ktor.network.sockets.Datagram
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.io.core.BytePacketBuilder
import kotlinx.io.core.readBytes
import kotlinx.io.core.writeFully
import java.util.*



data class UserData(
        val uuid:UUID,
        val moveKeys:BooleanArray,
        val cells:Int,
        val angle:Float,
        val HP:Int
)

val packet = BytePacketBuilder()
val outChannel = Channel<Cell>()

suspend fun sendOutput(server: ConnectedDatagramSocket) {
    while (true) {
        val outData = outChannel.receive()
        val output = server.outgoing
        packet.writeFully(outData.data)
        output.send(Datagram(packet.build(), serverAddress))
    }
}


suspend fun getInput(server: ConnectedDatagramSocket) {
    while (true) {
        val input = server.incoming.receive()
        val inputBytes = input.packet.readBytes()
        when(inputBytes[0]) {
            0.toByte() -> addChar(inputBytes.copyOfRange(1, inputBytes.lastIndex+1))
            1.toByte() -> genericFrame(inputBytes.copyOfRange(1, inputBytes.lastIndex+1))
            2.toByte() -> keyFrame(inputBytes.copyOfRange(1, inputBytes.lastIndex+1))
            3.toByte() -> {} //damage
            4.toByte() -> {} //dead
            5.toByte() -> removeChar(inputBytes.copyOfRange(1, inputBytes.lastIndex+1))
        }
    }
}

suspend fun networkLoop() {
    while (true) {
        delay(20)
        var outData = ByteArray(1)
        outData[0] = 1.toByte()
        outData += serializePlayerData(player)
        outChannel.send(Cell(player.uuid!!, outData))
    }
}



