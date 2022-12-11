import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


// class to store estimation models and predict v from v'
public class PBEstimation {

    // 2d array storing information for all linear regressions
    double[][] lnr;

    public PBEstimation() {
        lnr = new double[56][4];
        for(int i=0; i<=4; i++) {
            for(int j=0; j<4; j++) {
                lnr[i][j] = -1;
            }
        }
        try {
            File file = new File("linear-regression-results.csv");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
              String data = reader.nextLine();
              int index = Integer.parseInt(data.split(",")[0]);
              int numObs = Integer.parseInt(data.split(",")[1]);
              double intercept = Double.parseDouble(data.split(",")[2]);
              double coefficient = Double.parseDouble(data.split(",")[3]);
              double rerror = Double.parseDouble(data.split(",")[4]);
              lnr[index][0] = numObs;
              lnr[index][1] = intercept;
              lnr[index][2] = coefficient;
              lnr[index][3] = rerror;
            }
            reader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred. File not found.");
            e.printStackTrace();
          }
    }

    // return number of observations in linear regression for d pieces on board or -1 if regression doesnt exist
    public double getNumObs(int d) {
        return lnr[d][0];
    }

    // return intercept of linear regression for d pieces on board or -1 if regression doesnt exist
    public double getIntercept(int d) {
        return lnr[d][1];
    }

    // return v' coefficient in linear regression for d pieces on board or -1 if regression doesnt exist
    public double getCoeff(int d) {
        return lnr[d][2];
    }

    // return r squared error of linear regression for d pieces on board or -1 if regression doesn't exist
    public double getR2Error(int d) {
        return lnr[d][3];
    }

    // return estimate of v for v' at d pieces on board, or -1 if linear regression doesn't exist
    public double estimateV(int d, int v_prime) {
        if(lnr[d][1] == -1) {
            return -1;
        }
        return getIntercept(d) + getCoeff(d) * v_prime;
    }

    public static void main(String[] args) {
        PBEstimation pb = new PBEstimation();
        System.out.println(pb.getNumObs(7));
        System.out.println(pb.getIntercept(7));
        System.out.println(pb.getCoeff(7));
        System.out.println(pb.getR2Error(7));
    }
}
