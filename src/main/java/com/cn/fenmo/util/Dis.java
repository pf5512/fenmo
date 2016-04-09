package com.cn.fenmo.util;

import java.math.BigDecimal;

public class Dis {
  
  //计算两个人之间的距离
  public static double getDistance(double lat1,double lng1 , double lat2,double lng2) {
    double PI = 3.14159265358979323; // 圆周率
    double R = 6371229; // 地球的半径
    double x, y, distance;
    x = (lng2-lng1) * PI * R* Math.cos(((lat2+lat1) / 2) * PI / 180) / 180;
    y = (lat2-lat1) * PI *R / 180;
    distance = Math.sqrt(x*x+y*y);
    BigDecimal   b   =   new   BigDecimal(distance);  
    double   f1   =   b.setScale(0,   BigDecimal.ROUND_HALF_UP).doubleValue(); 
    return f1;
  }
  
  public static double computeDistance(double lat1, double lon1,double lat2, double lon2) {
      int MAXITERS = 20;
      lat1 *= Math.PI / 180.0;
      lat2 *= Math.PI / 180.0;
      lon1 *= Math.PI / 180.0;
      lon2 *= Math.PI / 180.0;

      double a = 6378137.0; // WGS84 major axis
      double b = 6356752.3142; // WGS84 semi-major axis
      double f = (a - b) / a;
      double aSqMinusBSqOverBSq = (a * a - b * b) / (b * b);

      double L = lon2 - lon1;
      double A = 0.0;
      double U1 = Math.atan((1.0 - f) * Math.tan(lat1));
      double U2 = Math.atan((1.0 - f) * Math.tan(lat2));

      double cosU1 = Math.cos(U1);
      double cosU2 = Math.cos(U2);
      double sinU1 = Math.sin(U1);
      double sinU2 = Math.sin(U2);
      double cosU1cosU2 = cosU1 * cosU2;
      double sinU1sinU2 = sinU1 * sinU2;

      double sigma = 0.0;
      double deltaSigma = 0.0;
      double cosSqAlpha = 0.0;
      double cos2SM = 0.0;
      double cosSigma = 0.0;
      double sinSigma = 0.0;
      double cosLambda = 0.0;
      double sinLambda = 0.0;

      double lambda = L; // initial guess
      for (int iter = 0; iter < MAXITERS; iter++) {
          double lambdaOrig = lambda;
          cosLambda = Math.cos(lambda);
          sinLambda = Math.sin(lambda);
          double t1 = cosU2 * sinLambda;
          double t2 = cosU1 * sinU2 - sinU1 * cosU2 * cosLambda;
          double sinSqSigma = t1 * t1 + t2 * t2; // (14)
          sinSigma = Math.sqrt(sinSqSigma);
          cosSigma = sinU1sinU2 + cosU1cosU2 * cosLambda; // (15)
          sigma = Math.atan2(sinSigma, cosSigma); // (16)
          double sinAlpha = (sinSigma == 0) ? 0.0 :cosU1cosU2 * sinLambda / sinSigma; // (17)
          cosSqAlpha = 1.0 - sinAlpha * sinAlpha;
          cos2SM = (cosSqAlpha == 0) ? 0.0 :cosSigma - 2.0 * sinU1sinU2 / cosSqAlpha; // (18)
          double uSquared = cosSqAlpha * aSqMinusBSqOverBSq; // defn
          A = 1 + (uSquared / 16384.0) * (4096.0 + uSquared *(-768 + uSquared * (320.0 - 175.0 * uSquared)));
          double B = (uSquared / 1024.0) * (256.0 + uSquared *(-128.0 + uSquared * (74.0 - 47.0 * uSquared)));
          double C = (f / 16.0) *cosSqAlpha *(4.0 + f * (4.0 - 3.0 * cosSqAlpha)); 
          double cos2SMSq = cos2SM * cos2SM;
          deltaSigma = B * sinSigma *(cos2SM + (B / 4.0) *
               (cosSigma * (-1.0 + 2.0 * cos2SMSq) -
                (B / 6.0) * cos2SM *
                (-3.0 + 4.0 * sinSigma * sinSigma) *
                (-3.0 + 4.0 * cos2SMSq)));

          lambda = L +(1.0 - C) * f * sinAlpha *(sigma + C * sinSigma *(cos2SM + C * cosSigma *(-1.0 + 2.0 * cos2SM * cos2SM))); // (11)

          double delta = (lambda - lambdaOrig) / lambda;
          if (Math.abs(delta) < 1.0e-12) {
              break;
          }
      }

      return  b * A * (sigma - deltaSigma);
  }
  public static double geographicDistance(double lng1, double lat1, double lng2, double lat2) {
    double dLat = Math.toRadians(lat2 - lat1); 
    double dLng = Math.toRadians(lng2 - lng1);
    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double distance = 6378137 * c;
    return new BigDecimal(distance).intValue();
}
  
  
  public  static void main(String[] arg){
    System.out.println(getDistance(34.8082342, 113.6125439, 34.8002478, 113.659779));
    System.out.println(geographicDistance(113.6125439,34.8082342, 113.659779, 34.8002478 ));
    
   // System.out.println(computeDistance(34.8082342, 113.6125439, 34.8002478, 113.659779));

  }
}
