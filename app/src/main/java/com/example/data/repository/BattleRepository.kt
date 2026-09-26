package com.example.data.repository

import com.example.data.local.BattleDao
import com.example.data.local.BattleEntity
import kotlinx.coroutines.flow.Flow

class BattleRepository(private val battleDao: BattleDao) {
    val allBattles: Flow<List<BattleEntity>> = battleDao.getAllBattles()
    val wonBattles: Flow<List<BattleEntity>> = battleDao.getWonBattles()
    val lostBattles: Flow<List<BattleEntity>> = battleDao.getLostBattles()
    val battleCount: Flow<Int> = battleDao.getBattleCount()
    val winCount: Flow<Int> = battleDao.getWinCount()

    suspend fun insertBattle(battle: BattleEntity): Long {
        return battleDao.insertBattle(battle)
    }
}
