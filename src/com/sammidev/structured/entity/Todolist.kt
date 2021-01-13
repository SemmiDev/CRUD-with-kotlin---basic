package com.sammidev.structured.entity

import java.time.LocalDateTime
import java.util.*

data class Todolist(
        var id: UUID?,
        var todo: String,
        var desc: String?,
        var start: LocalDateTime,
        var end: LocalDateTime
)