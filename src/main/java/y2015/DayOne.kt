package y2015

class DayOne {
    companion object {
        fun partOne(): String {
            var l = 0
            dayOneInput.forEach {
                if (it == '(') l++
                else if (it == ')') l--
            }
            return l.toString()
        }

        fun partTwo(): String {
            var level = 0
            var r = 0
            dayOneInput.forEachIndexed { i, c ->
                if (c == '(') level++
                else if (c == ')') level--
                if (level < 0 && r == 0) r = i + 1
            }
            return r.toString()
        }
    }
}
