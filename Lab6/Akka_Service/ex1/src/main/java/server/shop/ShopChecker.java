package server.shop;

import java.util.Random;

public class ShopChecker {

    static Random generator = new Random();

    public static int getCheckingTime() {
        return generator.nextInt(400) + 100;
    }

    public static int getValueOfProduct() {
        return generator.nextInt(10) + 1;
    }
}
