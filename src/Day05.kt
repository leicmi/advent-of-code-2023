// https://adventofcode.com/2023/day/5
fun main() {
    fun getMultiRangeMaps(lines: List<String>): List<MultiRangeMap> {
        val multiRangeMaps = mutableListOf<MultiRangeMap>()
        var i = 0
        while (i < lines.count()) {
            lines.drop(i + 1)
                .takeWhile { it.isNotEmpty() }
                .map(RangeMap::create)
                .let(::MultiRangeMap)
                .apply(multiRangeMaps::add)
            i += multiRangeMaps.last().rangeMaps.count() + 2
        }
        return multiRangeMaps
    }

    fun part1(input: List<String>): Long {
        val seeds = input[0]
            .split(": ")[1]
            .split(" ")
            .map(String::toLong)
        // get all the mappings
        val multiRangeMaps = getMultiRangeMaps(input.drop(2))
        // map the seeds to a location
        return seeds.map { multiRangeMaps.fold(it) { n, multiRangeMap -> multiRangeMap.map(n) } }.min()
    }


    fun part2(input: List<String>): Long {
        // get all the mappings
        val multiRangeMaps = getMultiRangeMaps(input.drop(2))
        // takes a few minutes, no parallelization up to now
        // If we would optimize the algorithm, we could try to map the ranges to other ranges,
        //   e.g. a long range is split into multiple ranges after the first map, and so on
        return input[0] // "seeds: 79 14 55 13"
            .split(": ")[1]
            .split(" ")
            .map(String::toLong)
            .chunked(2)
            .asSequence() // avoid out of memory exceptions by not materializing all the intermediary numbers and ranges
            .flatMap { LongRange(it[0], it[0] + it[1] - 1) } // these are the seeds
            // map the seeds to a location
            .minOf { multiRangeMaps.fold(it) { n, multiRangeMap -> multiRangeMap.map(n) } }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    part1(input).println() // 993500720
    part2(input).println() // 4917124
}

data class RangeMap(
    val destinationRangeStart: Long,
    val sourceRangeStart: Long,
    val rangeLength: Long,
) {
    val sourceRange = sourceRangeStart..<sourceRangeStart + rangeLength

    companion object {
        fun create(line: String): RangeMap =
            line.split(" ")
                .map { it.toLong() }
                .let { RangeMap(it[0], it[1], it[2]) }
    }
}

data class MultiRangeMap(
    val rangeMaps: List<RangeMap>
) {
    fun map(n: Long) = rangeMaps.filter { n in it.sourceRange }
        .map { n - it.sourceRangeStart + it.destinationRangeStart }
        .firstOrNull() ?: n
}
