import java.util.*

// https://adventofcode.com/2023/day/3
fun main() {
    fun part1(lines: List<String>): Int {
        val numbers = mutableListOf<Number>()
        val symbols = mutableListOf<Symbol>()
        lines.forEachIndexed { i, line ->
            Scanner(line).findAll("[0-9]+").forEach {
                val positions = mutableListOf<Position>()
                positions.addAll((it.start() - 1..it.end()).map { Position(it, i + 1) })
                positions.addAll((it.start() - 1..it.end()).map { Position(it, i) })
                positions.addAll((it.start() - 1..it.end()).map { Position(it, i - 1) })

                numbers.add(
                    Number(
                        it.group().toInt(),
                        positions
                    )
                )
            }
            Scanner(line).findAll("[^\\.0-9]").forEach {
                assert(it.start() == it.end())
                symbols.add(
                    Symbol(
                        it.group().first(),
                        Position(it.start(), i)
                    )
                )
            }
        }
        val validPositions = symbols.map { it.pos }.toSet()
        return numbers.filter { number ->
            number.pos.any { pos -> pos in validPositions }
        }.map { it.num }.sum()
    }

    fun part2(lines: List<String>): Int {
        val numbers = mutableListOf<Number>()
        val symbols = mutableListOf<Symbol>()
        lines.forEachIndexed { i, line ->
            Scanner(line).findAll("[0-9]+").forEach {
                val positions = mutableListOf<Position>()
                positions.addAll((it.start() - 1..it.end()).map { Position(it, i + 1) })
                positions.addAll((it.start() - 1..it.end()).map { Position(it, i) })
                positions.addAll((it.start() - 1..it.end()).map { Position(it, i - 1) })

                numbers.add(
                    Number(
                        it.group().toInt(),
                        positions
                    )
                )
            }
            Scanner(line).findAll("[\\*]").forEach {
                assert(it.start() == it.end())
                symbols.add(
                    Symbol(
                        it.group().first(),
                        Position(it.start(), i)
                    )
                )
            }
        }
        val starPosition = symbols.associateBy { it.pos }
        for (number in numbers) {
            for (pos in number.pos) {
                starPosition[pos]?.apply {
                    this.numbers.add(number)
                }
            }
        }

        return symbols.filter { it.numbers.count() == 2 }.map {
            val l = it.numbers.toList()
            l.first().num * l.last().num
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println() // 540025
    part2(input).println() // 84584891
}

data class Position(
    val x: Int,
    val y: Int
)

data class Symbol(val sym: Char, val pos: Position) {
    val numbers = mutableSetOf<Number>()
}

data class Number(val num: Int, val pos: List<Position>)
