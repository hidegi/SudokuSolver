import java.util.Arrays;

public class Solver {

    private class Cell {
        private int possibilities;
        private int answer = -1;
        private boolean solved = false;

        public Cell() {
            reset();
        }

        public void setPossibility(int num, boolean value) {
            if (num > 0 && num <= 9)
                possibilities = value ? (possibilities | (1 << (num - 1))) : (possibilities & ~(1 << (num - 1)));
        }

        public void reset() {
            possibilities = 0;
        }
    }

    private int countBoxPossibilities(int col, int row, int num) {
        return 0;
    }

    private Cell[][] grid = new Cell[9][9];

    public Solver() {
    }


    static int[][] easyPuzzle =
    {
            {0, 7, 0,   5, 8, 3,    0, 2, 0},
            {0, 5, 9,   2, 0, 0,    3, 0, 0},
            {3, 4, 0,   0, 0, 6,    5, 0, 7},

            {7, 9, 5,   0, 0, 0,    6, 3, 2},
            {0, 0, 3,   6, 9, 7,    1, 0, 0},
            {0, 0, 0,   0, 0, 2,    7, 0, 0},

            {9, 1, 4,   8, 3, 5,    0, 7, 6},
            {0, 3, 0,   7, 0, 1,    4, 9, 5},
            {5, 6, 7,   4, 2, 9,    0, 1, 3},
    };

    static int[][] hardPuzzle =
    {
            {0, 0, 6,   3, 0, 7,    0, 0, 0},
            {0, 0, 4,   0, 0, 0,    0, 0, 5},
            {1, 0, 0,   0, 0, 6,    0, 8, 2},

            {2, 0, 5,   0, 3, 0,    1, 0, 6},
            {0, 0, 0,   2, 0, 0,    3, 0, 0},
            {9, 0, 0,   0, 7, 0,    0, 0, 4},

            {0, 5, 0,   0, 0, 0,    0, 0, 0},
            {0, 1, 0,   0, 0, 0,    0, 0, 0},
            {0, 0, 8,   1, 0, 9,    0, 4, 0}
    };

    //372004701
    //004089006
    static int[][] puzzle =
            {
                    {3, 7, 2,   0, 0, 4,    9, 0, 0},
                    {0, 0, 4,   0, 8, 9,    1, 0, 0},
                    {7, 0, 1,   0, 0, 6,    2, 5, 3},

                    {0, 0, 1,   0, 0, 0,    1, 2, 3},
                    {9, 0, 0,   0, 0, 2,    6, 5, 4},
                    {0, 0, 0,   8, 0, 7,    7, 8, 9},

                    {4, 0, 5,   0, 0, 1,    6, 2, 0},
                    {2, 3, 7,   0, 0, 0,    5, 0, 1},
                    {0, 0, 0,   0, 2, 5,    7, 0, 0},
            };

    public enum Set
    {
        ROW,
        COLUMN,
        BOX
    }
    static boolean hasSet(int set, int num)
    {
        return (set & (1 << (num - 1))) != 0;
    }

