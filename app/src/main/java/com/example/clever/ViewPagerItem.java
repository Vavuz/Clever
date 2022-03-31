package com.example.clever;

import java.text.DecimalFormat;


public class ViewPagerItem {
    // Properties instantiation
    private static final DecimalFormat df = new DecimalFormat("0.00");
    String type;
    String total;

    /**
     * Constructor
     * @param type
     * @param total
     */
    public ViewPagerItem(String type, float total) {
        this.type = type;
        this.total = df.format(total);
    }
}
