public class MatrixMultiplication {
    public static void multiply(int[][] A, int[][] B) {
        // make vars for A and B's number of rows and cols
        int Arow = A.length;
        int Acol = A[0].length;
        int Bcol = B[0].length;

        //insert values of each A's row and B's column to get each element in matrix
        for(int i=0; i<Arow; i++) {
            String fin = "";
            for(int j=0; j<Bcol; j++) {
                long x = calcElement(i,j, Acol, A, B);
                // append each element to final string
                fin += String.valueOf(x)+" ";
            }
            System.out.println(fin.trim());
        }
    }
    // function that outputs value of one element in matrix
    private static long calcElement(int AR, int BC, int Acol, int[][] A, int[][] B){
        long x1 = 0;
        for(int k=0; k<Acol; k++ ) {
            long y = A[AR][k]*B[k][BC];
            x1 += y;
        }
        return x1;
    }

}


