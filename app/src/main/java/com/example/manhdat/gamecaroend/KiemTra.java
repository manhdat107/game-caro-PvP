package com.example.manhdat.gamecaroend;

public class KiemTra
{
    private static final int[][][] VECTORS={
            // up, down
            { {0, 1}, {0, -1} },
            // left, right
            { {-1, 0}, {1, 0} },
            // up-left, down-right
            { {-1, 1}, {1, -1} },
            // up-right, down-left
            { {1, 1}, {-1, -1} }
    };
    public static boolean isWin(char[][] board, final int PX, final int PY, char player)
    {
        loop_couples_of_vector: for (int[][] vecs:VECTORS)
        {
            int allyCount=1; // so quan dong minh ben canh
            char[] tailCouple={'\0', '\0'}; // 2 quân ở 2 đầu 1 hàng ( ngang, dọc, chéo), khoi tao ban dau la 2 o trong
            int tailIndex=0;

            for (int[] v:vecs)
            {
                int x=PX+v[0], y=PY+v[1];
                while (allyCount<=6 && -1<x && x<board.length && -1<y && y<board[0].length)
                {
                    if (board[x][y]!=player)
                    {
                        tailCouple[tailIndex++]=board[x][y];
                        break;
                    }

                    ++allyCount;
                    x+=v[0]; y+=v[1];
                }

                if (allyCount>5) continue loop_couples_of_vector;
            }

            if (allyCount==5 && (tailCouple[0]=='\0' || tailCouple[1]=='\0')) return true;
        }

        return false;
    }
}
