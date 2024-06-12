public class Main {

    static int[][] printSolution(int[][] array)
    {
        if(array.length != 9)
        {
            System.err.println("invalid size");
        }

        for(var v : array)
            if(v.length != 9)
            {
                System.err.println("invalid size");
                return null;
            }

        boolean solved = false;
        for(int i = 0; i < array.length; i++)
        {
            inner:
            for(int j = 0; j < array[i].length; j++)
            {
                int currentCell = array[i][j];

                //vertical check
                for(int col = 0; col < array.length; col++)
                {
                    if(col == i)
                        continue;

                    if(currentCell == array[col][j])
                    {
                        if(array[i][j]++ > 9)
                            array[i][j] = 1;
                        i = 0;
                        break inner;
                    }
                }

                //horizontal check
                for(int row = 0; row < array.length; row++)
                {
                    if(row == i)
                        continue;

                    if(currentCell == array[i][row] && currentCell != 0)
                    {
                        if(array[i][j]++ > 9)
                            array[i][j] = 1;
                        i = 0;
                        break inner;
                    }
                }
            }
        }
        return array;
    }

    static void printArray(int[][] array)
    {
        if(array == null)
            System.err.println("array is null");

        for(var a : array)
        {
            for(var b : a)
            {
                System.out.print(b + " ");
            }
            System.out.println();
        }
    }

    static void printVector(int[] array)
    {
        for (int j : array) System.out.println(j);
    }

    static int[] getBox(int row, int col, int[][] array)
    {
        if(row > 2 || col > 2)
            return null;
        int[] output = new int[3 * 3];
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                output[i * 3 + j] = array[row * 3 + i][col * 3 + j];
            }
        }

        return output;
    }

    static boolean cellComplete(int array[][])
    {
        int sum = 0;
        for(var a : array)
        {
            for(var b : a)
                sum += b;
        }
        return sum == 45;
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
        int[][] puzzle =
                {
                        {0, 7, 2,   0, 0, 4,    9, 0, 0},
                        {3, 0, 4,   0, 8, 9,    1, 0, 0},
                        {7, 0, 1,   0, 0, 6,    2, 5, 3}
                        ,
                        {7, 0, 1,   0, 0, 0,    0, 9, 5},
                        {9, 0, 0,   0, 0, 2,    0, 7, 0},
                        {0, 0, 0,   8, 0, 7,    0, 1, 2},

                        {4, 0, 5,   0, 0, 1,    6, 2, 0},
                        {2, 3, 7,   0, 0, 0,    5, 0, 1},
                        {0, 0, 0,   0, 2, 5,    7, 0, 0},
                };
        printVector(getBox(1, 2, puzzle));
        */

        /*
         *    111110111
         *  & 111111111
         *    111110111
         */
        int num = 6;
        int val = 0x1FF & ~(1 << num - 1);
        System.out.println(Integer.toBinaryString(val));
        System.out.println(countRaisedBits(val));
    }
}