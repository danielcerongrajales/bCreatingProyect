package com.example.bcreatingproyect.home.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SyncEntity(
    @PrimaryKey(autoGenerate = false)
    val id:String
)