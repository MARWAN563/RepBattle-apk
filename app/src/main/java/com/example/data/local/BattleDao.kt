package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BattleDao {
    @Query("SELECT * FROM battles ORDER BY id DESC")
    fun getAllBattles(): Flow<List<BattleEntity>>

    @Query("SELECT * FROM battles WHERE isWin = 1 ORDER BY id DESC")
    fun getWonBattles(): Flow<List<BattleEntity>>

    @Query("SELECT * FROM battles WHERE isWin = 0 ORDER BY id DESC")
    fun getLostBattles(): Flow<List<BattleEntity>>

    @Query("SELECT COUNT(*) FROM battles")
    fun getBattleCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM battles WHERE isWin = 1")
    fun getWinCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBattle(battle: BattleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBattles(battles: List<BattleEntity>)
}
