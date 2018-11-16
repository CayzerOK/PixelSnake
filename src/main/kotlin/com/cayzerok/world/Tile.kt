package com.cayzerok.world

import java.lang.IllegalStateException

val tiles = Array<Tile?>(16, {null})

class Tile(var id:Int, var texture:String) {

    init {
        if (tiles[id] != null) {
            throw IllegalStateException("Tile id [$id] is already used.")
        } else {
            tiles[id] = this
        }
    }
}


object TileList{
    val aim = Tile(4, "aim")
    val stoneTile = Tile(1,"stone")
    val grassTile = Tile(2, "grass")
    val waterTile = Tile(3, "water")
}



