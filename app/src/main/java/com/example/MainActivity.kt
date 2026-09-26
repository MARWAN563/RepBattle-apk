package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.data.local.BattleEntity
import com.example.data.local.RepBattleDatabase
import com.example.data.model.ExerciseType
import com.example.data.model.RuleType
import com.example.data.repository.BattleRepository
import com.example.ui.components.NavTab
import com.example.ui.components.RepBattleBottomBar
import com.example.ui.components.RepBattleStackHeader
import com.example.ui.components.RepBattleTabHeader
import com.example.ui.screens.BattlesScreen
import com.example.ui.screens.CreateBattleScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HomeArenaScreen
import com.example.ui.screens.LiveCameraBattleScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.VictoryResultScreen
import com.example.ui.theme.ArenaBackground
import com.example.ui.theme.RepBattleTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class ScreenDestination {
    data class MainTabs(val tab: NavTab = NavTab.HOME) : ScreenDestination()
    object CreateBattle : ScreenDestination()
    data class CameraBattle(
        val exerciseType: ExerciseType = ExerciseType.PUSH_UPS,
        val ruleType: RuleType = RuleType.FIRST_TO_50,
        val strictForm: Boolean = true,
        val ghostMode: Boolean = true
    ) : ScreenDestination()
    data class VictoryResult(
        val userScore: Int,
        val opponentScore: Int,
        val exerciseName: String
    ) : ScreenDestination()
}

class MainActivity : ComponentActivity() {

    private lateinit var database: RepBattleDatabase
    private lateinit var repository: BattleRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        database = RepBattleDatabase.getDatabase(applicationContext, lifecycleScope)
        repository = BattleRepository(database.battleDao())

        setContent {
            RepBattleTheme {
                RepBattleApp(repository = repository)
            }
        }
    }
}

