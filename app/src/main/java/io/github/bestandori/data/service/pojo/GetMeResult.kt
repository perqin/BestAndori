package io.github.bestandori.data.service.pojo

class GetMeResult(
    result: Boolean,
    val username: String,
    val email: String
) : Result(result)
