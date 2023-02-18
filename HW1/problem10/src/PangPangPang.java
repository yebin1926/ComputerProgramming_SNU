public class PangPangPang {
    public static void afterPang(int[][] board) {
        int n = board.length;
        int m = board[0].length;

        //copy board into nboard
        int[][] nboard = new int[n][m];
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                nboard[i][j] = board[i][j];
            }
        }
        Boolean t = true;

        while(t) {
            //make nboard = board with 0s
            nboard = searchAndMark(nboard, n, m);


            //exit?
            t = false;
            for(int i=0; i<n; i++) {
                for(int j=0; j<m; j++) {
                    if(nboard[i][j] != board[i][j]) {
                        t = true;
                    }
                    board[i][j] = nboard[i][j];
                }
            }
            if(!t){
                finishPang(nboard, n, m);
            }

            //count number of times we need to run gravity
            int maxN = 0;
            for(int j=0; j<m; j++) {
                int x=0;
                for(int i=0; i<n; i++) {
                    if(nboard[i][j] == 0) { x++; }
                }
                if(x>maxN) {maxN = x;};
            }
            //gravity
            for(int y=0; y<maxN; y++){
                for(int i=0; i<n-1; i++) {
                    for (int j = 0; j <m; j++) {
                        while (nboard[i + 1][j] == 0 && nboard[i][j] != 0) {
                            nboard[i + 1][j] = nboard[i][j];
                            nboard[i][j] = 0;
                        }
                    }
                }
            }
        }
    }
    private static int[][] searchAndMark(int[][] board, int n, int m) {
        //create corresponding boolean matrix
        Boolean[][] BBoard = new Boolean[n][m];
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                BBoard[i][j] = false;
            }
        }
        // boolean to check if it has no more to pang
        boolean p = false;
        //for each square, check if they have match vertically
        for(int i=0; i<n-2; i++) {
            for(int j=0; j<m; j++) {
                if(board[i][j] == board[i+1][j] && board[i][j] == board[i+2][j]) {
                    p = true;
                    BBoard[i][j] =true; BBoard[i+1][j]=true; BBoard[i+2][j] =true;
                }
            }
        }

        //check for horizontal consecutives
        for(int i=0; i<n; i++) {
            for (int j=0; j<m-2; j++) {
                if(board[i][j] == board[i][j+1] && board[i][j] == board[i][j+2]) {
                    p = true;
                    BBoard[i][j] = true; BBoard[i][j + 1] = true; BBoard[i][j + 2] = true;
                }
            }
        }

        // if there is no remaining matches/pang, go to finishing method
        if (!p) {
            finishPang(board, n, m);
        }
        //for each square on BBoard, if it's true, change corresponding value on board to 0
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if (BBoard[i][j]) {board[i][j] = 0;}
            }
        }
        return board;
    }

    //print the final board
    private static void finishPang(int[][] board, int n, int m) {
        for(int i=0; i<n; i++) {
            String str = "";
            for(int j=0; j<m; j++) {
                // MAKE SURE TO TAKE OUT TRAILING SPACES!!!!!!
                str += board[i][j]+" ";
            }
            System.out.println(str.trim());
        }
        System.exit(0);
    }
}