    public static int checkOptions(Set set, int index, int num, int[][] grid)
    {
        return 0;
    }
    public static int countMissingNumbers(Set set, int index, int[][] grid)
    {
        int counter = 0;
        index -= 1;
        switch (set) {
            case ROW:
            case COLUMN:
            {
                for(int i = 0; i < 9; i++)
                {
                    int cell = (set == Set.ROW) ? grid[index][i] : grid[i][index];
                    if(cell == 0)
                        counter++;
                }
                break;
            }

            case BOX:
            {
                int x = index % 3;
                int y = index / 3;

                for(int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int cell = grid[i + y * 3][j + x * 3];
                        if (cell == 0)
                            counter++;
                    }
                }
            }
        }
        return counter;
    }

    public static int getChecksum(Set set, int index, int[][] grid)
    {
        if(index < 0 || index > 8) {
            throw new IndexOutOfBoundsException("index out of range for " + index);
        }
        int checksum = 0;
        switch (set)
        {
            case ROW:
            case COLUMN:
            {
                for(int i = 0; i < 9; i++)
                {
                    int cell = (set == Set.ROW) ? grid[index][i] : grid[i][index];
                    if(set == Set.ROW && grid[index][i] == 0)
                        continue;

                    if(set == Set.COLUMN && grid[i][index] == 0)
                        continue;

                    if(hasSet(checksum, cell)) {
                        return -1;
                    }
                    checksum |= (1 << (cell - 1));
                }
                break;
            }

            case BOX:
            {
                int x = index % 3;
                int y = index / 3;

                for(int i = 0; i < 3; i++)
                {
                    for(int j = 0; j < 3; j++)
                    {

                        int cell = grid[i + y * 3][j + x * 3];
                        if(cell == 0)
                            continue;

                        if(hasSet(checksum, cell)) {
                            return -1;
                        }
                        checksum |= (1 << (cell - 1));
                    }
                }
                break;
            }
        }
        return checksum;
    }
    public static boolean isValid(Set set, int index, int[][] grid)
    {
        return getChecksum(set, index, grid) != -1;
    }

    int ROWS[] = new int[9];
    int COLUMNS[] = new int[9];
    int BOXES[] = new int[9];

    static int calculatePossibilitiesFor(int num, int index, int[][] grid)
    {
        int possibilities = 0;
        if(isValid(Set.BOX, index, grid)) {
            int checksum = getChecksum(Set.BOX, index, grid);


            if (hasSet(checksum, num))
                return 0;

            long code = encodeSeries(Set.BOX, index, grid);

            int r = 0;
            long mask = 0xF00000000L;

            int x = index % 3;
            int y = index / 3;

            int b = 9;
            for (int i = 0; i < 3; i++) {
                for(int j = 0; j < 3; j++) {
                    int absRow = i + y * 3;
                    int absCol = j + x * 3;
                    b = 9 - (i * 3 + j) - 1;
                    int value = (int)((code & mask) >> (b * 4));
                    mask >>= 4;

                    if(value == 0)
                    {
                        int rowSum = getChecksum(Set.ROW, absRow, grid);
                        int colSum = getChecksum(Set.COLUMN, absCol, grid);

                        if(!hasSet(rowSum, num) && !hasSet(colSum, num)) {
                            possibilities++;
                        }
                    }
                }
            }
        }


        return possibilities;
    }

    //372004701
    //004089006

    //0000 0000 0100 0000 1000 1001 0000 0000 0101
    static long encodeSeries(Set set, int index, int[][] grid)
    {
        long code = 0;
        switch (set)
        {
            case ROW:
            case COLUMN:
            {
                for(int i = 0; i < 9; i++)
                {
                    byte cell = (byte)((set == Set.ROW) ? grid[index][i] : grid[i][index]);
                    code |= cell;
                    code <<= 4;
                }
                break;
            }

            case BOX:
            {
                int x = index % 3;
                int y = index / 3;

                for(int i = 0; i < 3; i++)
                {
                    for(int j = 0; j < 3; j++)
                    {
                        byte cell = (byte)grid[i + y * 3][j + x * 3];

                        code |= cell;
                        code <<= 4;
                    }
                }
                break;
            }
        }
        return code >> 4;
    }

    static void decodeSeries(long code)
    {
        long mask = 0xF00000000L;
        for(int i = 8; i >= 0; i--)
        {
            System.out.print(((code & mask) >> i * 4) + " ");
            mask >>= 4;
        }
    }

    static int countRaisedBits(long mask)
    {
        int count = 0;
        while(mask != 0)
        {
            if((mask & 1) != 0)
                count++;
            mask >>= 1;
        }
        return count;
    }

    public static void main(String[] args) {
        /*
        System.out.println(checkValidity(Set.ROW, 1, puzzle));
        System.out.println(checkValidity(Set.COLUMN, 1, puzzle));
        System.out.println(checkValidity(Set.BOX ,6, puzzle));
        System.out.println(countMissingNumbers(Set.ROW, 9, easyPuzzle));
        System.out.println(countMissingNumbers(Set.BOX, 8, easyPuzzle));
        */
        /*
        long code = encodeSeries(Set.ROW, 5, easyPuzzle);
        System.out.println(code);
        decodeSeries(code);
        System.out.println();

         */

        boolean f = true;
        int iterations = 1000;
        for(int k = 0; k < iterations; k++) {
            boolean solved = true;
            for (int i = 0; i < 9; i++) {
                //System.out.println((i + 1) + ". box: ");
                int checksum = getChecksum(Set.BOX, i, easyPuzzle);
                if(checksum != 0x1FF)
                    solved = false;
                checksum = getChecksum(Set.ROW, i, easyPuzzle);
                if(checksum != 0x1FF)
                    solved = false;
                checksum = getChecksum(Set.COLUMN, i, easyPuzzle);
                if(checksum != 0x1FF)
                    solved = false;

                if(solved) {
                    System.out.println("solved");
                    break;
                }
                for (int j = 0; j < 9; j++) {
                    if(hasSet(checksum, j + 1))
                        continue;

                    int possibilities = calculatePossibilitiesFor((j + 1), i, easyPuzzle);
                    if (possibilities != 0) {
                        int x = i % 3;
                        int y = i / 3;

                        checksum = getChecksum(Set.BOX, i, easyPuzzle);
                        boolean filler = countRaisedBits(checksum) == 8;

                        if (possibilities == 1 || filler)
                        {
                            for(int m = 0; m < 3; m++)
                            {
                                for(int n = 0; n < 3; n++)
                                {
                                    int cell = easyPuzzle[m + y * 3][n + x * 3];
                                    if(cell == 0) {
                                        int colSum = getChecksum(Set.COLUMN, n + x * 3, easyPuzzle);
                                        int rowSum = getChecksum(Set.ROW, m + y * 3, easyPuzzle);

                                        if (!hasSet(colSum, j + 1) && !hasSet(rowSum, j + 1)) {
                                            if(filler)
                                                System.out.println("filler found (" + (j + 1) + ") in box " + (i + 1) + " checksum: " +
                                                        Integer.toBinaryString((0x1FF & (~(1 << j)))));
                                            easyPuzzle[m + y * 3][n + x * 3] = j + 1;
                                        }
                                    }
                                }
                            }
                            //easyPuzzle[i][j] = (j + 1);
                        }
                    }
                }
            }
        }

        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                if(easyPuzzle[i][j] != 0)
                    System.out.print(easyPuzzle[i][j] + " ");
                else
                    System.out.print("_ ");
                if((j + 1) % 3 == 0 && (j != 8))
                    System.out.print("| ");
            }
            System.out.println();
            if((i + 1) % 3 == 0 && (i != 8))
                System.out.println("---------------------");
        }
    }
}