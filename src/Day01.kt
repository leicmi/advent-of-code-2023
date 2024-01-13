// https://adventofcode.com/2023/day/1
fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val first = line.first { it.isDigit() }.digitToInt()
            val last = line.last { it.isDigit() }.digitToInt()
            first * 10 + last
        }
    }

    fun part2(input: List<String>): Int {
        // instead of looking at all occurences with regex/pattern matching,
        // we replace all occurences with something that is easily parsed.
        // Note that there might be overlaps, which is why the number is
        // not replaced as a whole and why the number is not at the beginning/end.
        return input.sumOf { line ->
            val l = line
                .replace("one", "on1e")
                .replace("two", "t2wo")
                .replace("three", "t3hree")
                .replace("four", "f4our")
                .replace("five", "f5ive")
                .replace("six", "s6ix")
                .replace("seven", "s7even")
                .replace("eight", "e8ight")
                .replace("nine", "n9ine")
            val first = l.first { it.isDigit() }.digitToInt()
            val last = l.last { it.isDigit() }.digitToInt()
            first * 10 + last
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println() // 54597
    part2(input).println() // 54504
}
