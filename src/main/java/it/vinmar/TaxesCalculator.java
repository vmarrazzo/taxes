package it.vinmar;

import java.util.Arrays;
import java.util.List;

public class TaxesCalculator
{
    /**
     * Mapping the tax rate categories with enum
     */
    public enum TaxesMapping {

        // Normal Tax rate
        NORMAL(0.1f),

        // Exception Tax rate for  books, food, and medical products
        EXCEPT(0.0f),

        // Importation Tax rate
        IMPORTATION(0.05f);

        // Constructor
        TaxesMapping(float f){percentTax = f;}

        // Tax rate
        private float percentTax;

        // get Tax rate
        public float getPercentTax() { return percentTax; }
    }

    /**
     * Current total
     */
    private double currTotal = 0.0;

    /**
     * Current Sales Taxes
     */
    private double currSalesTaxes = 0.0;

    /**
     * Current Bill
     */
    StringBuilder currBill = new StringBuilder();

    /**
     * Add an entry into bill
     *
     * @param description is the good descriptive name
     * @param amount is the amount without of taxes
     * @param options is the list of applicable tax rates
     */
    public void addGood(String description, float amount, TaxesMapping... options) {

        List<TaxesMapping> args = Arrays.asList(options);

        // the NORMAL and EXCEPT tax rate cannot be applied to same good
        if ( args.contains(TaxesMapping.NORMAL) && args.contains(TaxesMapping.EXCEPT) )
            throw new IllegalArgumentException("On \"" + description + "\" cannot be applied " +
                    " NORMAL and EXCEPT tax rate at same time!");

        float tempRate = 0.0f;

        for ( TaxesMapping t : options )
            tempRate += t.getPercentTax();

        float deltaSalesTaxes = amount * tempRate;
        float finalPrice = amount + deltaSalesTaxes;

        currBill.append(description + ": "+String.format("%.2f", finalPrice) + System.getProperty("line.separator"));
        currTotal += finalPrice;
        currSalesTaxes += deltaSalesTaxes;
    }

    /**
     * It returns printable version of total
     *
     * @return printable version of total
     */
    public String getTotal() {

        // no requirement on its formatting except rounding to 2 decimal
        return String.format("%.2f", currTotal);
    }

    /**
     * It returns the total sales taxes in printable format
     *
     * @return sales taxes in printable version
     */
    public String getSalesTaxes() {
        return String.format("%.2f", Math.round(currSalesTaxes * 20) / 20.0);
    }

    /**
     * It returns a printable version of current Bill
     *
     * @return Bill in printable version
     */
    public String getEntireBill() {

        StringBuilder resp = new StringBuilder(currBill);
        resp.append("Sales Taxes: "+this.getSalesTaxes() + System.getProperty("line.separator"));
        resp.append("Total: "+this.getTotal() + System.getProperty("line.separator"));

        return resp.toString();
    }
}
