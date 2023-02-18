public class PentominoSum {
    public static void printMaxSum(int[][] board) {
        int maxSum = 0;
        int n = board.length;
        int m = board[0].length;
        maxSum = testCross(board, n, m, maxSum);
        maxSum = testVert(board, n, m, maxSum);
        maxSum = testHorizon(board, n, m, maxSum);
        maxSum = testCorner1(board, n, m, maxSum);
        maxSum = testCorner2(board, n, m, maxSum);
        maxSum = testCorner3(board, n, m, maxSum);
        maxSum = testCorner4(board, n, m, maxSum);
        System.out.println(maxSum);
    }

    private static int testCross(int[][] board, int n, int m, int maxSum) {
        for(int i=1; i<=n-2; i++) {
            for(int j=1; j<=m-2; j++) {
                int currSum = board[i][j] + board[i-1][j] + board[i][j-1] + board[i][j+1] + board[i+1][j];
                if(currSum > maxSum) {
                    maxSum = currSum;
                }
            }
        }
        return maxSum;
    }

    private static int testVert(int[][] board, int n, int m, int maxSum) {
        for(int i=0; i<=n-5; i++) {
            for(int j=0; j<=m-1; j++) {
                int currSum = board[i][j] + board[i+1][j] + board[i+2][j] + board[i+3][j] + board[i+4][j];
                if(currSum > maxSum) {
                    maxSum = currSum;
                }
            }
        }
        return maxSum;
    }

    private static int testHorizon(int[][] board, int n, int m, int maxSum) {
        for(int i=0; i<=n-1; i++) {
            for(int j=0; j<=m-5; j++) {
                int currSum = board[i][j] + board[i][j+1] + board[i][j+2] + board[i][j+3] + board[i][j+4];
                if(currSum > maxSum) {
                    maxSum = currSum;
                }
            }
        }
        return maxSum;
    }

    private static int testCorner1(int[][] board, int n, int m, int maxSum) {
        for(int i=2; i<=n-1; i++) {
            for(int j=2; j<=m-1; j++) {
                int currSum = board[i][j] + board[i-1][j] + board[i-2][j] + board[i][j-1] + board[i][j-2];
                if(currSum > maxSum) {
                    maxSum = currSum;
                }
            }
        }
        return maxSum;
    }

    private static int testCorner2(int[][] board, int n, int m, int maxSum) {
        for(int i=0; i<=n-3; i++) {
            for(int j=2; j<=m-1; j++) {
                int currSum = board[i][j] + board[i][j-1] + board[i][j-2] + board[i+1][j] + board[i+2][j];
                if(currSum > maxSum) {
                    maxSum = currSum;
                }
            }
        }
        return maxSum;
    }

    private static int testCorner3(int[][] board, int n, int m, int maxSum) {
        for(int i=0; i<=n-3; i++) {
            for(int j=0; j<=m-3; j++) {
                int currSum = board[i][j] + board[i][j+1] + board[i][j+2] + board[i+1][j] + board[i+2][j];
                if(currSum > maxSum) {
                    maxSum = currSum;
                }
            }
        }
        return maxSum;
    }

    private static int testCorner4(int[][] board, int n, int m, int maxSum) {
        for(int i=2; i<=n-1; i++) {
            for(int j=0; j<=m-3; j++) {
                int currSum = board[i][j] + board[i-1][j] + board[i-2][j] + board[i][j+1] + board[i][j+2];
                if(currSum > maxSum) {
                    maxSum = currSum;
                }
            }
        }
        return maxSum;
    }
}

