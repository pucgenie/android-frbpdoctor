package com.friendly_machines.frbpdoctor.watchprotocol.command

import com.friendly_machines.frbpdoctor.watchprotocol.WatchCommand
import java.nio.ByteBuffer
import java.nio.ByteOrder

class SetWatchFaceCommand(dialPos: Int) : WatchCommand(47, run {
    val buf = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN)
    buf.putInt(dialPos)
    buf.array()
})