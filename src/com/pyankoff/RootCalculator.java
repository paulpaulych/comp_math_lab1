package com.pyankoff;

import java.util.ArrayList;
import java.util.List;

public class RootCalculator {

    private double a;
    private double b;
    private double c;
    private double d;
    private List<Double> result = new ArrayList<>();
    private static double eps = 0.0000001;

    public RootCalculator(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = 4*a*a - 12*b;
    }

    private void calculate(){
        int count = discriminantSolutionsNumber();
        double x1 = (-2*a - Math.sqrt(d)) /6;
        double x2 = (-2*a + Math.sqrt(d)) /6;

        if(count == 0 || count == 1){
            Pair domain;
            if(c >= 0){
                domain = findDomain(0, false);
            }else{
                domain = findDomain(0, true);
            }
            result.add(findRoot(domain));
            return;
        }
        if(Math.abs(f(x1) - 0) < eps && f(x2) < 0){
            result.add(x1);
            result.add(findRoot(findDomain(x2, true)));
            return;
        }
        if(f(x1) > 0 && Math.abs(f(x2) - 0) < eps){
            result.add(x2);
            result.add(findRoot(findDomain(x1, false)));
            return;
        }
        if(f(x1) > 0 && f(x2) > 0){
            result.add(findRoot(findDomain(x1, false)));
            return;
        }
        if(f(x1) < 0 && f(x2) < 0){
            result.add(findRoot(findDomain(x2, true)));
            return;
        }
        result.add(findRoot(findDomain(x1, false)));
        result.add(findRoot(findDomain(x1, true)));
        result.add(findRoot(findDomain(x2, true)));
    }

    public List<Double> getResult(){
        calculate();
        return result;
    }

    private double f(double x){
        return x*x*x + a*x*x + b*x + c;
    }

    private int discriminantSolutionsNumber(){
        if(d < 0){
            return 0;
        } else if(d == 0){
            return 1;
        } else{
            return 2;
        }
    }

    private Pair findDomain(double start, boolean direction){
        double b0 = start;
        double a0 = direction ? start + 1: start - 1;
        while (f(a0) * f(b0) > 0){
            b0 = a0;
            a0 = direction ? a0 + 1: a0 - 1;
        }
        Pair pair = new Pair(Math.min(a0, b0), Math.max(a0, b0));
        System.out.println("pair: [" + pair.getA() +"; "+ pair.getB() + "]");
        return pair;
    }

    public double findRoot(Pair domain){
        double left = domain.getA();
        double right = domain.getB();
        if(Math.abs(f(left) - 0) < eps){
            return left;
        }
        if(Math.abs(f(right) - 0) < eps){
            return right;
        }
        double center = (left + right)/2;
        if(f(center)*f(left) <= 0){
            return findRoot(new Pair(Math.min(center, left), Math.max(center, left)));
        }
        return findRoot(new Pair(Math.min(center, right), Math.max(center, right)));
    }
}
