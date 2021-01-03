package com.samar.exam

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class Task(@PrimaryKey val id: UUID = UUID.randomUUID(),
                var title: String = "",
                var details:String="",
                var move: String = "",
                var date: Date = Date()) {
}

