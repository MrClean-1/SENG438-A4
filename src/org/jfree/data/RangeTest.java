package org.jfree.data;

import static org.junit.Assert.*;

import org.jfree.data.Range;
import org.junit.*;

public class RangeTest {
    private static Range exampleRange, largeRange;

    @BeforeClass
    public static void setUpBeforeClass() {
        exampleRange = new Range(-1, 1);
        largeRange = new Range(-99995.875, 139995.875);

    }
    
    // CONSTRAIN
    @Test
    public void getConstrainInRangeTest() {   	
        assertEquals(0, exampleRange.constrain(0), .000000001d);
    }
    
    @Test
    public void getConstrainOutBiggerTest() {   	
        assertEquals(1, exampleRange.constrain(2), .000000001d);
    }
    
    @Test
    public void getConstrainOutSmallerTest() {   	
        assertEquals(-1, exampleRange.constrain(-2), .000000001d);
    }
    
    // COMBINE
    @Test
    public void getCombineTest() {   
    	Range combined = new Range(-99995.875, 139995.875);
    	assertEquals(combined, Range.combine(exampleRange, largeRange));
    }
    
    @Test
    public void getCombineRange1NullTest() {   
    	assertEquals(largeRange, Range.combine(null, largeRange));
    }
    
    @Test
    public void getCombineRange2NullTest() {   
    	assertEquals(exampleRange, Range.combine(exampleRange, null));
    }
    
    //EXPAND TO INCLUDE
    @Test
    public void getExpandToIncludeNullTest() {   
    	Range expected = new Range(2, 2);
    	assertEquals(expected, Range.expandToInclude(null,2));
    }
    
    @Test
    public void getExpandToIncludeUpperTest() {   
    	Range expected = new Range(-1, 2);
    	assertEquals(expected, Range.expandToInclude(exampleRange,2));
    }
    
    @Test
    public void getExpandToIncludeLowerTest() {   
    	Range expected = new Range(-2, 1);
    	assertEquals(expected, Range.expandToInclude(exampleRange,-2));
    }

    @Test
    public void getExpandToIncludeTest() {   
    	Range expected = new Range(-1, 1);
    	assertEquals(expected, Range.expandToInclude(exampleRange,1));
    }

    @Test
    public void getExpandTest() {
        Range expected = new Range(3, 5);
    	assertEquals(expected, Range.expand(exampleRange,-2,2));
    }
    
//    @Test
//    public void getExpandLowerBiggerTest() {   
//    	Range expected = new Range(0, 0);
//    	assertEquals(expected, Range.expand(exampleRange,2,-2));
//    }
    
    //SHIFT
    @Test
    public void getShiftTest() {   
    	Range expected = new Range(0, 3);
    	assertEquals(expected, Range.shift(exampleRange,2));
    }
    
    @Test
    public void getShiftTrueTest() {   
    	Range expected = new Range(1, 3);
    	assertEquals(expected, Range.shift(exampleRange,2, true));
    }
    
    
    //CENTRAL
    @Test
    public void getCentralValueTestZero() {
        assertEquals(0, exampleRange.getCentralValue(), .000000001d);
    }

    @Test
    public void getCentralValueTestLargeRange() {
        assertEquals(20000, largeRange.getCentralValue(), .000000001d);
    }

    //length
    @Test
    public void getLengthTestTwo() {
        assertEquals(2, exampleRange.getLength(), .000000001d);
    }

    @Test
    public void getLengthTestLargeRange() {
        assertEquals(239991.75, largeRange.getLength(), .000000001d);
    }

    //lowerBound
    @Test
    public void getLowerBoundTestNegOne() {
        assertEquals(-1, exampleRange.getLowerBound(), .000000001d);
    }

    @Test
    public void getLowerBoundTestLargeRange() {
        assertEquals(-99995.875, largeRange.getLowerBound(), .000000001d);
    }

    @Test
    public void getIntersects() {
    	Range expected = new Range(1, 3);
        assertEquals(true, largeRange.intersects(expected));
    }
    //upperBound
    @Test
    public void getUpperBoundTestOne() {
        assertEquals(1, exampleRange.getUpperBound(), .000000001d);
    }

