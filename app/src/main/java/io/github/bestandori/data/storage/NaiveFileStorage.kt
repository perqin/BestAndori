package io.github.bestandori.data.storage

import java.io.File

class NaiveFileStorage(private val rootDirectory: File) : FileStorage {
    init {
        if (!rootDirectory.isDirectory && !rootDirectory.mkdirs()) {
            throw RuntimeException("Failed to create rootDirectory: $rootDirectory")
        }
    }

    override suspend fun save(filename: String, content: String) {
        rootDirectory.resolve(filename).writeText(content)
    }

    override suspend fun load(filename: String): String {
        return rootDirectory.resolve(filename).readText()
    }

    override suspend fun list(): List<String> {
        return (rootDirectory.list() ?: emptyArray()).toList()
    }
}