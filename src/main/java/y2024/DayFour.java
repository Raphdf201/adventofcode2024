package y2024;

public class DayFour {
    public static final String[] day4XmasArr = Inputs.day4Xmas.split("\n");

    /**
     * <p>"Looks like the Chief's not here. Next!" One of The Historians pulls out a device and pushes the only button on it. After a brief flash, you recognize the interior of the <a href="https://adventofcode.com/2019/day/10">Ceres monitoring station</a>!</p>
     * <p>As the search for the Chief continues, a small Elf who lives on the station tugs on your shirt; she'd like to know if you could help her with her <em>word search</em> (your puzzle input). She only has to find one word: <code>XMAS</code>.</p>
     * <p>This word search allows words to be horizontal, vertical, diagonal, written backwards, or even overlapping other words. It's a little unusual, though, as you don't merely need to find one instance of <code>XMAS</code> - you need to find <em>all of them</em>. Here are a few ways <code>XMAS</code> might appear, where irrelevant characters have been replaced with <code>.</code>:<p>
     * <pre><code>
     * ..X...
     * .SAMX.
     * .A..A.
     * XMAS.S
     * .X....
     * </code></pre>
     * <p>The actual word search will be full of letters instead. For example:</p>
     * <pre><code>
     * MMMSXXMASM
     * MSAMXMSMSA
     * AMXSXMAAMM
     * MSAMASMSMX
     * XMASAMXAMM
     * XXAMMXXAMA
     * SMSMSASXSS
     * SAXAMASAAA
     * MAMMMXMMMM
     * MXMXAXMASX
     * </code></pre>
     * <p>In this word search, <code>XMAS</code> occurs a total of <code><em>18</em></code> times; here's the same word search again, but where letters not involved in any <code>XMAS</code> have been replaced with <code>.</code>:</p>
     * <pre><code>
     * ....XXMAS.
     * .SAMXMS...
     * ...S..A...
     * ..A.A.MS.X
     * XMASAMX.MM
     * X.....XA.A
     * S.S.S.S.SS
     * .A.A.A.A.A
     * ..M.M.M.MM
     * .X.X.XMASX
     * </code></pre>
     * <p>Take a look at the little Elf's word search. <strong>How many times does <code>XMAS</code> appear?</strong></p>
     */
    public static int partOne() {
        int rows = DayFour.day4XmasArr.length;
        int cols = DayFour.day4XmasArr[0].length();
        int count = 0;

        int[][] directions = {
                {0, 1},
                {0, -1},
                {1, 0},
                {-1, 0},
                {1, 1},
                {-1, -1},
                {1, -1},
                {-1, 1}
        };

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                for (int[] dir : directions) {
                    if (matches(r, c, dir[0], dir[1], rows, cols)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    /**
     * <p>The Elf looks quizzically at you. Did you misunderstand the assignment?</p>
     * <p>Looking for the instructions, you flip over the word search to find that this isn't actually an <code><em>XMAS</em></code> puzzle; it's an <code><em>X-MAS</em></code> puzzle in which you're supposed to find two <code>MAS</code> in the shape of an <code>X</code>. One way to achieve that is like this:</p>
     * <pre><code>
     * M.S
     * .A.
     * M.S
     * </code></pre>
     * <p>Irrelevant characters have again been replaced with <code>.</code> in the above diagram. Within the <code>X</code>, each <code>MAS</code> can be written forwards or backwards.</p>
     * <p>Here's the same example from before, but this time all of the <code>X-MAS</code>es have been kept instead:</p>
     * <pre><code>
     * .M.S......
     * ..A..MSMS.
     * .M.S.MAA..
     * ..A.ASMSM.
     * .M.S.M....
     * ..........
     * S.S.S.S.S.
     * .A.A.A.A..
     * M.M.M.M.M.
     * ..........
     * </code></pre>
     * <p>In this example, an <code>X-MAS</code> appears <code><em>9</em></code> times.</p>
     * <p>Flip the word search from the instructions back over to the word search side and try again. <strong>How many times does an <code>X-MAS</code> appear?</strong></p>
     */
    public static int partTwo() {
        int rows = DayFour.day4XmasArr.length;
        int cols = DayFour.day4XmasArr[0].length();
        int count = 0;

        for (int r = 1; r < rows - 1; r++) {
            for (int c = 1; c < cols - 1; c++) {
                if (DayFour.day4XmasArr[r].charAt(c) == 'A') {
                    if (isValidXMAS(r, c)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private static boolean matches(int r, int c, int dr, int dc, int rows, int cols) {
        for (int i = 0; i < "XMAS".length(); i++) {
            int nr = r + i * dr;
            int nc = c + i * dc;

            if (nr < 0 || nr >= rows || nc < 0 || nc >= cols || DayFour.day4XmasArr[nr].charAt(nc) != "XMAS".charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidXMAS(int r, int c) {
        char topLeft = DayFour.day4XmasArr[r - 1].charAt(c - 1);
        char topRight = DayFour.day4XmasArr[r - 1].charAt(c + 1);
        char bottomLeft = DayFour.day4XmasArr[r + 1].charAt(c - 1);
        char bottomRight = DayFour.day4XmasArr[r + 1].charAt(c + 1);

        char middle = DayFour.day4XmasArr[r].charAt(c);

        return (isMAS(topLeft, middle, bottomRight) && isMAS(bottomLeft, middle, topRight));
    }

    private static boolean isMAS(char first, char middle, char last) {
        return (first == 'M' && middle == 'A' && last == 'S') ||
                (first == 'S' && middle == 'A' && last == 'M');
    }
}