    @Test
    public void getUpperBoundTestLargeRange() {
        assertEquals(139995.875, largeRange.getUpperBound(), .000000001d);
    }

    //contains
    @Test
    public void containsTestOneInRange() {
        boolean actual = exampleRange.contains(1);
        assertEquals(true, actual);
    }

    @Test
    public void containsTestNegOneInRange() {
        boolean actual = exampleRange.contains(-1);
        assertEquals(true, actual);
    }

    @Test
    public void containsTestZeroInRange() {
        boolean actual = largeRange.contains(0);
        assertEquals(true, actual);
    }

    @Test
    public void containsTestTwoOutOfRange() {
        boolean actual = exampleRange.contains(2);
        assertEquals(false, actual);
    }

    @Test
    public void containsTestLargeNegOutOfRange() {
        boolean actual = largeRange.contains(-99999);
        assertEquals(false, actual);
    }

    @Test
    public void containsTestLargePosOutOfRange() {
        boolean actual = largeRange.contains(140000);
        assertEquals(false, actual);
    }
    
    @Test
    public void containsBoundaryTest() {
    	Range newRange = new Range(1,1);
        boolean actual = exampleRange.contains(0);
        assertEquals(true, actual);
    }

    //intersect
    @Test
    public void intersectsTestInRange() {
        boolean actual = exampleRange.intersects(-1, 2);
        assertEquals(true, actual);
    }

    @Test
    public void intersectsTestOutOfRangeAbove() {
        boolean actual = largeRange.intersects(139995.876, 149995.875);
        assertEquals(false, actual);
    }

    @Test
    public void intersectsTestOutOfRangeBelow() {
        boolean actual = largeRange.intersects(-1099995, -99995.876);
        assertEquals(false, actual);
    }
    //COMBINE IGNORING NaN
    @Test
    public void CombineIgnoreNaNR1NullTest() {
    	Range expected = null;
    	Range expected2 = new Range(3, 4); 
        assertEquals(expected2, Range.combineIgnoringNaN(expected, expected2));
    }
    @Test
    public void CombineIgnoreNaNR1R2NullTest() {
    	Range expected = null;
    	Range expected2 = null; 
        assertEquals(null, Range.combineIgnoringNaN(expected, expected2));
    }
    @Test
    public void CombineIgnoreNaNR2NullTest() {
    	Range expected = new Range(1,2);
    	Range expected2 = null; 
        assertEquals(expected, Range.combineIgnoringNaN(expected, expected2));
    }
    @Test
    public void CombineIgnoreNaNTest() {
    	Range expected = new Range(1,2);
    	Range expected2 = new Range(1,2); 
        assertEquals(expected, Range.combineIgnoringNaN(expected, expected2));
    }
    
    // SCALE
    @Test(expected = IllegalArgumentException.class)
    public void scaleTest() {
    	Range expected = new Range(2,2);
        assertEquals(expected, Range.scale(expected, -1));
    }
    
    @Test
    public void scaleTestGreaterThan0() {
    	Range expected = new Range(2,2);
        assertEquals(expected, Range.scale(expected, 1));
    }

    @Test
    public void scaleTestEqual0() { 
        Range expected = new Range(2,2); 
        assertEquals(expected, Range.scale(expected, 0)); 
    } 

    @Test 
    public void scaleTestBetweenZeroOne() { 
        Range expected = new Range(2,2); 
        assertEquals(expected, Range.scale(expected, 0.5)); 

    } 
    
    //EQUALS
    @Test
    public void equalsObjNULL() {
        Object obj = null;
        boolean actual = largeRange.equals(obj);
        assertEquals(false, actual);
    }
    @Test
    public void notEqualsObj() {
        Object obj = largeRange;
        assertEquals(false, exampleRange.equals(obj));
    }
    
    @Test
    public void equalsObj() {
        Object obj = new Range (-99995.875, 1300);
        boolean actual = largeRange.equals(obj);
        assertEquals(false, actual);
    }
    
    // HASH CODE
    @Test
    public void hashCodeTest() {
    	assertEquals(-31457280, exampleRange.hashCode());
    }

    @Test
    public void toStringTest() {
        assertEquals("Range[-1.0,1.0]", exampleRange.toString());
    }
}