package y2015

class DayTwo {
    companion object {
        fun partOne(): String {
            var total = 0
            dayTwoInput.split("\n").drop(1).dropLast(1).forEachIndexed { i, it ->
                val sides = it.split("x")
                val l = sides[0].toInt()
                val w = sides[1].toInt()
                val h = sides[2].toInt()
                val smallest = minOf(l, w, h)
                val secondSmallest = when (smallest) {
                    l -> minOf(w, h)
                    w -> minOf(l, h)
                    else -> minOf(l, w)
                }
                total += (2 * l * w) + (2 * w * h) + (2 * h * l) + (smallest * secondSmallest)
            }
            return total.toString()
        }


    }
}