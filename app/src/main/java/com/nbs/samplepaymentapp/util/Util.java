package com.nbs.samplepaymentapp.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Sidiq on 10/03/2016.
 */
public class Util {
    public static String getCurrency(double price) {
        Locale indonesia = new Locale("id", "ID");
        NumberFormat swedishFormat = NumberFormat.getCurrencyInstance(indonesia);
        return getNormalizedPrice(swedishFormat.format(price));
    }

    public static String getNormalizedPrice(String price) {
        String currency = price.substring(0, 2);
        String value = price.substring(2, price.length());
        String normalizedPrice = currency + " " + value;

        return normalizedPrice;
    }
}
