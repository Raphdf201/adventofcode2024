import y2024.Inputs

class DayNine {
    companion object {
        fun partOne(): Long {
            val blocks = mutableListOf<Int>()
            var fileId = 0
            var isFile = true

            for (char in Inputs.day9Disk) {
                val length = char.digitToInt()
                if (isFile) {
                    // Add file blocks with the current file ID
                    repeat(length) {
                        blocks.add(fileId)
                    }
                    fileId++
                } else {
                    // Add free space blocks (represented as -1)
                    repeat(length) {
                        blocks.add(-1)
                    }
                }
                isFile = !isFile
            }

            // Compact the disk by moving file blocks from the end to leftmost free space
            var leftPtr = 0
            var rightPtr = blocks.size - 1

            while (leftPtr < rightPtr) {
                // Find the next free space from the left
                while (leftPtr < blocks.size && blocks[leftPtr] != -1) {
                    leftPtr++
                }

                // Find the next file block from the right
                while (rightPtr >= 0 && blocks[rightPtr] == -1) {
                    rightPtr--
                }

                // If pointers haven't crossed, move the block
                if (leftPtr < rightPtr) {
                    blocks[leftPtr] = blocks[rightPtr]
                    blocks[rightPtr] = -1
                    leftPtr++
                    rightPtr--
                }
            }

            // Calculate the checksum
            var checksum = 0L
            for (i in blocks.indices) {
                if (blocks[i] != -1) {
                    checksum += i * blocks[i]
                }
            }

            return checksum
        }

        fun partTwo(): Long {
            val diskMap = Inputs.day9Disk.trim()

            // Parse the disk map into blocks
            val blocks = mutableListOf<Int>()
            var fileId = 0
            var isFile = true

            for (char in diskMap) {
                val length = char.digitToInt()
                if (isFile) {
                    repeat(length) {
                        blocks.add(fileId)
                    }
                    fileId++
                } else {
                    repeat(length) {
                        blocks.add(-1)
                    }
                }
                isFile = !isFile
            }

            val maxFileId = fileId - 1

            // Try to move each file, starting from the highest file ID
            for (currentFileId in maxFileId downTo 0) {
                // Find the position and size of the current file
                val fileStart = blocks.indexOfFirst { it == currentFileId }
                if (fileStart == -1) continue

                val fileEnd = blocks.indexOfLast { it == currentFileId }
                val fileSize = fileEnd - fileStart + 1

                // Find the leftmost span of free space that can fit this file
                var freeStart = -1
                var freeSize = 0

                for (i in 0 until fileStart) {
                    if (blocks[i] == -1) {
                        if (freeStart == -1) {
                            freeStart = i
                        }
                        freeSize++

                        // Check if we found a large enough span
                        if (freeSize >= fileSize) {
                            // Move the file
                            for (j in 0 until fileSize) {
                                blocks[freeStart + j] = currentFileId
                                blocks[fileStart + j] = -1
                            }
                            break
                        }
                    } else {
                        // Reset free space tracking
                        freeStart = -1
                        freeSize = 0
                    }
                }
            }

            // Calculate the checksum
            var checksum = 0L
            for (i in blocks.indices) {
                if (blocks[i] != -1) {
                    checksum += i * blocks[i]
                }
            }

            return checksum
        }
    }
}