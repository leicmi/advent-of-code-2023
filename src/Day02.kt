// https://adventofcode.com/2023/day/2
fun main() {
    fun part1(input: List<String>, nRed: Int, nGreen: Int, nBlue: Int): Int {
        val games = input.map(::createGame)
        val validGames = games.filterNot {
            it.sets.any {
                (it.cubes["red"] ?: 0) > nRed ||
                        (it.cubes["green"] ?: 0) > nGreen ||
                        (it.cubes["blue"] ?: 0) > nBlue
            }
        }
        return validGames.sumOf { it.id }
    }

    fun part2(input: List<String>): Int = input.map(::createGame)
        .map { game ->
            1 *
                    game.sets.map { it.cubes["blue"] ?: 0 }.max() *
                    game.sets.map { it.cubes["red"] ?: 0 }.max() *
                    game.sets.map { it.cubes["green"] ?: 0 }.max()
        }.sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput, 12, 13, 14) == 8)

    val input = readInput("Day02")
    part1(input, 12, 13, 14).println() // 2101
    part2(input).println() // 58269
}

data class Game(val id: Int, val sets: List<GamesSet>)
data class GamesSet(val cubes: Map<String, Int>)

fun createGame(text: String): Game {
    val (game, rest) = text.split(": ")
    val id = game.substring(5).toInt()
    val sets = rest.split("; ").map(::createGameSet)
    return Game(id, sets)
}

fun createGameSet(text: String): GamesSet {
    return GamesSet(text.split(", ").associate {
        val (num, col) = it.split(" ")
        col to num.toInt()
    })
}

