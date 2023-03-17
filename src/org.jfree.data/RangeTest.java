package org.jfree.data;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.ExpectedException;

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
    
    @Test
    public void getConstrainBoundaryTest() {   	
    	assertEquals(1, exampleRange.constrain(1), .000000001d);
    }
    
    @Test
    public void getConstrainNegBoundaryTest() {   	
    	assertEquals(-1, exampleRange.constrain(-1), .000000001d);
    }
    
    @Test
    public void getConstrainbetween0andUpperTest() {   	
    	assertEquals(0.5, exampleRange.constrain(0.5), .000000001d);
    }
    
    @Test
    public void getConstrainbetween0andlowerTest() {   	
    	assertEquals(-0.5, exampleRange.constrain(-0.5), .000000001d);
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
    
    @Test
    public void getCombineRangeZeroTest() { 
    	Range combined = new Range(0,0);
    	assertEquals(combined, Range.combine(combined,combined));
    }
    
    //EXPAND TO INCLUDE
    @Test
    public void getExpandToIncludeNullTest() {   
    	Range expected = new Range(2, 2);
    	Range result = Range.expandToInclude(null,2);
    	assertEquals(expected, result);
    }
    
    @Test
    public void getExpandToIncludeUpperTest() {   
    	Range expected = new Range(-1, 2);
    	Range result = Range.expandToInclude(exampleRange,2);
    	assertEquals(expected, result);
    }
    
    @Test
    public void getExpandToIncludeLowerTest() {   
    	Range expected = new Range(-2, 1);
    	Range result =  Range.expandToInclude(exampleRange,-2);
    	assertEquals(expected,result);
    }

    @Test
    public void getExpandToIncludeTest() {   
    	Range expected = new Range(-1, 1);
    	Range result = Range.expandToInclude(exampleRange,1);
    	assertEquals(expected, result);
    }
    @Test
    public void getExpandToIncludeTestValequaltolower() {   
    	Range expected = new Range(-1, 1);
    	Range result = Range.expandToInclude(exampleRange,-1);
    	assertEquals(expected,result);
    }
    
    @Test
    public void getExpandToIncludeTestValequaltozero() {   
    	Range expected = new Range(-1, 1);
    	Range result = Range.expandToInclude(exampleRange,0);
    	assertEquals(expected,result);
    }

    // expand
    @Test
    public void getExpandTest() {
        Range expected = new Range(3, 5);
    	assertEquals(expected, Range.expand(exampleRange,-2,2));
    }
    @Test
    public void getExpandTestEqual(){
        Range expected = new Range(-7,-7);
    	assertEquals(expected, Range.expand(exampleRange,3,-4));
    }
    @Test
    public void getExpandTestEqualMargins(){
        Range expected = new Range(-1,-1);
    	assertEquals(expected, Range.expand(new Range(-1,-1),3,3));
    }

    @Test
    public void getExpandTestUpperEqualsLower(){
        Range expected = new Range(1,1);
        assertEquals(expected, Range.expand(new Range(1,1),-1,1));
    }

    @Test
    public void getExpandTestLargerLower(){
        Range expected = new Range(3,3);
        assertEquals(expected, Range.expand(new Range(-1,1),-4,-1));
    }

    @Test
    public void getExpandTestUpperSmaller(){
        Range expected = new Range(-7,-5);
    	assertEquals(expected, Range.expand(exampleRange,3,-3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExpandTestNullRange(){
        assertNull(Range.expand(null, 3, -3));
    }

    //SHIFT
    @Test(expected = IllegalArgumentException.class)
    public void getShiftTestNull() {
        Range.shift(null, 2, true);
    }

    @Test
    public void getShiftTest() {   
    	Range expected = new Range(0, 3);
    	Range result = Range.shift(exampleRange,2);
    	assertEquals(expected, result);
    }

    @Test
    public void getShiftTestNoZeroCrossing() {
        Range expected = new Range(2, 2);
        Range result = Range.shift(new Range(0,0),2, false);
        assertEquals(expected, result);
    }

    @Test
    public void getShiftTestZero() {   
    	Range expected = new Range(-1, 1);
    	Range result = Range.shift(exampleRange,0);
    	assertEquals(expected, result);
    }
    
    @Test
    public void getShiftTrueTest() {   
    	Range expected = new Range(1, 3);
    	Range result = Range.shift(exampleRange,2, true);
    	assertEquals(expected, result);
    }
    
    @Test
    public void getShiftFalseTest() {   
    	Range expected = new Range(0, 3);
    	Range result =  Range.shift(exampleRange,2, false);
    	assertEquals(expected,result);
    }
    
    @Test
    public void getShiftNegTrueTest() {   
    	Range expected = new Range(-3, -1);
    	Range result = Range.shift(exampleRange,-2, true);
    	assertEquals(expected, result);
    }
    
    @Test
    public void getShiftBetweenZeroAndOne() {   
    	Range expected = new Range(-0.5, 0.5);
    	Range ran = new Range (-0.5,0.5);
    	Range result = Range.shift(ran,0, false);
    	assertEquals(expected, result);
    }
    
    @Test
    public void getShiftBetweenZeroAndOnesecond() {   
    	Range expected = new Range(0.0,0.0);
    	Range ran = new Range (0.0,0.0);
    	Range result = Range.shift(ran,0.0, false);
    	assertEquals(expected, result);
    }
    
    @Test
    public void getShiftBetweenZeroAndOnethird() {   
    	Range expected = new Range(1.1,1.15);
    	Range ran = new Range (0.1,0.15);
    	Range result = Range.shift(ran,1, false);
    	assertEquals(expected, result);
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
    	Range test = new Range(1, 3);
        assertTrue(largeRange.intersects(test));
    }

    @Test
    public void getIntersectsFalse() {
        Range test = new Range(6, 25);
        assertFalse(exampleRange.intersects(test));
    }

    @Test
    public void getIntersectsBoundary() {
        assertTrue(new Range(-1,-1).intersects(-1, 0));
    }

    @Test
    public void getIntersectsBoundaryPoint() {
        assertFalse(new Range(-1,-1).intersects(-1, -1));
    }

    @Test
    public void getIntersectsBoundaryFalse() {
        assertFalse(exampleRange.intersects(-1, -2));
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
        assertTrue(actual);
    }

    @Test
    public void containsTestNegOneInRange() {
        boolean actual = exampleRange.contains(-1);
        assertTrue(actual);
    }

    @Test
    public void containsTestZeroInRange() {
        boolean actual = largeRange.contains(0);
        assertTrue(actual);
    }

    @Test
    public void containsTestTwoOutOfRange() {
        boolean actual = exampleRange.contains(2);
        assertFalse(actual);
    }

    @Test
    public void containsTestLargeNegOutOfRange() {
        boolean actual = largeRange.contains(-99999);
        assertFalse(actual);
    }

    @Test
    public void containsTestLargePosOutOfRange() {
        boolean actual = largeRange.contains(140000);
        assertFalse(actual);
    }
    
    @Test
    public void containsBoundaryTest() {
    	Range newRange = new Range(1,1);
        boolean actual = newRange.contains(0);
        assertFalse(actual);
    }

    //intersect
    @Test
    public void intersectsTestInRange() {
        boolean actual = exampleRange.intersects(-1, 2);
        assertTrue(actual);
    }
    
    @Test
    public void intersectsTestInRangelessb0() {
        boolean actual = exampleRange.intersects(-2, 2);
        assertTrue(actual);
    }
    
    @Test
    public void intersectsTestInRangebiggerb0() {
        boolean actual = exampleRange.intersects(0, 2);
        assertTrue(actual);
    }
    @Test
    public void intersectsTestInlessRange0() {
        boolean actual = exampleRange.intersects(0, -1);
        assertFalse(actual);
    }

    @Test
    public void intersectsTestRange2() {
        boolean actual = exampleRange.intersects(1, 2);
        assertFalse(actual);
    }
    @Test
    public void intersectsTestRange3() {
        boolean actual = exampleRange.intersects(3, 5);
        assertFalse(actual);
    }
    
    @Test
    public void intersectsTestRange4() {
        boolean actual = exampleRange.intersects(0, 0);
        assertTrue(actual);
    }

    @Test
    public void intersectsTestOutOfRangeAbove() {
        boolean actual = largeRange.intersects(139995.876, 149995.875);
        assertFalse(actual);
    }

    @Test
    public void intersectsTestOutOfRangeBelow() {
        boolean actual = largeRange.intersects(-1099995, -99995.876);
        assertFalse(actual);
    }
    //COMBINE IGNORING NaN
    @Test
    public void CombineIgnoreNaNR1NullTest() {
    	Range expected2 = new Range(3, 4); 
        assertEquals(expected2, Range.combineIgnoringNaN(null, expected2));
    }
    @Test
    public void CombineIgnoreNaNR1R2NullTest() {
        assertNull(Range.combineIgnoringNaN(null, null));
    }

    @Test
    public void CombineIgnoreNaNNullAndNaNTest() {
        assertNull(Range.combineIgnoringNaN(new Range(Double.NaN, Double.NaN), null));
    }

    @Test
    public void CombineIgnoreNaNNullAndNaNTestSwapped() {
        assertNull(Range.combineIgnoringNaN(null, new Range(Double.NaN, Double.NaN)));
    }

    @Test
    public void CombineIgnoreNaNOnlyNaNTest() {
        assertNull(Range.combineIgnoringNaN(new Range(Double.NaN, Double.NaN), new Range(Double.NaN, Double.NaN)));
    }

    @Test
    public void CombineIgnoreNaNR2NullTest() {
    	Range expected = new Range(1,2);
        assertEquals(expected, Range.combineIgnoringNaN(expected, null));
    }
    @Test
    public void CombineIgnoreNaNTest() {
    	Range expected = new Range(1,2);
    	Range expected2 = new Range(1,2); 
        assertEquals(expected, Range.combineIgnoringNaN(expected, expected2));
    }

    @Test
    public void CombineTestNaNCatching() {
        Range expected = new Range(-1,Double.NaN);
        Range expected2 = new Range(Double.NaN,1);
        assertEquals(new Range(-1, 1), Range.combineIgnoringNaN(expected, expected2));
    }

    @Test
    public void CombineTestNaNCatchingSwapped() {
        Range expected = new Range(-1,Double.NaN);
        Range expected2 = new Range(Double.NaN,1);
        assertEquals(new Range(-1, 1), Range.combineIgnoringNaN(expected2, expected));
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
    
    //paramtest
    @Test(expected = IllegalArgumentException.class)
    public void scaleTestNullBase() {
    	Range expected = new Range(2,2);
        assertEquals(expected, Range.scale(null, 1));
    }

    @Test
    public void scaleTestEqual0() { 
    	Range ran = new Range(2,2); 
        Range expected = new Range(0,0); 
        assertEquals(expected, Range.scale(ran, 0)); 
    } 

    @Test 
    public void scaleTestBetweenZeroOne() { 
    	Range ran = new Range(2,2); 
        Range expected = new Range(1,1); 
        assertEquals(expected, Range.scale(ran, 0.5)); 

    } 
    
    //EQUALS
    @Test
    public void equalsObjNULL() {
        Object obj = null;
        boolean actual = largeRange.equals(obj);
        assertFalse(actual);
    }
    
    @Test
    public void notEqualsObj() {
        Object obj = largeRange;
        assertNotEquals(exampleRange, obj);
    }
    
    @Test
    public void notEqualsSmallerObj() {  	
        Object obj = exampleRange;
        assertNotEquals(largeRange, obj);
    }
    
    @Test
    public void equalsObj() {
        Object obj = new Range (-99995.875, 1300);
        boolean actual = largeRange.equals(obj);
        assertFalse(actual);
    }
    
    @Test
    public void equalsUpperObj() {
        Object obj = new Range (-2, 1);
        Range ran = new Range (-3,1);
        boolean actual = ran.equals(obj);
        assertFalse(actual);
    }
    
    @Test
    public void equalsEqualObj() {
        Object obj = new Range (-2, 1);
        Range ran = new Range (-2,1);
        boolean actual = ran.equals(obj);
        assertTrue(actual);
    }
    
    @Test
    public void equalslowergreaterObj() {
        Object obj = new Range (-3, 1);
        Range ran = new Range (-2,1);
        boolean actual = ran.equals(obj);
        assertFalse(actual);
    }
    @Test
    public void equalsuppernotequalObj() {
        Object obj = new Range (-100, 1);
        Range ran = new Range (-100,2);
        boolean actual = ran.equals(obj);
        assertFalse(actual);
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
    
    // isNanRange
    @Test
    public void isNanNoDoubleTest() {
        assertFalse(exampleRange.isNaNRange());
    }
    
    @Test
    public void isNaNBothDoubleTest() {
    	Range test = new Range(-1, 1);
        assertFalse(test.isNaNRange());
    }

    @Test
    public void isNaNBothNaNTest() {
        Range test = new Range(Double.NaN, Double.NaN);
        assertTrue(test.isNaNRange());
    }
    
    @Test
    public void isNanOneDoubleTest() {
    	Range ran = new Range(-1.0, 1);
        assertFalse(ran.isNaNRange());
    }
    
    @Test
    public void isNanOtherDoubleTest() {
    	Range ran = new Range(-1, 1.0);
        assertFalse(ran.isNaNRange());
    }
    
    //Testing the exception thrown by constructor
    @Test(expected = IllegalArgumentException.class)
    public void rangeTestNullBase() {
    	Range expected = new Range(2,-2);
        assertEquals(expected, Range.scale(null, 1));
    }
}//f