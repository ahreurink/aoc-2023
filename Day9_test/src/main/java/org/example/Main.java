package org.example;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Main {
    static final int part = 2;
    static final String FILE_PATH = "Day9_test/input_lines_test_2";

    static double predictNextValue(List<WeightedObservedPoint> values){
        values.stream().forEach(w -> System.out.printf(w.getWeight() + ", "));
        System.out.println();

        values.stream().forEach(w -> System.out.printf(w.getX() + ", "));
        System.out.println();
        values.stream().forEach(w -> System.out.printf(w.getY() + ", "));
        System.out.println();

        double[] coeffs = fitter.fit(values);
        PolynomialFunction myfunc = new PolynomialFunction(coeffs);
        double xValueToPredict = values.get(values.size()-1).getX() + 1;
        System.out.println("poly: " + Arrays.toString(coeffs));
        System.out.println("size: " + coeffs.length);
        return myfunc.value(xValueToPredict);
    }

    static PolynomialCurveFitter fitter;

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(Path.of(FILE_PATH));

        fitter = PolynomialCurveFitter.create(25);

        inputLines.stream()
                .map(s -> s.split(" "))
                .map(array -> IntStream.range(0, array.length)
                        .mapToObj(index -> new WeightedObservedPoint(1, index, Integer.parseInt(array[index])))
                        .toList())
                .map(Main::predictNextValue)
                .map(Math::round)
                .forEach(System.out::println);

        if (part == 1)
            System.out.println("result = " + 0);
        if (part == 2)
            System.out.println("result = ");
    }
}