@Composable
fun RepBattleApp(repository: BattleRepository) {
    var currentScreen by remember { mutableStateOf<ScreenDestination>(ScreenDestination.MainTabs(NavTab.HOME)) }
    var currentTab by remember { mutableStateOf(NavTab.HOME) }
    var showHtmlExplorer by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Handle Android system back button
    BackHandler {
        when (val screen = currentScreen) {
            is ScreenDestination.MainTabs -> {
                if (currentTab != NavTab.HOME) {
                    currentTab = NavTab.HOME
                }
            }
            is ScreenDestination.CreateBattle -> {
                currentScreen = ScreenDestination.MainTabs(currentTab)
            }
            is ScreenDestination.CameraBattle -> {
                currentScreen = ScreenDestination.CreateBattle
            }
            is ScreenDestination.VictoryResult -> {
                currentScreen = ScreenDestination.MainTabs(NavTab.HOME)
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(ArenaBackground),
        topBar = {
            when (val screen = currentScreen) {
                is ScreenDestination.MainTabs -> {
                    val title = when (currentTab) {
                        NavTab.HOME -> "Home"
                        NavTab.BATTLES -> "Battles"
                        NavTab.HISTORY -> "History"
                        NavTab.PROFILE -> "Profile"
                    }
                    RepBattleTabHeader(
                        title = title,
                        streakDays = 4,
                        onHtmlExplorerClick = {
                            showHtmlExplorer = true
                        },
                        onProfileClick = {
                            currentTab = NavTab.PROFILE
                            currentScreen = ScreenDestination.MainTabs(NavTab.PROFILE)
                        }
                    )
                }
                is ScreenDestination.CreateBattle -> {
                    RepBattleStackHeader(
                        title = "Create Battle",
                        onBackClick = {
                            currentScreen = ScreenDestination.MainTabs(currentTab)
                        }
                    )
                }
                is ScreenDestination.CameraBattle -> {
                    RepBattleStackHeader(
                        title = "Live Battle Tracking",
                        onBackClick = {
                            currentScreen = ScreenDestination.CreateBattle
                        }
                    )
                }
                is ScreenDestination.VictoryResult -> {
                    RepBattleStackHeader(
                        title = "Battle Result",
                        onBackClick = {
                            currentScreen = ScreenDestination.MainTabs(NavTab.HOME)
                        }
                    )
                }
            }
        },
        bottomBar = {
            if (currentScreen is ScreenDestination.MainTabs) {
                RepBattleBottomBar(
                    currentTab = currentTab,
                    onTabSelected = { tab ->
                        currentTab = tab
                        currentScreen = ScreenDestination.MainTabs(tab)
                    },
                    onChallengeClick = {
                        currentScreen = ScreenDestination.CreateBattle
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ArenaBackground)
                .padding(innerPadding)
        ) {
            when (val screen = currentScreen) {
                is ScreenDestination.MainTabs -> {
                    when (currentTab) {
                        NavTab.HOME -> {
                            HomeArenaScreen(
                                onContinueBattle = {
                                    currentScreen = ScreenDestination.CameraBattle(
                                        exerciseType = ExerciseType.PUSH_UPS,
                                        ruleType = RuleType.FIRST_TO_50
                                    )
                                },
                                onStartDuel = {
                                    currentScreen = ScreenDestination.CameraBattle(
                                        exerciseType = ExerciseType.DEEP_SQUATS,
                                        ruleType = RuleType.FIRST_TO_50
                                    )
                                },
                                onRematchRival = { rivalName ->
                                    currentScreen = ScreenDestination.CameraBattle(
                                        exerciseType = ExerciseType.PUSH_UPS,
                                        ruleType = RuleType.FIRST_TO_50
                                    )
                                }
                            )
                        }
                        NavTab.BATTLES -> {
                            BattlesScreen(
                                onCreateBattleClick = {
                                    currentScreen = ScreenDestination.CreateBattle
                                },
                                onJoinDuelClick = { rival ->
                                    currentScreen = ScreenDestination.CameraBattle(
                                        exerciseType = ExerciseType.PUSH_UPS,
                                        ruleType = RuleType.FIRST_TO_50
                                    )
                                }
                            )
                        }
                        NavTab.HISTORY -> {
                            HistoryScreen(repository = repository)
                        }
                        NavTab.PROFILE -> {
                            ProfileScreen(
                                repository = repository,
                                onRematchBattle = { rivalName ->
                                    currentScreen = ScreenDestination.CameraBattle(
                                        exerciseType = ExerciseType.PUSH_UPS,
                                        ruleType = RuleType.FIRST_TO_50
                                    )
                                }
                            )
                        }
                    }
                }
                is ScreenDestination.CreateBattle -> {
                    CreateBattleScreen(
                        onInitiateBattle = { exercise, rule, strict, ghost ->
                            currentScreen = ScreenDestination.CameraBattle(
                                exerciseType = exercise,
                                ruleType = rule,
                                strictForm = strict,
                                ghostMode = ghost
                            )
                        }
                    )
                }
                is ScreenDestination.CameraBattle -> {
                    LiveCameraBattleScreen(
                        exerciseType = screen.exerciseType,
                        ruleType = screen.ruleType,
                        strictForm = screen.strictForm,
                        ghostEnabled = screen.ghostMode,
                        onEndSet = { userScore, opponentScore, formScore, pace ->
                            // Persist to Room database
                            coroutineScope.launch(Dispatchers.IO) {
                                repository.insertBattle(
                                    BattleEntity(
                                        opponentName = "Adam Miller",
                                        opponentHandle = "@miller_a",
                                        opponentAvatarUrl = "",
                                        opponentInitials = "AM",
                                        exerciseName = screen.exerciseType.title,
                                        userScore = userScore,
                                        opponentScore = opponentScore,
                                        isWin = userScore >= opponentScore,
                                        formScorePercent = formScore,
                                        paceSeconds = pace,
                                        timestampFormatted = "Just now",
                                        battleRule = screen.ruleType.title,
                                        shareCode = "8X" + (10..99).random()
                                    )
                                )
                            }
                            currentScreen = ScreenDestination.VictoryResult(
                                userScore = userScore,
                                opponentScore = opponentScore,
                                exerciseName = screen.exerciseType.title
                            )
                        }
                    )
                }
                is ScreenDestination.VictoryResult -> {
                    VictoryResultScreen(
                        userScore = screen.userScore,
                        opponentScore = screen.opponentScore,
                        exerciseName = screen.exerciseName,
                        onRematchClick = {
                            currentScreen = ScreenDestination.CameraBattle(
                                exerciseType = ExerciseType.PUSH_UPS,
                                ruleType = RuleType.FIRST_TO_50
                            )
                        },
                        onNewBattleClick = {
                            currentScreen = ScreenDestination.CreateBattle
                        }
                    )
                }
            }
        }
    }

    if (showHtmlExplorer) {
        com.example.ui.components.HtmlMediaExplorerDialog(
            onDismiss = { showHtmlExplorer = false }
        )
    }
}
