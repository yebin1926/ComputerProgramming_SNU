public class DrawingFigure {
    public static void drawFigure(int n) {
        int counter = 0;
        for(int row=1; row<n+1; row++) {
            for(int s1=n-row; s1>0; s1--) {
                System.out.print(" ");
            }
            if(row == 1 || row == n) {
                for(int col=1; col<=row*2-1; col++) {
                    System.out.print("*");
                }
            } else {
                counter ++;
                System.out.print("*");
                for(int m=1; m<2*counter; m++) { //for each row, how many spaces I have to print
                    System.out.print(" ");
                }
                System.out.print("*");
            }
            System.out.println();
        }

    }
}
