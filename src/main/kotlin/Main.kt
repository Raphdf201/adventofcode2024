import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    val totalTime = measureTimeMillis {
        val tasks = listOf(
            "Day 1 Part 1" to { DayOne.partOne().toString() },
            "Day 1 Part 2" to { DayOne.partTwo().toString() },
            "Day 2 Part 1" to { DayTwo.partOne().toString() },
            "Day 2 Part 2" to { DayTwo.partTwo().toString() },
            "Day 3 Part 1" to { DayThree.partOne().toString() },
            "Day 3 Part 2" to { DayThree.partTwo().toString() },
            "Day 4 Part 1" to { DayFour.partOne().toString() },
            "Day 4 Part 2" to { DayFour.partTwo().toString() },
            "Day 5 Part 1" to { DayFive.partOne().toString() },
            "Day 5 Part 2" to { DayFive.partTwo().toString() },
            "Day 6 Part 1" to { DaySix.partOne().toString() },
            "Day 6 Part 2" to { DaySix.partTwo().toString() },
            "Day 7 Part 1" to { DaySeven.partOne().toString() },
            "Day 7 Part 2" to { DaySeven.partTwo().toString() },
            "Day 8 Part 1" to { DayEight.partOne().toString() },
            "Day 8 Part 2" to { DayEight.partTwo().toString() },
            "Day 9 Part 1" to { DayNine.partOne().toString() },
            "Day 9 Part 2" to { DayNine.partTwo().toString() },
            "Day 10 Part 1" to { DayTen.partOne().toString() },
            "Day 10 Part 2" to { DayTen.partTwo().toString() },
        )

        val results = tasks.map { (name, task) ->
            async(Dispatchers.Default) {
                val result: String
                val time = measureTimeMillis {
                    result = task()
                }
                Triple(name, result, time)
            }
        }.awaitAll()

        // Print results in order
        println("\n=== Results ===")
        results.forEach { (name, result, time) ->
            println("$name: $result (${time}ms)")
        }
    }

    println("\n=== Total Time ===")
    println("All tasks completed in: ${totalTime}ms")
}
