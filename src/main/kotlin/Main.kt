import y2015.DayTwo
import kotlin.system.measureTimeMillis

fun main() {
    var res: String
    val time = measureTimeMillis {
        res = DayTwo.partOne()
    }
    println("task finished in $time : $res")
}
