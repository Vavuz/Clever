package com.example.clever;

enum SubscriptionType {
    DAILY,
    WEEKLY,
    TWOWEEKS,
    THREEWEEKS,
    FOURWEEKS,
    MONTHLY,
    TWOMONTHS,
    THREEMONTHS,
    FOURMONTHS,
    FIVEMONTHS,
    SIXMONTHS,
    YEARLY,
    TWOYEARS,
    THREEYEARS,
    FOURYEARS,
    FIVEYEARS,
    TENYEARS
}

public class Expense {
    private String name;
    private float price;
    private SubscriptionType subscriptionType;

    /**
     * Constructor
     * @param name
     * @param price
     * @param subscriptionType
     */
    public Expense(String name, float price, SubscriptionType subscriptionType) {
        return;
    }

    /**
     * Returns the namea
     * @return
     */
    public String getName() {
        return name;
    }
}
