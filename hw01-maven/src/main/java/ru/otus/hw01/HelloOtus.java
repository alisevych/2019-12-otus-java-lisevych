package ru.otus.hw01;

import com.google.common.math.BigIntegerMath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HelloOtus {

    public static void main(String... args) throws IOException {

        int intReceived = getPositiveInteger();
        System.out.format("Factorial of %d is %d\n", intReceived, BigIntegerMath.factorial(intReceived));

    }

    private static int getPositiveInteger() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter positive integer: ");
        Integer result = null;
        while (result == null) {
            try {
                String input = br.readLine();
                result = Integer.valueOf(input);
            } catch (NumberFormatException e) {
                System.out.print("\nINTEGER, please: ");
            }
        }
        return result;
    }
}
