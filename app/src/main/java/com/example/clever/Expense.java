package com.example.clever;

import java.util.ArrayList;
import java.util.Date;

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
    public static ArrayList<Expense> expenseArrayList = new ArrayList<>();
    public static String EXPENSE_EDIT_EXTRA =  "expenseEdit";

    private int id;
    private String name;
    private String price;
    private String subscriptionType;
    private Date deleted;

    public Expense(int id, String name, String price, String subscriptionType, Date deleted)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.subscriptionType = subscriptionType;
        this.deleted = deleted;
    }

    public Expense(int id, String name, String price, String subscriptionType)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.subscriptionType = subscriptionType;
        deleted = null;
    }

    public static Expense getExpenseForID(int passedExpenseID)
    {
        for (Expense expense : expenseArrayList)
        {
            if(expense.getId() == passedExpenseID)
                return expense;
        }

        return null;
    }

    public static ArrayList<Expense> nonDeletedExpenses()
    {
        ArrayList<Expense> nonDeleted = new ArrayList<>();
        for(Expense expense : expenseArrayList)
        {
            if(expense.getDeleted() == null)
                nonDeleted.add(expense);
        }

        return nonDeleted;
    }

    /**
     * Id getter
     * @return id
     */
    public int getId() { return id; }

    /**
     * Id setter
     * @param id
     */
    public void setId(int id) { this.id = id; }

    /**
     * name getter
     * @return name
     */
    public String getName() { return name; }

    /**
     * name setter
     * @param name
     */
    public void setName(String name) { this.name = name; }

    /**
     * price getter
     * @return price
     */
    public String getPrice() { return price; }

    /**
     * price setter
     * @param price
     */
    public void setPrice(String price) { this.price = price; }

    /**
     * subscriptionType getter
     * @return subscriptionType
     */
    public String getSubscriptionType() { return subscriptionType; }

    /**
     * subscriptionType setter
     * @param subscriptionType
     */
    public void setSubscriptionType(String subscriptionType) { this.subscriptionType = subscriptionType; }

    /**
     * deleted getter
     * @return deleted
     */
    public Date getDeleted() { return deleted; }

    /**
     * deleted setter
     * @param deleted
     */
    public void setDeleted(Date deleted) { this.deleted = deleted; }
}