package io.github.bestandori.data.storage

interface FileStorage {
    suspend fun save(filename: String, content: String)
    suspend fun load(filename: String): String
    suspend fun list(): List<String>
}
