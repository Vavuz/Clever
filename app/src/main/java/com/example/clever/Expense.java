package com.example.clever;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;


public class Expense {

    // Properties instantiation
    static LinkedHashMap<String, Integer> subscriptionsDict = new LinkedHashMap<String, Integer>();
    public static ArrayList<Expense> expenseArrayList = new ArrayList<>();
    public static String EXPENSE_EDIT_EXTRA =  "expenseEdit";
    private int id;
    private String name;
    private String price;
    private String subscriptionType;
    private Date deleted;

    /**
     * Constructor one
     * @param id
     * @param name
     * @param price
     * @param subscriptionType
     * @param deleted
     */
    public Expense(int id, String name, String price, String subscriptionType, Date deleted)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.subscriptionType = subscriptionType;
        this.deleted = deleted;
        populateDictionary();
    }

    /**
     * Constructor two
     * @param id
     * @param name
     * @param price
     * @param subscriptionType
     */
    public Expense(int id, String name, String price, String subscriptionType)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.subscriptionType = subscriptionType;
        deleted = null;
    }

    /**
     * Populates the dictionary of time frames
     */
    public void populateDictionary() {
        this.subscriptionsDict.put("Daily", 1);
        this.subscriptionsDict.put("Weekly", 7);
        this.subscriptionsDict.put("Every two weeks", 14);
        this.subscriptionsDict.put("Every three weeks", 21);
        this.subscriptionsDict.put("Every four weeks", 28);
        this.subscriptionsDict.put("Monthly", 30);
        this.subscriptionsDict.put("Every two months", 60);
        this.subscriptionsDict.put("Every three months", 90);
        this.subscriptionsDict.put("Every four months", 120);
        this.subscriptionsDict.put("Every five months", 150);
        this.subscriptionsDict.put("Every six months", 180);
        this.subscriptionsDict.put("Yearly", 365);
        this.subscriptionsDict.put("Every two years", 730);
        this.subscriptionsDict.put("Every three years", 1095);
        this.subscriptionsDict.put("Every four years", 1460);
        this.subscriptionsDict.put("Every five years", 1825);
        this.subscriptionsDict.put("Every ten years", 3650);
    }

    /**
     * Returns an expense with a certain ID
     * @param passedExpenseID
     * @return
     */
    public static Expense getExpenseForID(int passedExpenseID)
    {
        for (Expense expense : expenseArrayList)
        {
            if(expense.getId() == passedExpenseID)
                return expense;
        }

        return null;
    }

    /**
     * Returns the array of nonDeletedExpenses
     * @return
     */
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