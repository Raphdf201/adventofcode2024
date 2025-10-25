class DayNine {
    companion object {
        fun partOne() {
            val files = Inputs.day9Disk.filterIndexed { index, _ -> index % 2 == 0 }
            val free = Inputs.day9Disk.filterIndexed { index, _ -> index % 2 == 1 }
        }

        fun partTwo() {

        }
    }
}