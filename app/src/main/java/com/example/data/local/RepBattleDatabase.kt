package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [BattleEntity::class], version = 1, exportSchema = false)
abstract class RepBattleDatabase : RoomDatabase() {
    abstract fun battleDao(): BattleDao

    companion object {
        @Volatile
        private var INSTANCE: RepBattleDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): RepBattleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RepBattleDatabase::class.java,
                    "repbattle_database"
                )
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialBattles(database.battleDao())
                    }
                }
            }

            private suspend fun populateInitialBattles(dao: BattleDao) {
                val initialBattles = listOf(
                    BattleEntity(
                        opponentName = "Adam Miller",
                        opponentHandle = "@miller_a",
                        opponentAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCphduOty87-FkzyGfnaqZBwMebXfl924Eca2ehclLk3WSNer8s-xHrzhF3zToQ1EY4h1m54FCb3i7FkWZxQf7f32EOit-TSWB2qa7ZJAzKwnWyJ09kbvUZ8hCJP0m8Th_7HczYvg7QXVMWUUX3SDtFqZynKr2B8UJoNqA_FdJnIi2IQ6KiW0Y1iuJzSyLPUPeKNnY_LQc3yyt0mEcLtpZ5Ki_3n6HlUzSa4YJatXIz--7ifaEjlM9EXQ",
                        opponentInitials = "AM",
                        exerciseName = "Push-ups",
                        userScore = 47,
                        opponentScore = 39,
                        isWin = true,
                        formScorePercent = 98,
                        paceSeconds = 1.2f,
                        timestampFormatted = "Today • 14:20",
                        battleRule = "First to 50",
                        shareCode = "8X29"
                    ),
                    BattleEntity(
                        opponentName = "Sarah Chen",
                        opponentHandle = "@sarahc",
                        opponentAvatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuApuIm4PcujRGsDX42gn8pWkpinKEkLk3kYdsb2XfI2wSF23hFsPm2xdcZRbxIY3QpaSleZn1euVuaJXQWx2r8Hzy2AdQZTr0pSEDv8qjaApBpvnLkkStH9MWKFZciq8BDFjhOIq328HfORnzqBLlIhNYy5HZDPFM2Lx-ihWWubMY9-ZMHuuyYG4HdOrlzl3ZuKhClSVEqyf0V7-a2pQJjoilmhU7JxWKmEiuEsBr91HOmzMDJwzd_s4Q",
                        opponentInitials = "SC",
                        exerciseName = "Squats",
                        userScore = 52,
                        opponentScore = 58,
                        isWin = false,
                        formScorePercent = 94,
                        paceSeconds = 1.4f,
                        timestampFormatted = "Yesterday",
                        battleRule = "Century (100)",
                        shareCode = "9L41"
                    ),
                    BattleEntity(
                        opponentName = "Ghost Rival",
                        opponentHandle = "@ai_ghost",
                        opponentAvatarUrl = "",
                        opponentInitials = "GR",
                        exerciseName = "Push-ups",
                        userScore = 41,
                        opponentScore = 35,
                        isWin = true,
                        formScorePercent = 97,
                        paceSeconds = 1.3f,
                        timestampFormatted = "Mon • Solo Duel",
                        battleRule = "First to 50",
                        shareCode = "2K18"
                    ),
                    BattleEntity(
                        opponentName = "David K.",
                        opponentHandle = "@david_k",
                        opponentAvatarUrl = "",
                        opponentInitials = "DK",
                        exerciseName = "Pull-ups",
                        userScore = 24,
                        opponentScore = 20,
                        isWin = true,
                        formScorePercent = 95,
                        paceSeconds = 2.1f,
                        timestampFormatted = "Sun • Online Set",
                        battleRule = "Most in 5 Min",
                        shareCode = "7M55"
                    )
                )
                dao.insertBattles(initialBattles)
            }
        }
    }
}
