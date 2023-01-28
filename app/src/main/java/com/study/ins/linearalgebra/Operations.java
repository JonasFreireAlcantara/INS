package com.study.ins.linearalgebra;

public class Operations {

    public static double norm(float[] vector) {
        return Math.sqrt(
                vector[0] * vector[0]
                + vector[1] * vector[1]
                + vector[2] * vector[2]
        );
    }

    public static float[] multiplyByConstant(float[] a, float k) {
        float[] result = {
            a[0] * k,
            a[1] * k,
            a[2] * k
        };
        return result;
    }

    public static float[] add(float[] a, float[] b) {
        float[] result = {
                a[0] + b[0],
                a[1] + b[1],
                a[2] + b[2],
        };
        return result;
    }
}
