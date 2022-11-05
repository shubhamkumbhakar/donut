import java.util.concurrent.TimeUnit;
class Main {
    static double pi = 3.14;
    static String luminance = ".,-~:;=!*#$@";
    // static String luminance = "ABCDEFGHIJK";
    static int screenWidth = 40;
    static int screenHeight = 40;
    static double R1 = 1;
    static double R2 = 2;
    static double K2 = 5;
    static double K1 = screenWidth*K2*3/(8*(R1+R2));
    
    public static String[][] calculateMatrix(double A, double B){
        String[][] matrix = new String[screenHeight][screenWidth];
        double[][] zBuffer = new double[screenHeight][screenWidth];
        for(int i=0;i<screenHeight;i++){
            for(int j=0;j<screenWidth;j++){
                matrix[i][j] = " ";
            }
        }
        double cosA = Math.cos(A);
        double sinA = Math.sin(A);
        double cosB = Math.cos(B);
        double sinB = Math.sin(B);

        for(double theta=0;theta<2*pi;theta+=0.05){
            double sintheta = Math.sin(theta);
            double costheta = Math.cos(theta);
            for(double phi=0;phi<2*pi;phi+=0.05){
                double cosphi = Math.cos(phi);
                double sinphi = Math.sin(phi);

                double circlex = R2 + R1*costheta;
                double circley = R1*sintheta;

                double x = circlex*(cosB*cosphi + sinA*sinB*sinphi) - circley*cosA*sinB; 
                double y = circlex*(sinB*cosphi - sinA*cosB*sinphi) + circley*cosA*cosB;
                double z = K2 + cosA*circlex*sinphi + circley*sinA;
                double ooz = 1/z;

                int xp = (int) (screenWidth/2 + K1*ooz*x);
                int yp = (int) (screenHeight/2 - K1*ooz*y);
                
                double L = cosphi*costheta*sinB - cosA*costheta*sinphi - sinA*sintheta + cosB*(cosA*sintheta - costheta*sinA*sinphi);
                L = (L+1.5)*4;
                // System.out.println(L);
                if(L>0){
                    if(ooz>zBuffer[yp][xp]){
                        zBuffer[yp][xp] = ooz;
                        int luminanceIndex = (int)L;
                        matrix[yp][xp] = luminance.charAt(luminanceIndex)+"";
                    }
                }
            }
        }
        return matrix;
    }
    


	public static void main (String[] args) {
        double A = 0;
        double B = 0;
        
    while (true) {
        String[][] matrix = calculateMatrix(A,B);
        for(int i=0;i<screenHeight;i++){
            for(int j=0;j<screenWidth;j++){
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        A+= 0.05;
        B+= 0.05;
        try{
            TimeUnit.MILLISECONDS.sleep(50);
        }
        catch(Exception e){

        }
        System.out.print("\033[H\033[2J");  
        System.out.flush();
	}
  }
}