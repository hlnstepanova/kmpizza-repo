package dev.tutorial.kmpizza.util

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation
import platform.posix.memcpy

actual typealias ImageFile = UIImage

actual fun ImageFile.toByteArray() = UIImagePNGRepresentation(this)?.toByteArray() ?: emptyArray<Byte>().toByteArray()

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
private fun NSData.toByteArray(): ByteArray = ByteArray(length.toInt()).apply {
    usePinned {
        memcpy(it.addressOf(0), bytes, length)
    }
}
