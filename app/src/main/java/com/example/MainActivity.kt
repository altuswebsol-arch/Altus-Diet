package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.AltusDatabase
import com.example.data.AltusRepository
import com.example.data.MealItem
import com.example.data.WeightLog
import com.example.ui.AltusViewModel
import com.example.ui.theme.AltusSage
import com.example.ui.theme.AltusSlate
import com.example.ui.theme.MyApplicationTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object L10n {
    fun translate(key: String, lang: String): String {
        return when (lang) {
            "ur" -> when (key) {
                "nav_home" -> "پروفائل اور ٹریکر / होम"
                "nav_exercises" -> "ورزش کے معمولات / व्यायाम"
                "nav_profile" -> "پروفائل کی ترتیبات / प्रोफाइल"
                "nav_about" -> "کے بارے میں/ हमारे बारे में"
                "app_title" -> "آلٹس ڈائٹ پلانر"
                "good_morning" -> "صبح بخیر"
                "good_afternoon" -> "سہ پہر بخیر"
                "good_evening" -> "شام بخیر"
                "hydration_tracker" -> "پانی کا ٹریکر / पानी का ट्रैकर"
                "goal_label" -> "ہدف: 2.5 لیٹر / लक्ष्य: 2.5L"
                "add_glass" -> "پانی شامل کریں"
                "hydration_quote" -> "\"کامیابی صحت کے بہترین پانی سے شروع ہوتی ہے\""
                "hydration_title_success" -> "پانی کا ہدف مکمل!"
                "hydration_body_success" -> "آپ نے آج صحت بخش پانی پینے کا ہدف پورا کر لیا ہے۔"
                "budget_meal_title" -> "آج کا سستا کھانہ / आज का बजट भोजन"
                "day_indicator" -> "دن %d آف %d"
                "planner_title" -> "بجٹ کھانا پلانر / बजट प्लानर"
                "planner_desc" -> "روزانہ کے حساب سے صحت مند اور سستے گھر کے کھانے کے چارٹ۔"
                "reset_meals" -> "دوبارہ شروع کریں / रीसेट करें"
                "weekly_progress" -> "ہفتہ وار وزن ٹریکر / साप्ताहिक प्रगति"
                "weight_goal_label" -> "وزن کا ہدف: 62 کلو / लक्ष्य: 62.0kg"
                "log_weight_label" -> "نیا وزن درج کریں (کلو)"
                "log_weight_btn" -> "محفوظ کریں / लॉग करें"
                "no_data" -> "کوئی ڈیٹا دستیاب نہیں ہے۔"
                "exercise_companion" -> "ہلکی ورزش سیشن / व्यायाम साथी"
                "exercise_desc" -> "گھر پر کی جانے والی آسان اور سستی ورزشیں جو آپ کی صحت کو بہتر بنائیں۔"
                "steps_title" -> "طریقہ کار / चरण:"
                "duration" -> "وقت: %d منٹ / پندرہ سیکنڈ ہولڈ"
                "profile_settings" -> "ذاتی پروفائل ترتیبات / प्रोफाइल सेटिंग्स"
                "gender" -> "جنس / लिंग"
                "weight" -> "موجودہ وزن (کلو) / वज़न"
                "height" -> "قد (سینٹی میٹر) / लंबाई"
                "body_look" -> "جسمانی بناوٹ / शरीर का प्रकार"
                "clothes_size" -> "کپڑوں کا سائز / कपड़ों का आकार"
                "target_reduction" -> "کتنا وزن کم کرنا چاہتے ہیں (کلو)"
                "plan_duration" -> "ڈائٹ پلان کی مدت (دن) / योजना अवधि"
                "save_profile" -> "پروفائل محفوظ کریں / सहेजें"
                "personal_tip_title" -> "معمار صحت کی تجویز / अनुकूलित सलाह:"
                "tip_body" -> "وزن کم کرنے کے ہدف اور %s کلاتھ سائز کے حساب سے، سستے سبز گوبھی، ہری مرچیں، توری اور پالک متبادل استعمال کریں۔ روزانہ واک اور گہرے سانس کی مشق کریں۔"
                "developer_card" -> "چیف ویب معمار اور بانی / मुख्य वेब वास्तुकार"
                "about_desc" -> "آلٹس ڈائٹ ایک سستا، صحت مند اور قدرتی غذا کا منصوبہ فراہم کرتی ہے جو عام گھریلو اشیاء سے بنائی جا سکتی ہے۔"
                "co_founder" -> "آصف علی خان - بانی اور ویب آرکیٹیکٹ"
                "sub_btn" -> "سبزی تبدیل کریں / बदलिए"
                "sub_dialog_title" -> "سستی قدرتی متبادل سبزیاں"
                "sub_dialog_desc" -> "مہنگے پھلوں کی جگہ روزمرہ کی سستی اور فائدہ مند سبزیاں منتخب کریں:"
                else -> key
            }
            else -> when (key) {
                "nav_home" -> "Home / Tracker"
                "nav_exercises" -> "Exercises"
                "nav_profile" -> "Profile"
                "nav_about" -> "About"
                "app_title" -> "ALTUS DIET"
                "good_morning" -> "Good morning"
                "good_afternoon" -> "Good afternoon"
                "good_evening" -> "Good evening"
                "hydration_tracker" -> "Hydration Tracker"
                "goal_label" -> "Goal: 2.5L"
                "add_glass" -> "Add Glass"
                "hydration_quote" -> "\"Celebratory notification set for 2.5L\""
                "hydration_title_success" -> "HYDRATION COMPLETED"
                "hydration_body_success" -> "Aesthetic health requires pristine hydration. You reached your daily goal!"
                "budget_meal_title" -> "Today's Budget Meal"
                "day_indicator" -> "Day %d of %d"
                "planner_title" -> "7-DAY BUDGET PLANNER"
                "planner_desc" -> "Real-time cost trackers mapped to healthy nourishing ingredients."
                "reset_meals" -> "Reset Plan"
                "weekly_progress" -> "Weekly Progress"
                "weight_goal_label" -> "WEIGHT GOAL: 62.0KG"
                "log_weight_label" -> "Log weight (kg)"
                "log_weight_btn" -> "Log Weight"
                "no_data" -> "No weight entries recorded."
                "exercise_companion" -> "Light Exercise Companion"
                "exercise_desc" -> "Zero cost, light at-home physical routines with structural guides."
                "steps_title" -> "Steps:"
                "duration" -> "Duration: %d seconds / reps"
                "profile_settings" -> "User Profile Settings"
                "gender" -> "Gender"
                "weight" -> "Weight (kg)"
                "height" -> "Height (cm)"
                "body_look" -> "Body Silhouette"
                "clothes_size" -> "Clothing Size"
                "target_reduction" -> "Weight Loss Target (kg)"
                "plan_duration" -> "Plan Duration (Days)"
                "save_profile" -> "Update Profile Parameters"
                "personal_tip_title" -> "Architectural Diet Suggestion:"
                "tip_body" -> "Based on your goal, gender, and selected %s clothing size, we highly recommend focusing on cheap local green vegetable alternatives. Do not purchase costly imported fruits. Emphasize standing calf raises and seated breathing exercises from our companion plan to activate metabolic state."
                "developer_card" -> "Founder & Web Architect Profile"
                "about_desc" -> "Altus Diet introduces affordable organic food planners engineered around cheap, everyday household veggies to optimize physiological indices."
                "co_founder" -> "Asif Ali Khan - Co-Founder & Web Architect"
                "sub_btn" -> "Substitute Veggie"
                "sub_dialog_title" -> "Select Green Substitute"
                "sub_dialog_desc" -> "Choose from common, affordable green vegetables for dynamic substitution."
                else -> key
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val db = AltusDatabase.getDatabase(applicationContext)
                val repo = AltusRepository(db.altusDao())
                return AltusViewModel(repo) as T
            }
        })[AltusViewModel::class.java]

        setContent {
            MyApplicationTheme {
                AltusDashboardScreen(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AltusDashboardScreen(
    viewModel: AltusViewModel,
    modifier: Modifier = Modifier
) {
    // Collect Reactive StateFlows from ViewModel
    val waterState by viewModel.waterToday.collectAsStateWithLifecycle()
    val weightHistory by viewModel.weightHistory.collectAsStateWithLifecycle()
    val allMealItems by viewModel.allMealItems.collectAsStateWithLifecycle()
    val selectedDayIndex by viewModel.selectedDayIndex.collectAsStateWithLifecycle()
    val showCelebration by viewModel.showWaterCelebration.collectAsStateWithLifecycle()

    val lang by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val planDays by viewModel.planDurationDays.collectAsStateWithLifecycle()

    // Profile parameters
    val userGender by viewModel.userGender.collectAsStateWithLifecycle()
    val userWeight by viewModel.userWeight.collectAsStateWithLifecycle()
    val userHeight by viewModel.userHeight.collectAsStateWithLifecycle()
    val userBodyLook by viewModel.userBodyLook.collectAsStateWithLifecycle()
    val userClothSize by viewModel.userClothSize.collectAsStateWithLifecycle()
    val weightReductionGoalKg by viewModel.weightReductionGoalKg.collectAsStateWithLifecycle()

    var activeTab by rememberSaveable { mutableStateOf(0) }
    var showVegDialogForMeal by remember { mutableStateOf<MealItem?>(null) }

    Scaffold(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
            ) {
                NavigationBarItem(
                    selected = activeTab == 0,
                    onClick = { activeTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home Tracker") },
                    label = { Text(L10n.translate("nav_home", lang), fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        unselectedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.testTag("nav_home_tab")
                )
                NavigationBarItem(
                    selected = activeTab == 1,
                    onClick = { activeTab = 1 },
                    icon = { Icon(Icons.Default.Star, contentDescription = "Exercises Companion") },
                    label = { Text(L10n.translate("nav_exercises", lang), fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        unselectedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.testTag("nav_exercises_tab")
                )
                NavigationBarItem(
                    selected = activeTab == 2,
                    onClick = { activeTab = 2 },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile Settings") },
                    label = { Text(L10n.translate("nav_profile", lang), fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        unselectedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.testTag("nav_profile_tab")
                )
                NavigationBarItem(
                    selected = activeTab == 3,
                    onClick = { activeTab = 3 },
                    icon = { Icon(Icons.Default.Info, contentDescription = "About App") },
                    label = { Text(L10n.translate("nav_about", lang), fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        unselectedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.testTag("nav_about_tab")
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (activeTab) {
                0 -> TrackerTabContent(
                    viewModel = viewModel,
                    waterState = waterState,
                    weightHistory = weightHistory,
                    allMealItems = allMealItems,
                    selectedDayIndex = selectedDayIndex,
                    showCelebration = showCelebration,
                    lang = lang,
                    planDays = planDays,
                    onSubstituteClick = { showVegDialogForMeal = it }
                )
                1 -> ExercisesTabContent(lang = lang)
                2 -> ProfileTabContent(
                    viewModel = viewModel,
                    lang = lang,
                    planDays = planDays,
                    userGender = userGender,
                    userWeight = userWeight,
                    userHeight = userHeight,
                    userBodyLook = userBodyLook,
                    userClothSize = userClothSize,
                    weightReductionGoalKg = weightReductionGoalKg
                )
                3 -> AboutTabContent(lang = lang)
            }

            // Vegetable Substitution Dialog
            if (showVegDialogForMeal != null) {
                val targetMeal = showVegDialogForMeal!!
                AlertDialog(
                    onDismissRequest = { showVegDialogForMeal = null },
                    title = {
                        Text(
                            L10n.translate("sub_dialog_title", lang),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text(
                                L10n.translate("sub_dialog_desc", lang),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            
                            val vegOptions = listOf(
                                "Spinach" to "Spinach / پالک",
                                "Cabbage" to "Cabbage / بند گوبھی",
                                "Broccoli" to "Broccoli / بروکلی",
                                "Zucchini" to "Zucchini / توری",
                                "Cucumber" to "Cucumber / کھیرا",
                                "Green Beans" to "Green Beans / سبز پھلیاں",
                                "Peas" to "Peas / مٹر",
                                "Lettuce" to "Green Lettuce / ہری سلاد",
                                "Coriander & Mint" to "Coriander & Mint / دھنیا اور پودینہ"
                            )
                            
                            LazyColumn(
                                modifier = Modifier.heightIn(max = 240.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(vegOptions) { (enName, localizedLabel) ->
                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                viewModel.substituteMealVegetable(targetMeal, enName)
                                                showVegDialogForMeal = null
                                            }
                                            .testTag("sub_item_$enName")
                                    ) {
                                        Box(modifier = Modifier.padding(14.dp)) {
                                            Text(
                                                text = localizedLabel,
                                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showVegDialogForMeal = null }) {
                            Text("Cancel / منسوخ کریں", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackerTabContent(
    viewModel: AltusViewModel,
    waterState: com.example.data.WaterLog?,
    weightHistory: List<WeightLog>,
    allMealItems: List<com.example.data.MealItem>,
    selectedDayIndex: Int,
    showCelebration: Boolean,
    lang: String,
    planDays: Int,
    onSubstituteClick: (com.example.data.MealItem) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val mealsForSelectedDay = remember(allMealItems, selectedDayIndex) {
        allMealItems.filter { it.dayIndex == selectedDayIndex }
    }

    var weightInput by remember { mutableStateOf("") }
    var inputError by remember { mutableStateOf<String?>(null) }

    val waterTarget = 8
    val loggedGlasses = waterState?.glasses ?: 0
    val waterPercentage = (loggedGlasses.toFloat() / waterTarget).coerceAtMost(1.0f)

    val dayCompletionMap = remember(allMealItems) {
        val map = mutableMapOf<Int, Boolean>()
        for (day in 0..6) {
            val meals = allMealItems.filter { it.dayIndex == day }
            map[day] = meals.isNotEmpty() && meals.all { it.isCompleted }
        }
        map
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header with language selector
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
                    val baseGreetingKey = when (hour) {
                        in 0..11 -> "good_morning"
                        in 12..16 -> "good_afternoon"
                        else -> "good_evening"
                    }
                    val greeting = L10n.translate(baseGreetingKey, lang)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = L10n.translate("app_title", lang),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp
                            ),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "$greeting, Elena",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Light,
                                letterSpacing = (-0.5).sp
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Aesthetic Language Selector Pill
                    Row(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f), RoundedCornerShape(20.dp))
                            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.08f), RoundedCornerShape(20.dp))
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (lang == "en") MaterialTheme.colorScheme.primary else Color.Transparent)
                                .clickable { viewModel.updateLanguage("en") }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                .testTag("lang_en_btn"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "ENG",
                                color = if (lang == "en") Color.White else MaterialTheme.colorScheme.primary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (lang == "ur") MaterialTheme.colorScheme.primary else Color.Transparent)
                                .clickable { viewModel.updateLanguage("ur") }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                .testTag("lang_ur_btn"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "اردو/हिंदी",
                                color = if (lang == "ur") Color.White else MaterialTheme.colorScheme.primary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Hydration cylinder panel
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                            shape = RoundedCornerShape(32.dp)
                        )
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = L10n.translate("hydration_tracker", lang),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 0.5.sp
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(100.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = L10n.translate("goal_label", lang),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp
                                    ),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(130.dp)
                                    .background(Color(0xFFF1F4F1), shape = RoundedCornerShape(20.dp))
                                    .clip(RoundedCornerShape(20.dp))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(waterPercentage)
                                        .align(Alignment.BottomCenter)
                                        .background(MaterialTheme.colorScheme.primary)
                                        .padding(bottom = 12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (waterPercentage > 0.15f) {
                                        Text(
                                            text = "${"%.1f".format(loggedGlasses * 0.3125)}L",
                                            color = Color.White,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                if (waterPercentage <= 0.15f) {
                                    Text(
                                        text = "${"%.1f".format(loggedGlasses * 0.3125)}L",
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    IconButton(
                                        onClick = { viewModel.decrementWater() },
                                        modifier = Modifier
                                            .size(44.dp)
                                            .background(
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                                                RoundedCornerShape(12.dp)
                                            )
                                            .testTag("decrement_water_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Remove,
                                            contentDescription = "Remove Glass",
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                    Button(
                                        onClick = { viewModel.incrementWater() },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                            contentColor = Color.White
                                        ),
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(44.dp)
                                            .testTag("increment_water_button"),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Plus",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = L10n.translate("add_glass", lang),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    ),
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = L10n.translate("hydration_quote", lang),
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }

            // Today's Budget Meal Highlight
            item {
                val todayMeal = mealsForSelectedDay.firstOrNull() ?: com.example.data.MealItem(
                    dayIndex = selectedDayIndex,
                    mealType = "Breakfast",
                    name = "Spinach Scramble",
                    costEst = "$1.20",
                    isCompleted = false,
                    details = "Eggs scrambled with fresh baby spinach."
                )
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = L10n.translate("budget_meal_title", lang),
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Light,
                                        letterSpacing = 1.5.sp
                                    ),
                                    color = Color.White.copy(alpha = 0.7f)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = todayMeal.name,
                                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "${todayMeal.costEst}/pp",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color.White.copy(alpha = 0.2f))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(((selectedDayIndex + 1) / planDays.toFloat()).coerceAtMost(1.0f))
                                        .background(MaterialTheme.colorScheme.secondary)
                                )
                            }
                            Text(
                                text = L10n.translate("day_indicator", lang).format(selectedDayIndex + 1, planDays),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                ),
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }

            // Weight loss stats input
            item {
                val netDiff = if (weightHistory.size >= 2) {
                    weightHistory.last().weight - weightHistory.first().weight
                } else {
                    -1.4
                }
                val diffLabel = if (netDiff <= 0.0) {
                    "%.1f kg".format(netDiff)
                } else {
                    "+%.1f kg".format(netDiff)
                }
                val currentWeight = weightHistory.lastOrNull()?.weight ?: 64.2

                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = L10n.translate("weekly_progress", lang),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = diffLabel,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        AltusWeightChart(
                            logs = weightHistory,
                            onRemoveLog = { id -> viewModel.removeWeightEntry(id) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = L10n.translate("weight_goal_label", lang),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                ),
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = "%.1fkg".format(currentWeight),
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = weightInput,
                                onValueChange = {
                                    weightInput = it
                                    inputError = null
                                },
                                label = { Text(L10n.translate("log_weight_label", lang)) },
                                placeholder = { Text("e.g. 64.2") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                isError = inputError != null,
                                singleLine = true,
                                modifier = Modifier
                                    .weight(1.3f)
                                    .testTag("weight_input_field"),
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                    focusedContainerColor = Color.White.copy(alpha = 0.5f),
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.3f)
                                )
                            )

                            Button(
                                onClick = {
                                    val dWeight = weightInput.toDoubleOrNull()
                                    if (dWeight == null || dWeight <= 0.0) {
                                        inputError = "Invalid"
                                    } else {
                                        viewModel.addWeightEntry(dWeight)
                                        weightInput = ""
                                        keyboardController?.hide()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(56.dp)
                                    .testTag("log_weight_button")
                            ) {
                                Text(
                                    L10n.translate("log_weight_btn", lang),
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }
            }

            // Week planner selector and Meal logs list
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = L10n.translate("planner_title", lang),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )

                        IconButton(
                            onClick = { viewModel.resetMeals() },
                            modifier = Modifier.testTag("reset_meals_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reset",
                                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Text(
                        text = L10n.translate("planner_desc", lang),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Weekdays selection capsules mapped to plan days config
                    val dShort = listOf("M", "T", "W", "T", "F", "S", "S")
                    val visibleDays = dShort.take(planDays)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        visibleDays.forEachIndexed { index, name ->
                            val isSelected = selectedDayIndex == index
                            val completedAll = dayCompletionMap[index] == true

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(52.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        if (isSelected) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.06f),
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .clickable { viewModel.selectDay(index) }
                                    .testTag("day_tab_$index"),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = name,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.primary
                                    )
                                    if (completedAll) {
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .background(
                                                    if (isSelected) Color.White else MaterialTheme.colorScheme.secondary,
                                                    CircleShape
                                                )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // List of meals
            items(mealsForSelectedDay, key = { it.id }) { meal ->
                MealPlanItemView(
                    meal = meal,
                    lang = lang,
                    onToggleCompletion = { isChecked ->
                        viewModel.toggleMealCompletion(meal.id, isChecked)
                    },
                    onSubstituteClick = { onSubstituteClick(meal) }
                )
            }
        }

        // Celebratory floating banner
        AnimatedVisibility(
            visible = showCelebration,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .padding(horizontal = 20.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = L10n.translate("hydration_title_success", lang),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            ),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = L10n.translate("hydration_body_success", lang),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = { viewModel.dismissCelebration() },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary)
                    ) {
                        Text("DISMISS", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}

@Composable
fun ExercisesTabContent(lang: String) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 24.dp, bottom = 48.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Column {
                Text(
                    text = L10n.translate("exercise_companion", lang),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Light),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = L10n.translate("exercise_desc", lang),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Exercise 1: Shoulder & Back Stretch
        item {
            ExerciseCardView(
                title = if (lang == "ur") "کندھے اور کمر کی ورزش" else "Gentle Shoulder & Back Stretch",
                reps = "15-second holds",
                steps = listOf(
                    if (lang == "ur") "سیدھے کھڑے ہو جائیں اور اپنے ہاتھ ملائیں۔" else "Stand upright and interlock your fingers.",
                    if (lang == "ur") "اپنے ہاتھ اوپر اٹھائیں۔" else "Extend your arms straight above your head.",
                    if (lang == "ur") "کمر اور شانوں میں ہلکے کھچاؤ کو محسوس کریں۔" else "Feel the gentle pull in your shoulders and spine."
                ),
                illustration = {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val center = size.width / 2f
                        val height = size.height
                        drawCircle(color = AltusSage, radius = 8.dp.toPx(), center = Offset(center, height * 0.25f))
                        drawLine(color = AltusSlate, start = Offset(center, height * 0.35f), end = Offset(center, height * 0.65f), strokeWidth = 3.dp.toPx())
                        drawLine(color = AltusSlate, start = Offset(center, height * 0.4f), end = Offset(center - 20.dp.toPx(), height * 0.15f), strokeWidth = 3.dp.toPx())
                        drawLine(color = AltusSlate, start = Offset(center, height * 0.4f), end = Offset(center + 20.dp.toPx(), height * 0.15f), strokeWidth = 3.dp.toPx())
                        drawLine(color = AltusSlate, start = Offset(center, height * 0.65f), end = Offset(center - 12.dp.toPx(), height * 0.85f), strokeWidth = 3.dp.toPx())
                        drawLine(color = AltusSlate, start = Offset(center, height * 0.65f), end = Offset(center + 12.dp.toPx(), height * 0.85f), strokeWidth = 3.dp.toPx())
                    }
                },
                lang = lang
            )
        }

        // Exercise 2: Calf Raises
        item {
            ExerciseCardView(
                title = if (lang == "ur") "پنڈلیوں کی ورزش" else "Standing Calf Raises",
                reps = "15 repetitions",
                steps = listOf(
                    if (lang == "ur") "سہارے کے لیے دیوار یا کرسی کے قریب کھڑے ہوں۔" else "Stand near a wall or chair for light support.",
                    if (lang == "ur") "آہستہ آہستہ اپنے تلووں کے بل اوپر اٹھیں۔" else "Slowly raise your body on your tiptoes.",
                    if (lang == "ur") "اوپر پہنچ کر دو سیکنڈ ہولڈ کریں اور نیچے آئیں۔" else "Hold for 2 seconds, then slowly lower your heels."
                ),
                illustration = {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val center = size.width / 2f
                        val height = size.height
                        drawCircle(color = AltusSage, radius = 8.dp.toPx(), center = Offset(center, height * 0.22f))
                        drawLine(color = AltusSlate, start = Offset(center, height * 0.32f), end = Offset(center, height * 0.62f), strokeWidth = 3.dp.toPx())
                        drawLine(color = AltusSlate, start = Offset(center, height * 0.35f), end = Offset(center - 15.dp.toPx(), height * 0.48f), strokeWidth = 3.dp.toPx())
                        drawLine(color = AltusSlate, start = Offset(center, height * 0.35f), end = Offset(center + 15.dp.toPx(), height * 0.48f), strokeWidth = 3.dp.toPx())
                        drawLine(color = AltusSlate, start = Offset(center, height * 0.62f), end = Offset(center - 8.dp.toPx(), height * 0.8f), strokeWidth = 3.dp.toPx())
                        drawLine(color = AltusSlate, start = Offset(center, height * 0.62f), end = Offset(center + 8.dp.toPx(), height * 0.8f), strokeWidth = 3.dp.toPx())
                        drawLine(color = AltusSage, start = Offset(center - 30.dp.toPx(), height * 0.85f), end = Offset(center + 30.dp.toPx(), height * 0.85f), strokeWidth = 2.dp.toPx())
                    }
                },
                lang = lang
            )
        }

        // Exercise 3: Seated Breathing
        item {
            val infiniteTransition = rememberInfiniteTransition()
            val rippleScale by infiniteTransition.animateFloat(
                initialValue = 0.4f,
                targetValue = 0.95f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2500, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            ExerciseCardView(
                title = if (lang == "ur") "گہرے سانس کی ورزش" else "Seated Deep Breathing",
                reps = "2 minutes",
                steps = listOf(
                    if (lang == "ur") "کمر سیدھی کر کے آرام دہ پوزیشن میں بیٹھ جائیں۔" else "Sit comfortably with your spine straight.",
                    if (lang == "ur") "چار سیکنڈ تک ناک سے گہرا سانس لیں۔" else "Inhale deeply through your nose for 4 seconds.",
                    if (lang == "ur") "چھ سیکنڈ تک منہ سے آہستہ آہستہ سانس باہر نکالیں۔" else "Exhale slowly through your mouth for 6 seconds."
                ),
                illustration = {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val centerPt = Offset(size.width / 2f, size.height / 2f)
                        drawCircle(color = AltusSage.copy(alpha = 0.15f), radius = (35.dp.toPx() * rippleScale), center = centerPt)
                        drawCircle(color = AltusSage.copy(alpha = 0.3f), radius = (25.dp.toPx() * rippleScale), center = centerPt)
                        drawCircle(color = AltusSlate, radius = 10.dp.toPx(), center = centerPt)
                    }
                },
                lang = lang
            )
        }

        // Exercise 4: Knee Bends
        item {
            ExerciseCardView(
                title = if (lang == "ur") "ہلکی زانو خم ورزش" else "Gentle Knee Bends",
                reps = "10 repetitions",
                steps = listOf(
                    if (lang == "ur") "پاؤں چوڑے کر کے آرام دہ کھڑے ہو جائیں۔" else "Stand comfortably with your feet shoulder-width.",
                    if (lang == "ur") "اپنے گھٹنے تھوڑے موڑیں (منی سکواٹ)۔" else "Bend your knees slightly into a half-squat.",
                    if (lang == "ur") "ایک سیکنڈ کے لیے رکیں اور واپس اوپر آئیں۔" else "Hold for 1 second, then extend back upright."
                ),
                illustration = {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val center = size.width / 2f
                        val height = size.height
                        drawCircle(color = AltusSage, radius = 8.dp.toPx(), center = Offset(center - 8.dp.toPx(), height * 0.35f))
                        drawLine(color = AltusSlate, start = Offset(center - 8.dp.toPx(), height * 0.42f), end = Offset(center, height * 0.65f), strokeWidth = 3.dp.toPx())
                        drawLine(color = AltusSlate, start = Offset(center - 8.dp.toPx(), height * 0.45f), end = Offset(center + 20.dp.toPx(), height * 0.45f), strokeWidth = 3.dp.toPx())
                        drawLine(color = AltusSlate, start = Offset(center, height * 0.65f), end = Offset(center + 12.dp.toPx(), height * 0.72f), strokeWidth = 3.dp.toPx())
                        drawLine(color = AltusSlate, start = Offset(center + 12.dp.toPx(), height * 0.72f), end = Offset(center - 5.dp.toPx(), height * 0.9f), strokeWidth = 3.dp.toPx())
                    }
                },
                lang = lang
            )
        }
    }
}

@Composable
fun ExerciseCardView(
    title: String,
    reps: String,
    steps: List<String>,
    illustration: @Composable () -> Unit,
    lang: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.06f), RoundedCornerShape(24.dp))
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                illustration()
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = reps,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = L10n.translate("steps_title", lang),
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                steps.forEachIndexed { i, step ->
                    Text(
                        text = "${i + 1}. $step",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTabContent(
    viewModel: AltusViewModel,
    lang: String,
    planDays: Int,
    userGender: String,
    userWeight: Double,
    userHeight: Int,
    userBodyLook: String,
    userClothSize: String,
    weightReductionGoalKg: Double
) {
    var genderInput by remember { mutableStateOf(userGender) }
    var weightInput by remember { mutableStateOf(userWeight.toString()) }
    var heightInput by remember { mutableStateOf(userHeight.toString()) }
    var bodyLookInput by remember { mutableStateOf(userBodyLook) }
    var clothSizeInput by remember { mutableStateOf(userClothSize) }
    var goalInput by remember { mutableStateOf(weightReductionGoalKg.toString()) }
    var sliderValue by remember { mutableStateOf(planDays.toFloat()) }

    val personalizedAdvice = remember(lang, userGender, userWeight, userHeight, userBodyLook, userClothSize, weightReductionGoalKg) {
        if (userGender.equals("Female", ignoreCase = true)) {
            val calculatedDeficit = (weightReductionGoalKg * 7700 / 30).toInt().coerceIn(300, 750)
            val targetWeight = (userWeight - weightReductionGoalKg).coerceAtLeast(40.0)
            when (lang) {
                "ur" -> "آپ کے ہدف (موجودہ وزن %.1f کلو، ہدف کم کرنے %.1f کلو، نشانہ %.1f کلو) اور کپڑوں کے سائز (%s) کے مطابق خواتین کے لیے خصوصی مشورہ: روزانہ %d کلیوری کا خسارہ رکھیں۔ پانی پینے کی شرح 2.5L برقرار رکھیں اور مہنگے غیر ملکی پھلوں کی نسبت گھر پر موجود سستی اور عام سبز گوبھی، ہری توری، پالک اور کچے کھیرے کا متبادل ڈائٹ استعمال کریں۔".format(userWeight, weightReductionGoalKg, targetWeight, userClothSize, calculatedDeficit)
                else -> "Personalized Female Diet Plan: To reduce %.1f kg from your current %.1f kg (Target weight: %.1f kg, Clothing Size: %s), you should target an active diet deficit of %d kcal daily. Completely avoid costly imported fruits. Emphasize extremely affordable local ingredients like sliced cucumber, steamed cabbage, and fresh local spinach leaves. Pair this budget diet with the calf raise and seated breathing routines."
                    .format(weightReductionGoalKg, userWeight, targetWeight, userClothSize, calculatedDeficit)
            }
        } else {
            L10n.translate("tip_body", lang).format(userClothSize)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 24.dp, bottom = 48.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                text = L10n.translate("profile_settings", lang),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Light),
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Suggestion Card based on user goal & cloth size
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = L10n.translate("personal_tip_title", lang),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp),
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = personalizedAdvice,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }
        }

        // Gender row
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = L10n.translate("gender", lang),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Female", "Male").forEach { g ->
                        val isSelected = genderInput.equals(g, ignoreCase = true)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                                .border(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.06f), RoundedCornerShape(16.dp))
                                .clickable { genderInput = g }
                                .testTag("gender_opt_$g"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = g,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }

        // Height and Weight Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = weightInput,
                    onValueChange = { weightInput = it },
                    label = { Text(L10n.translate("weight", lang)) },
                    singleLine = true,
                    modifier = Modifier.weight(1f).testTag("profile_weight_input"),
                    shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                    value = heightInput,
                    onValueChange = { heightInput = it },
                    label = { Text(L10n.translate("height", lang)) },
                    singleLine = true,
                    modifier = Modifier.weight(1f).testTag("profile_height_input"),
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }

        // Silhouette and size
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = bodyLookInput,
                    onValueChange = { bodyLookInput = it },
                    label = { Text(L10n.translate("body_look", lang)) },
                    singleLine = true,
                    modifier = Modifier.weight(1f).testTag("profile_look_input"),
                    shape = RoundedCornerShape(16.dp)
                )

                OutlinedTextField(
                    value = clothSizeInput,
                    onValueChange = { clothSizeInput = it },
                    label = { Text(L10n.translate("clothes_size", lang)) },
                    singleLine = true,
                    modifier = Modifier.weight(1f).testTag("profile_size_input"),
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }

        // Loss Target & Duration slider
        item {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = goalInput,
                    onValueChange = { goalInput = it },
                    label = { Text(L10n.translate("target_reduction", lang)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("profile_goal_input"),
                    shape = RoundedCornerShape(16.dp)
                )

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = L10n.translate("plan_duration", lang),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${sliderValue.toInt()} Days",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Slider(
                        value = sliderValue,
                        onValueChange = { newVal ->
                            val rounded = when {
                                newVal < 4.0f -> 3.0f
                                newVal < 6.0f -> 5.0f
                                else -> 7.0f
                            }
                            sliderValue = rounded
                        },
                        valueRange = 3f..7f,
                        steps = 2,
                        modifier = Modifier.testTag("plan_duration_slider")
                    )
                }
            }
        }

        // Save actions
        item {
            Button(
                onClick = {
                    val w = weightInput.toDoubleOrNull() ?: 64.2
                    val h = heightInput.toIntOrNull() ?: 165
                    val g = goalInput.toDoubleOrNull() ?: 5.0
                    viewModel.updateProfile(
                        gender = genderInput,
                        weight = w,
                        height = h,
                        bodyLook = bodyLookInput,
                        clothSize = clothSizeInput,
                        goal = g
                    )
                    viewModel.updatePlanDuration(sliderValue.toInt())
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .testTag("save_profile_button")
            ) {
                Text(
                    L10n.translate("save_profile", lang),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun AboutTabContent(lang: String) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        contentPadding = PaddingValues(top = 24.dp, bottom = 48.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Column {
                Text(
                    text = L10n.translate("nav_about", lang),
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Light),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = L10n.translate("about_desc", lang),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Founder credit panel - requested "Asif Ali Khan Co-Founder & Web Architect at www.Altuswebsolutions.com | info@altuswebsolutions.com"
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.08f), RoundedCornerShape(32.dp))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = L10n.translate("developer_card", lang),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Asif Ali Khan",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Co-Founder & Web Architect",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.tertiary
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 16.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Website",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "www.Altuswebsolutions.com",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "info@altuswebsolutions.com",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MealPlanItemView(
    meal: com.example.data.MealItem,
    lang: String,
    onToggleCompletion: (Boolean) -> Unit,
    onSubstituteClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (meal.isCompleted) MaterialTheme.colorScheme.secondary.copy(alpha = 0.06f) else MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (meal.isCompleted) MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.06f),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.Top
        ) {
            IconButton(
                onClick = { onToggleCompletion(!meal.isCompleted) },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .testTag("meal_toggle_${meal.id}")
            ) {
                Icon(
                    imageVector = if (meal.isCompleted) Icons.Default.CheckCircle else Icons.Outlined.CheckCircleOutline,
                    contentDescription = "Check off meal",
                    tint = if (meal.isCompleted) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary.copy(alpha = 0.35f),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = meal.mealType.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp
                        ),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = meal.costEst,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = meal.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        textDecoration = if (meal.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                    ),
                    color = if (meal.isCompleted) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = meal.details,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (meal.isCompleted) MaterialTheme.colorScheme.primary.copy(alpha = 0.4f) else MaterialTheme.colorScheme.tertiary
                )

                Button(
                    onClick = onSubstituteClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .height(34.dp)
                        .testTag("substitute_meal_veggie_${meal.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = "Substitute ingredient",
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = L10n.translate("sub_btn", lang),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun AltusWeightChart(
    logs: List<WeightLog>,
    onRemoveLog: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    if (logs.isEmpty()) {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.03f), shape = RoundedCornerShape(8.dp))
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.05f), shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "No data indicator",
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "No weight entries recorded.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
        return
    }

    // Sort logs chronologically to draw the graph smoothly
    val sortedLogs = remember(logs) { logs.sortedBy { it.date } }

    val maxWeight = (sortedLogs.maxOfOrNull { it.weight } ?: 70.0) + 1.5
    val minWeight = (sortedLogs.minOfOrNull { it.weight } ?: 60.0) - 1.5
    val range = (maxWeight - minWeight).coerceAtLeast(1.0)

    val dateFormat = remember { SimpleDateFormat("MM/dd", Locale.getDefault()) }

    Column(modifier = modifier) {
        // High-fidelity custom graph rendering
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height
                val padLeft = 46.dp.toPx()
                val padRight = 16.dp.toPx()
                val padTop = 16.dp.toPx()
                val padBottom = 20.dp.toPx()
                
                val usableWidth = width - padLeft - padRight
                val usableHeight = height - padTop - padBottom

                // Draw Grid baseline rules
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.15f),
                    start = Offset(padLeft, padTop),
                    end = Offset(padLeft, height - padBottom),
                    strokeWidth = 1.dp.toPx()
                )
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.15f),
                    start = Offset(padLeft, height - padBottom),
                    end = Offset(width - padRight, height - padBottom),
                    strokeWidth = 1.dp.toPx()
                )

                // Render lines if there are multiple entries
                if (sortedLogs.size >= 2) {
                    val points = sortedLogs.mapIndexed { i, log ->
                        val x = padLeft + (i.toFloat() / (sortedLogs.size - 1)) * usableWidth
                        val ratio = (log.weight - minWeight) / range
                        val y = height - padBottom - (ratio.toFloat()* usableHeight)
                        Offset(x, y)
                    }

                    val baseCurve = Path().apply {
                        moveTo(points.first().x, points.first().y)
                        for (i in 1 until points.size) {
                            val prev = points[i - 1]
                            val curr = points[i]
                            // Cubic splines to produce smooth architectural curves
                            cubicTo(
                                (prev.x + curr.x) / 2f, prev.y,
                                (prev.x + curr.x) / 2f, curr.y,
                                curr.x, curr.y
                            )
                        }
                    }

                    // Stroke line
                    drawPath(
                        path = baseCurve,
                        color = AltusSage,
                        style = Stroke(width = 2.dp.toPx())
                    )

                    // Soft background gradient below graph curve
                    val fillPath = Path().apply {
                        addPath(baseCurve)
                        lineTo(points.last().x, height - padBottom)
                        lineTo(points.first().x, height - padBottom)
                        close()
                    }
                    drawPath(
                        path = fillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(AltusSage.copy(alpha = 0.18f), Color.Transparent)
                        )
                    )

                    // Plot distinct markers
                    points.forEach { pt ->
                        drawCircle(
                            color = AltusSlate,
                            radius = 4.dp.toPx(),
                            center = pt
                        )
                        drawCircle(
                            color = Color.White,
                            radius = 1.5.dp.toPx(),
                            center = pt
                        )
                    }
                } else if (sortedLogs.size == 1) {
                    // Single-point baseline
                    val x = padLeft + usableWidth / 2f
                    val y = padTop + usableHeight / 2f
                    drawCircle(color = AltusSage, radius = 5.dp.toPx(), center = Offset(x, y))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Horizontal list of logs to allow individual interactive deletions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            sortedLogs.forEach { log ->
                val fDate = dateFormat.format(Date(log.date))
                Row(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.04f),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .clickable { onRemoveLog(log.id) }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .testTag("weight_log_item_${log.id}"),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${log.weight} kg",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = fDate,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Remove weight Entry",
                        tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f),
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}
