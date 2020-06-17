package server.shop;

public enum Products {
    COFFEE("coffee"),
    TEA("tea"),
    WATER("water"),
    COLA("cola"),
    PEPSI("pepsi"),
    LIPTON("lipton"),
    NESTEA("nestea");

    String product;

    Products(String product) {
        this.product = product;
    }
}
