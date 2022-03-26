package dev.tutorial.kmpizza.backend.utils

import io.ktor.http.content.*
import java.io.File

fun PartData.FileItem.toFile() = File(originalFileName!!).also { file ->
    streamProvider().use { input ->
        file.outputStream().buffered().use { output ->
            input.copyTo(output)
        }
    }
}
