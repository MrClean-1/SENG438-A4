package org.jfree.data;

import static org.junit.Assert.*;

import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.util.Arrays;

public class DataUtilitiesTest extends DataUtilities {
    static Mockery mockingContext;
    static Values2D values;

    @BeforeClass
    public static void setUpBeforeClass() {
        mockingContext = new Mockery();
        values = mockingContext.mock(Values2D.class);
    }

    @Test
    public void equalForNULL() {
        boolean result = DataUtilities.equal(null, null);
        assertEquals(true, result);
    }

    @Test
    public void equalOneNull() {
        double [][] a = {{0},{1}};
        boolean result = DataUtilities.equal(a, null);
        assertEquals(false, result);
    }

    @Test
    public void equalDiffLength() {
        double [][] a = {{0},{1}};
        double [][] b = {{0},{1}, {2}};
        boolean result = DataUtilities.equal(a, b);
        assertEquals(false, result);
    }

    @Test
    public void equalSameArrays() {
        double [][] a = {{0},{1},{2}};
        double [][] b = {{0},{1},{2}};
        boolean result = DataUtilities.equal(a, b);
        assertEquals(true, result);
    }

    @Test
    public void equalDiffArrays() {
        double [][] a = {{0},{1},{2}};
        double [][] b = {{1},{2},{3}};
        boolean result = DataUtilities.equal(a, b);
        assertEquals(false, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCloneNULL(){
        DataUtilities.clone(null);
    }

    @Test
    public void testCloneWithArray() {
        double[][] a = {{1}, {2}, {3}};
        double [][] result = DataUtilities.clone(a);
        assertEquals(Arrays.deepToString(a), Arrays.deepToString(result));
    }

    @Test
    public void calculateColumnTotalForFiveValues() {
        // setup
        mockingContext.checking(new Expectations() {
            {
                one(values).getRowCount();
                will(returnValue(5));
                one(values).getValue(0, 0);
                will(returnValue(7.5));
                one(values).getValue(1, 0);
                will(returnValue(2.5));
                one(values).getValue(2, 0);
                will(returnValue(0));
                one(values).getValue(3, 0);
                will(returnValue(-10.4));
                one(values).getValue(4, 0);
                will(returnValue(5.4));
            }
        });
        // test
        double result = DataUtilities.calculateColumnTotal(values, 0);
        // verify
        assertEquals(5.0, result, .000000001d);
    }

    @Test
    public void calculateColumnTotalForTwoValues() {
        // setup
        mockingContext.checking(new Expectations() {
            {
                one(values).getRowCount();
                will(returnValue(2));
                one(values).getValue(0, 1);
                will(returnValue(7.5));
                one(values).getValue(1, 1);
                will(returnValue(-2.5));
            }
        });
        // test
        double result = DataUtilities.calculateColumnTotal(values, 1);
        // verify
        assertEquals(5.0, result, .000000001d);
    }

    @Test
    public void calculateColumnTotalValidRows() {
        // setup
        mockingContext.checking(new Expectations() {
            {
                one(values).getRowCount();
                will(returnValue(5));
                one(values).getValue(0, 0);
                will(returnValue(7.5));
                one(values).getValue(1, 0);
                will(returnValue(2.5));
                one(values).getValue(2, 0);
                will(returnValue(0));
                one(values).getValue(3, 0);
                will(returnValue(-10.4));
                one(values).getValue(4, 0);
                will(returnValue(5.4));
            }
        });
        int[] validRows = {0,1,4};

        // test
        double result = DataUtilities.calculateColumnTotal(values, 0, validRows);
        // verify
        assertEquals(15.4, result, .000000001d);
    }

    @Test
    public void calculateRowTotalForFiveValues() {
        // setup
        mockingContext.checking(new Expectations() {
            {
                one(values).getColumnCount();
                will(returnValue(5));
                one(values).getValue(0, 0);
                will(returnValue(7.5));
                one(values).getValue(0, 1);
                will(returnValue(2.5));
                one(values).getValue(0, 2);
                will(returnValue(0));
                one(values).getValue(0, 3);
                will(returnValue(-10.4));
                one(values).getValue(0, 4);
                will(returnValue(5.4));
            }
        });
        // test
        double result = DataUtilities.calculateRowTotal(values, 0);
        // verify
        assertEquals(5.0, result, .000000001d);
    }

    @Test
    public void calculateRowTotalForNegativeTwoValues() {
        // setup
        mockingContext.checking(new Expectations() {
            {
                one(values).getColumnCount();
                will(returnValue(2));
                one(values).getValue(1, 0);
                will(returnValue(-7.5));
                one(values).getValue(1, 1);
                will(returnValue(2.5));
            }
        });
        // test
        double result = DataUtilities.calculateRowTotal(values, 1);
        // verify
        assertEquals(-5.0, result, .000000001d);
    }

    @Test
    public void calculateRowTotalValidRows() {
        // setup
        mockingContext.checking(new Expectations() {
            {
                one(values).getColumnCount();
                will(returnValue(5));
                one(values).getValue(0, 0);
                will(returnValue(7.5));
                one(values).getValue(0, 1);
                will(returnValue(2.5));
                one(values).getValue(0, 2);
                will(returnValue(0));
                one(values).getValue(0, 3);
                will(returnValue(-10.4));
                one(values).getValue(0, 4);
                will(returnValue(5.4));
            }
        });
        int[] validRows = {0,1,4};
        // test
        double result = DataUtilities.calculateRowTotal(values, 0, validRows);
        // verify
        assertEquals(15.4, result, .000000001d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNumberArrayTestNull() {
        DataUtilities.createNumberArray(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNumberArray2DTestNull() {
        DataUtilities.createNumberArray2D(null);
    }

    @Test
    public void createNumberArrayTestWithValues() {
        // setup
        double[] doubleArray = {1.2, 8.65, -10.9, 5.4, -4.35};
        //test
        Number[] numberArray = DataUtilities.createNumberArray(doubleArray);
        //verify
        assertEquals(Arrays.toString(doubleArray), Arrays.toString(numberArray));
    }

    @Test
    public void createNumberArray2DTestWithValues() {
        // setup
        double[][] doubleArray = {
                {1.2, 8.65, -10.9, 5.4, -4.35},
                {1.2, 8.65, -10.9, 5.4, -4.35},
                {1.2, 8.65, -10.9, 5.4, -4.35},
                {1.2, 8.65, -10.9, 5.4, -4.35},
        };
        //test
        Number[][] numberArray = DataUtilities.createNumberArray2D(doubleArray);
        // Verify
        assertEquals(Arrays.deepToString(doubleArray), Arrays.deepToString(numberArray));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCumulativePercentagesTestNull() {
        DataUtilities.getCumulativePercentages(null);
    }

    @Test
    public void getCumulativePercentagesTest() {
        //setup
        DefaultKeyedValues test = new DefaultKeyedValues();
        test.addValue((Comparable<?>) 0, 5);
        test.addValue((Comparable<?>) 1, 9);
        test.addValue((Comparable<?>) 2, 2);
        //test
        KeyedValues result = DataUtilities.getCumulativePercentages(test);
        //verify
        assertEquals(0.875, result.getValue(1).doubleValue(), .000000001d);
    }
}
