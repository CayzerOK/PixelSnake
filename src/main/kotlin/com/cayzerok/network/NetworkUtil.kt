package com.cayzerok.network

import com.cayzerok.entity.Player
import java.nio.ByteBuffer
import java.util.*

data class Cell(
    val user:UUID,
    val data:ByteArray
)

fun serializePlayerData(player: Player) : ByteArray {
    val data = ByteArray(25)
    val uuidBytes = getBytesFromUUID(player.uuid!!)
    uuidBytes.forEachIndexed { index, byte ->
        data[index] = byte }
    player.moveKeys.forEachIndexed{ index, it ->
        data[index+16] = (if (it) 1 else 0).toByte() }
    data[20] = (if (player.mustShoot) 1 else 0).toByte()
    getByteArray(player.angle).forEachIndexed { index, byte ->
        data[index+21] = byte }
    return data
}

fun deserializePlayerData(data: ByteArray) : UserData {
    val moveKeys = BooleanArray(4)
    for (index in 0..3) {
        moveKeys[index] = (data[index+16] == 1.toByte())
    }
    return UserData(
            getUUIDFromBytes(data.copyOfRange(0,16)),
            moveKeys,
            data[20].toInt(),
            getFloat(data.copyOfRange(21, 25)),
            data[25].toInt())
}

fun getByteArray(value: Float): ByteArray {
    return ByteBuffer.allocate(4).putFloat(value).array()
}

fun getFloat(value:ByteArray) : Float {
    return ByteBuffer.wrap(value).float
}

fun getBytesFromUUID(uuid: UUID): ByteArray {
    val bb = ByteBuffer.wrap(ByteArray(16))
    bb.putLong(uuid.mostSignificantBits)
    bb.putLong(uuid.leastSignificantBits)
    bb.flip()
    return bb.array()
}

fun getUUIDFromBytes(bytes: ByteArray): UUID {
    val byteBuffer = ByteBuffer.wrap(bytes)
    val high = byteBuffer.long
    val low = byteBuffer.long

    return UUID(high, low)
}