// class to store estimation models and predict v from v'
public class PBEstimation {

    // 2d array storing information for all linear regressions
    //  first index corresponds to number of pieces on board
    //  second index correpsonds to different info for that linear regression
    double[][] lnr;

    public PBEstimation() {
        lnr = new double[65][4];

        // ugly code, but it works
        double[] a5 = {1998,9.00526,-0.001317,6.18E-07};
        lnr[5] = a5;
        double[] a6 = {2002,7.50565,0.49887,0.2513};
        lnr[6] = a6;
        double[] a7 = {1873,10.24264,0.27242,0.03157};
        lnr[7] = a7;
        double[] a8 = {1778,12.3642,0.02023,0.0002269};
        lnr[8] = a8;
        double[] a9 = {1935,13.25188,-0.0404,0.001216};
        lnr[9] = a9;
        double[] a10 = {1788,13.5883,0.08516,0.008611};
        lnr[10] = a10;
        double[] a11 = {1868,15.36411,-0.04967,0.002525};
        lnr[11] = a11;
        double[] a12 = {1729,14.27287,0.17467,0.06985};
        lnr[12] = a12;
        double[] a13 = {1868,17.17737,-0.05339,0.003262};
        lnr[13] = a13;
        double[] a14 = {1759,15.24675,0.21462,0.1104};
        lnr[14] = a14;
        double[] a15 = {1848,18.56498,-0.02593,0.0008742};
        lnr[15] = a15;
        double[] a16 = {1762,16.17335,0.25377,0.1369};
        lnr[16] = a16;
        double[] a17 = {1863,18.93002,0.08891,0.01295};
        lnr[17] = a17;
        double[] a18 = {1791,17.47835,0.25658,0.1599};
        lnr[18] = a18;
        double[] a19 = {1867,19.28576,0.1794,0.05804};
        lnr[19] = a19;
        double[] a20 = {1774,18.09214,0.30556,0.2108};
        lnr[20] = a20;
        double[] a21 = {1850,19.47139,0.26351,0.1258};
        lnr[21] = a21;
        double[] a22 = {1778,19.12141,0.31853,0.2252};
        lnr[22] = a22;
        double[] a23 = {1869,20.68138,0.26863,0.1576};
        lnr[23] = a23;
        double[] a24 = {1791,19.5711,0.3593,0.2691};
        lnr[24] = a24;
        double[] a25 = {1893,21.5386,0.2945,0.1918};
        lnr[25] = a25;
        double[] a26 = {1800,20.6409,0.3654,0.2806};
        lnr[26] = a26;
        double[] a27 = {1910,21.86726,0.34181,0.2641};
        lnr[27] = a27;
        double[] a28 = {1806,21.16396,0.3971,0.3077};
        lnr[28] = a28;
        double[] a29 = {1891,22.54047,0.36558,0.2965};
        lnr[29] = a29;
        double[] a30 = {1810,21.67922,0.42344,0.319};
        lnr[30] = a30;
        double[] a31 = {1895,22.90977,0.39679,0.3405};
        lnr[31] = a31;
        double[] a32 = {1815,22.67501,0.42211,0.3342};
        lnr[32] = a32;
        double[] a33 = {1908,23.42166,0.42007,0.3617};
        lnr[33] = a33;
        double[] a34 = {1809,23.18932,0.44261,0.362};
        lnr[34] = a34;
        double[] a35 = {1912,24.0962,0.4324,0.3852};
        lnr[35] = a35;
        double[] a36 = {1811,23.62339,0.46029,0.372};
        lnr[36] = a36;
        double[] a37 = {1913,24.591,0.4465,0.3926};
        lnr[37] = a37;
        double[] a38 = {1810,23.23506,0.50772,0.4215};
        lnr[38] = a38;
        double[] a39 = {1912,25.57349,0.44157,0.3723};
        lnr[39] = a39;
        double[] a40 = {1806,24.51511,0.49067,0.3874};
        lnr[40] = a40;
        double[] a41 = {1905,25.58851,0.4703,0.3762};
        lnr[41] = a41;
        double[] a42 = {1810,24.17372,0.5292,0.4158};
        lnr[42] = a42;
        double[] a43 = {1909,26.93858,0.45163,0.3606};
        lnr[43] = a43;
        double[] a44 = {1817,24.30111,0.54794,0.4456};
        lnr[44] = a44;
        double[] a45 = {1904,26.0311,0.5002,0.3849};
        lnr[45] = a45;
        double[] a46 = {1812,23.96063,0.57461,0.4485};
        lnr[46] = a46;
        double[] a47 = {1896,26.08399,0.52059,0.3932};
        lnr[47] = a47;
        double[] a48 = {1804,24.5687,0.57453,0.4414};
        lnr[48] = a48;
        double[] a49 = {1902,27.72316,0.48595,0.3461};
        lnr[49] = a49;
        double[] a50 = {1803,26.96986,0.51815,0.3746};
        lnr[50] = a50;
        double[] a51 = {1897,28.53621,0.47729,0.3247};
        lnr[51] = a51;
        double[] a52 = {1897,28.53621,0.47729,0.3247};
        lnr[52] = a52;
        double[] a53 = {1882,30.37027,0.43308,0.2734};
        lnr[53] = a53;
        double[] a54 = {1766,32.87089,0.37124,0.1916};
        lnr[54] = a54;
        double[] a55 = {1792,37.79713,0.23736,0.07451};
        lnr[55] = a55;
        double[] a56 = {1377,46.25399,0.0367,0.00161};
        lnr[56] = a56;

        // number of pieces without linear regression have all values set to -1
        for(int i=0; i<=4; i++) {
            for(int j=0; j<4; j++) {
                lnr[i][j] = -1;
            }
        }
        for(int i=58; i<65; i++) {
            for(int j=0; i<4; j++) {
                lnr[i][j] = -1;
            }
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
        // System.out.println(pb.getNumObs(7));
        // System.out.println(pb.getIntercept(7));
        // System.out.println(pb.getCoeff(7));
        // System.out.println(pb.getR2Error(7));
        System.out.println(pb.estimateV(10, 6));
        System.out.println(pb.estimateV(26, 20));
    }
}
