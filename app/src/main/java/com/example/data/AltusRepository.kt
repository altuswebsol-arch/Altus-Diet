package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AltusRepository(private val dao: AltusDao) {

    // Flows for observing database state
    fun getWaterLog(date: String): Flow<WaterLog?> = dao.getWaterLog(date)
    
    val allWeightLogs: Flow<List<WeightLog>> = dao.getAllWeightLogs()
    
    val allMealItems: Flow<List<MealItem>> = dao.getAllMealItems()

    fun getMealItemsForDay(dayIndex: Int): Flow<List<MealItem>> = dao.getMealItemsForDay(dayIndex)

    // Suspend operations for inserting/updating tracking state
    suspend fun logWater(date: String, glasses: Int) {
        dao.insertWaterLog(WaterLog(date, glasses))
    }

    suspend fun addWeightLog(weight: Double, timestamp: Long) {
        dao.insertWeightLog(WeightLog(date = timestamp, weight = weight))
    }

    suspend fun deleteWeightLog(id: Long) {
        dao.deleteWeightLogById(id)
    }

    suspend fun updateMealItemCompletion(id: Long, isCompleted: Boolean) {
        dao.updateMealItemCompletion(id, isCompleted)
    }

    suspend fun resetAllMeals() {
        val currentMeals = dao.getAllMealItems().first()
        val reseted = currentMeals.map { it.copy(isCompleted = false) }
        dao.insertMealItems(reseted)
    }

    suspend fun insertMealItems(mealItems: List<MealItem>) {
        dao.insertMealItems(mealItems)
    }

    // Seed the database with the default Altus Standards 7-day budget meals
    suspend fun seedMealsIfEmpty() {
        val existing = dao.getAllMealItems().first()
        if (existing.isEmpty()) {
            val defaultMeals = createDefaultMealPlan()
            dao.insertMealItems(defaultMeals)
        }
    }

    private fun createDefaultMealPlan(): List<MealItem> {
        return listOf(
            // Day 0: Monday
            MealItem(dayIndex = 0, mealType = "Breakfast", name = "Spinach & Herb Scramble", costEst = "$1.20", details = "2 white eggs or tofu scrambled with 1 cup fresh spinach, fresh thyme, and sea salt."),
            MealItem(dayIndex = 0, mealType = "Lunch", name = "Broccoli & Celery Detox Bowl", costEst = "$1.50", details = "Steamed broccoli florets, sliced celery stalks, cucumber rounds, dressed with lemon juice."),
            MealItem(dayIndex = 0, mealType = "Dinner", name = "Garlic Sauteed Kale & Beans", costEst = "$1.80", details = "Lacinato kale sauteed with minced garlic, olive oil, and 1/2 cup cooked local white beans."),
            MealItem(dayIndex = 0, mealType = "Snack", name = "Cucumber Slices & Sea Salt", costEst = "$0.40", details = "1 whole cucumber sliced, seasoned with mineral sea salt and lemon juice."),

            // Day 1: Tuesday
            MealItem(dayIndex = 1, mealType = "Breakfast", name = "Vibrant Green Oat Smoothie", costEst = "$1.30", details = "Blended spinach, 1/4 cup whole oats, chilled water, 1/2 green cucumber, and flax seeds."),
            MealItem(dayIndex = 1, mealType = "Lunch", name = "Crisp Cabbage Steaks", costEst = "$1.10", details = "Thick green cabbage slices baked with sea salt, ground pepper, and rosemary sprigs."),
            MealItem(dayIndex = 1, mealType = "Dinner", name = "Roasted Broccoli & Cauliflower Wok", costEst = "$1.60", details = "Oven-roasted broccoli and organic local barley grains skillet with low-sodium soy sauce."),
            MealItem(dayIndex = 1, mealType = "Snack", name = "Steamed Edamame Shells", costEst = "$0.70", details = "Lightly boiled green soybean pods with coarse sea salt."),

            // Day 2: Wednesday
            MealItem(dayIndex = 2, mealType = "Breakfast", name = "Avocado & Spinach Open toast", costEst = "$1.40", details = "1 slice local whole-wheat toast topped with mashed avocado, spinach leaves, and red pepper flakes."),
            MealItem(dayIndex = 2, mealType = "Lunch", name = "Sage-infused Fennel & Celery Soup", costEst = "$1.20", details = "Finely chopped fennel bulbs, leek, and celery simmered in herbal green broth."),
            MealItem(dayIndex = 2, mealType = "Dinner", name = "Peas, Zucchini & Barley Stir-fry", costEst = "$1.70", details = "Whole barley pan-stirred with cubed zucchini, fresh green peas, and fresh dill."),
            MealItem(dayIndex = 2, mealType = "Snack", name = "Minted Cucumber Rounds", costEst = "$0.40", details = "Slices of chilled cucumber tossed with finely chopped mint and apple cider vinegar."),

            // Day 3: Thursday
            MealItem(dayIndex = 3, mealType = "Breakfast", name = "Double Green Herb Omelet", costEst = "$1.30", details = "Eggs whisked with fresh dill, parsley, and finely chopped kale leaves."),
            MealItem(dayIndex = 3, mealType = "Lunch", name = "Earthy Kale & Zucchini Ribbon Salad", costEst = "$1.50", details = "Zucchini shaved into ribbons, massaged kale, seasoned with olive oil and coarse salt."),
            MealItem(dayIndex = 3, mealType = "Dinner", name = "Broccoli-Cabbage Slow Soup", costEst = "$1.60", details = "Creamy puréed soup of boiled broccoli, green cabbage, potato base, and mineral sea salt."),
            MealItem(dayIndex = 3, mealType = "Snack", name = "Celery Logs with Pumpkin Seeds", costEst = "$0.85", details = "Celery ribs filled with a touch of unsweetened seed butter, topped with raw pumpkin seeds."),

            // Day 4: Friday
            MealItem(dayIndex = 4, mealType = "Breakfast", name = "Savory Spinach Breakfast Roll", costEst = "$1.20", details = "Whole-wheat flat wrap filled with scrambled soy/beans and raw chopped baby spinach."),
            MealItem(dayIndex = 4, mealType = "Lunch", name = "Pureed Garden Broccoli Bisque", costEst = "$1.40", details = "Silky broccoli broth blended with cooked potato, onion, and herbs."),
            MealItem(dayIndex = 4, mealType = "Dinner", name = "Baked Tofu & Green Wok", costEst = "$1.80", details = "Firm local tofu cubed and wok-fried with broccoli florets, baby bok choy, and ginger."),
            MealItem(dayIndex = 4, mealType = "Snack", name = "Chilled Cucumber Mint Pops", costEst = "$0.50", details = "Puréed fresh cucumber-mint water frozen in clean architectural stick molds."),

            // Day 5: Saturday
            MealItem(dayIndex = 5, mealType = "Breakfast", name = "Spinach & Oat Savory Skillet", costEst = "$1.10", details = "Pan-cooked savory pancakes made of oat flour, blended spinach leaves, and salt."),
            MealItem(dayIndex = 5, mealType = "Lunch", name = "Herbal Green Crunch Combo", costEst = "$1.60", details = "Finely shredded romaine lettuce, cucumbers, broccoli cubes, dressed with sunflower seed oil."),
            MealItem(dayIndex = 5, mealType = "Dinner", name = "Rosemary Pinto Beans & Greens", costEst = "$1.50", details = "Local pinto beans stewed with whole rosemary stems, served over steamed field greens."),
            MealItem(dayIndex = 5, mealType = "Snack", name = "Raw Celery Sprigs", costEst = "$0.40", details = "Chilled crisp celery sticks with a splash of fresh lemon squeeze."),

            // Day 6: Sunday
            MealItem(dayIndex = 6, mealType = "Breakfast", name = "Steamed Power Greens Medley", costEst = "$1.35", details = "Kale, spinach, broccoli florets steam-cooked to bright green, with raw sesame sprinkle."),
            MealItem(dayIndex = 6, mealType = "Lunch", name = "Chunky cucumber Gazpacho & Rye", costEst = "$1.10", details = "Cold raw gazpacho of pureed cucumbers, garlic, bell pepper, mint, with 1 slice local rye toast."),
            MealItem(dayIndex = 6, mealType = "Dinner", name = "Lentil & Diced Zucchini Skillet", costEst = "$1.90", details = "Brown lentils simmered with cubed zucchini, onions, bay leaves, and dark green collard ribbons."),
            MealItem(dayIndex = 6, mealType = "Snack", name = "Raw Broccoli Vinaigrette Bites", costEst = "$0.50", details = "Crunchy raw broccoli florets dipped in home-made mustard-vinegar emulsion.")
        )
    }
}
