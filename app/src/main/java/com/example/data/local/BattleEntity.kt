package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "battles")
data class BattleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val opponentName: String,
    val opponentHandle: String,
    val opponentAvatarUrl: String,
    val opponentInitials: String,
    val exerciseName: String,
    val userScore: Int,
    val opponentScore: Int,
    val isWin: Boolean,
    val formScorePercent: Int,
    val paceSeconds: Float,
    val timestampFormatted: String,
    val battleRule: String,
    val shareCode: String
)
