package it.vinmar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import it.vinmar.TaxesCalculator.TaxesMapping;

/**
 * Created by vincenzo on 26/02/16.
 */
public class BasicTest {

    @Test
    public void TestOne() {

        TaxesCalculator underTest = new TaxesCalculator();

        underTest.addGood("1 book", 12.49f, TaxesMapping.EXCEPT);
        underTest.addGood("1 music CD", 14.99f, TaxesMapping.NORMAL);
        underTest.addGood("1 chocolate bar", 0.85f, TaxesMapping.EXCEPT);

        assertEquals( "29.83", underTest.getTotal());
        assertEquals( "1.50", underTest.getSalesTaxes());

        String expectedBill =
                "1 book: 12.49" + System.getProperty("line.separator") +
                "1 music CD: 16.49" + System.getProperty("line.separator") +
                "1 chocolate bar: 0.85" + System.getProperty("line.separator") +
                "Sales Taxes: 1.50" + System.getProperty("line.separator") +
                "Total: 29.83" + System.getProperty("line.separator");

        assertEquals( expectedBill, underTest.getEntireBill());
    }

    @Test
    public void TestTwo() {

        TaxesCalculator underTest = new TaxesCalculator();

        underTest.addGood("1 imported box of chocolates", 10.00f, TaxesMapping.EXCEPT, TaxesMapping.IMPORTATION);
        underTest.addGood("1 imported bottle of perfume", 47.50f, TaxesMapping.NORMAL, TaxesMapping.IMPORTATION);

        assertEquals( "65.13", underTest.getTotal());
        assertEquals( "7.65", underTest.getSalesTaxes());

        String expectedBill =
                "1 imported box of chocolates: 10.50" + System.getProperty("line.separator") +
                // maybe a mistake on exercise text ????
                "1 imported bottle of perfume: 54.63" + System.getProperty("line.separator") +
                "Sales Taxes: 7.65" + System.getProperty("line.separator") +
                "Total: 65.13" + System.getProperty("line.separator");

        assertEquals( expectedBill, underTest.getEntireBill());
    }

    @Test
    public void TestThree() {

        TaxesCalculator underTest = new TaxesCalculator();

        underTest.addGood("1 imported bottle of perfume", 27.99f, TaxesMapping.NORMAL, TaxesMapping.IMPORTATION);
        underTest.addGood("1 bottle of perfume", 18.99f, TaxesMapping.NORMAL);
        underTest.addGood("1 packet of headache pills", 9.75f, TaxesMapping.EXCEPT);
        underTest.addGood("1 imported box of chocolates", 11.25f, TaxesMapping.EXCEPT, TaxesMapping.IMPORTATION);

        assertEquals( "74.64", underTest.getTotal());
        assertEquals( "6.65", underTest.getSalesTaxes());

        String expectedBill =
                "1 imported bottle of perfume: 32.19" + System.getProperty("line.separator") +
                "1 bottle of perfume: 20.89" + System.getProperty("line.separator") +
                "1 packet of headache pills: 9.75" + System.getProperty("line.separator") +
                // maybe a mistake on exercise text ????
                "1 imported box of chocolates: 11.81" + System.getProperty("line.separator") +
                "Sales Taxes: 6.65" + System.getProperty("line.separator") +
                "Total: 74.64" + System.getProperty("line.separator");

        assertEquals( expectedBill, underTest.getEntireBill());
    }

    @Test(expected=IllegalArgumentException.class)
    public void TestUnexpectedTaxRateCombination() {

        TaxesCalculator underTest = new TaxesCalculator();

        underTest.addGood("1 food but not food", 27.99f, TaxesMapping.NORMAL, TaxesMapping.EXCEPT);

        fail("It's not possible to have normal tax rate and except tax rate!");
    }
}
