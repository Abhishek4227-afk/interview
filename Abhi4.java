import java.util.Scanner*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PolynomialCValueNoLib {

    public static void main(String[] args) {
        try {
            // Step 1: Read JSON file into a string
            String content = new String(Files.readAllBytes(Paths.get("data.json")));

            // Step 2: Remove extra spaces
            content = content.replaceAll("\\s+", "");

            // Step 3: Extract values for a, b, x
            double a = Double.parseDouble(content.split("\"a\":")[1].split(",")[0]);
            double b = Double.parseDouble(content.split("\"b\":")[1].split(",")[0]);
            double x = Double.parseDouble(content.split("\"x\":")[1].split(",")[0]);

            // Step 4: Extract points array
            String pointsData = content.split("\"points\":\\[")[1].split("]")[0];
            String[] pointStrings = pointsData.split("\\},\\{");

            double[][] points = new double[pointStrings.length][2];
            for (int i = 0; i < pointStrings.length; i++) {
                String pointStr = pointStrings[i].replace("{", "").replace("}", "");
                double px = Double.parseDouble(pointStr.split("\"x\":")[1].split(",")[0]);
                double py = Double.parseDouble(pointStr.split("\"y\":")[1].split(",")[0]);
                points[i][0] = px;
                points[i][1] = py;
            }

            // Step 5: Substitution Method
            double cSubstitute = -(a * Math.pow(x, 2) + b * x);
            System.out.println("C (Substitution Method) = " + cSubstitute);

            // Step 6: Lagrange Interpolation
            double cLagrange = lagrangeInterpolation(points, 0);
            System.out.println("C (Lagrange Interpolation) = " + cLagrange);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing data: " + e.getMessage());
        }
    }

    // Method for Lagrange interpolation
    public static double lagrangeInterpolation(double[][] points, double value) {
        double result = 0;
        int n = points.length;

        for (int i = 0; i < n; i++) {
            double term = points[i][1];
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    term *= (value - points[j][0]) / (points[i][0] - points[j][0]);
                }
            }
            result += term;
        }
        return result;
    }
}