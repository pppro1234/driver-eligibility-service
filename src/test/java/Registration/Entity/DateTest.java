package Registration.Entity;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests date construction, comparison, and formatting.
 */
class DateTest {

    // ==================== Builder Constructor Tests ====================

    @Test
    @DisplayName("Builder constructor creates Date with required fields")
    void testBuilderConstructor() {
        Date date = new Date.Builder(2024, 6, 15).build();

        assertEquals(2024, date.getYear());
        assertEquals(6, date.getMonth());
        assertEquals(15, date.getDay());
        assertEquals(0, date.getHour());  // Default value
        assertEquals(0, date.getMinute());  // Default value
    }

    @Test
    @DisplayName("Builder creates Date with all fields")
    void testBuilderWithAllFields() {
        Date date = new Date.Builder(2024, 6, 15)
                .hour(14)
                .minute(30)
                .build();

        assertEquals(2024, date.getYear());
        assertEquals(6, date.getMonth());
        assertEquals(15, date.getDay());
        assertEquals(14, date.getHour());
        assertEquals(30, date.getMinute());
    }

    // ==================== Builder Method Chaining Tests ====================

    @Test
    @DisplayName("Builder year() method works correctly")
    void testBuilderYear() {
        Date date = new Date.Builder(2020, 1, 1)
                .year(2025)
                .build();

        assertEquals(2025, date.getYear());
    }

    @Test
    @DisplayName("Builder month() method works correctly")
    void testBuilderMonth() {
        Date date = new Date.Builder(2020, 1, 1)
                .month(12)
                .build();

        assertEquals(12, date.getMonth());
    }

    @Test
    @DisplayName("Builder day() method works correctly")
    void testBuilderDay() {
        Date date = new Date.Builder(2020, 1, 1)
                .day(25)
                .build();

        assertEquals(25, date.getDay());
    }

    @Test
    @DisplayName("Builder hour() method works correctly")
    void testBuilderHour() {
        Date date = new Date.Builder(2020, 1, 1)
                .hour(23)
                .build();

        assertEquals(23, date.getHour());
    }

    @Test
    @DisplayName("Builder minute() method works correctly")
    void testBuilderMinute() {
        Date date = new Date.Builder(2020, 1, 1)
                .minute(59)
                .build();

        assertEquals(59, date.getMinute());
    }

    @Test
    @DisplayName("Builder supports full method chaining")
    void testBuilderFullChaining() {
        Date date = new Date.Builder(2000, 1, 1)
                .year(2024)
                .month(12)
                .day(31)
                .hour(23)
                .minute(59)
                .build();

        assertEquals(2024, date.getYear());
        assertEquals(12, date.getMonth());
        assertEquals(31, date.getDay());
        assertEquals(23, date.getHour());
        assertEquals(59, date.getMinute());
    }

    // ==================== getDateDiff() Tests ====================

    @Test
    @DisplayName("getDateDiff - calculates positive difference")
    void testGetDateDiffPositive() {
        Date date1 = new Date.Builder(2024, 6, 15).hour(14).minute(30).build();
        Date date2 = new Date.Builder(2020, 3, 10).hour(10).minute(15).build();

        Date diff = date1.getDateDiff(date2);

        assertEquals(4, diff.getYear());
        assertEquals(3, diff.getMonth());
        assertEquals(5, diff.getDay());
        assertEquals(4, diff.getHour());
        assertEquals(15, diff.getMinute());
    }

    @Test
    @DisplayName("getDateDiff - calculates negative difference")
    void testGetDateDiffNegative() {
        Date date1 = new Date.Builder(2020, 3, 10).hour(10).minute(15).build();
        Date date2 = new Date.Builder(2024, 6, 15).hour(14).minute(30).build();

        Date diff = date1.getDateDiff(date2);

        assertEquals(-4, diff.getYear());
        assertEquals(-3, diff.getMonth());
        assertEquals(-5, diff.getDay());
        assertEquals(-4, diff.getHour());
        assertEquals(-15, diff.getMinute());
    }

    @Test
    @DisplayName("getDateDiff - same dates returns zero difference")
    void testGetDateDiffSameDates() {
        Date date1 = new Date.Builder(2024, 6, 15).hour(14).minute(30).build();
        Date date2 = new Date.Builder(2024, 6, 15).hour(14).minute(30).build();

        Date diff = date1.getDateDiff(date2);

        assertEquals(0, diff.getYear());
        assertEquals(0, diff.getMonth());
        assertEquals(0, diff.getDay());
        assertEquals(0, diff.getHour());
        assertEquals(0, diff.getMinute());
    }

    @Test
    @DisplayName("getDateDiff - mixed positive and negative components")
    void testGetDateDiffMixed() {
        Date date1 = new Date.Builder(2024, 3, 20).hour(8).minute(45).build();
        Date date2 = new Date.Builder(2020, 6, 10).hour(14).minute(30).build();

        Date diff = date1.getDateDiff(date2);

        assertEquals(4, diff.getYear());
        assertEquals(-3, diff.getMonth());
        assertEquals(10, diff.getDay());
        assertEquals(-6, diff.getHour());
        assertEquals(15, diff.getMinute());
    }

    @Test
    @DisplayName("getDateDiff - with default time values")
    void testGetDateDiffDefaultTime() {
        Date date1 = new Date.Builder(2024, 12, 31).build();
        Date date2 = new Date.Builder(2024, 1, 1).build();

        Date diff = date1.getDateDiff(date2);

        assertEquals(0, diff.getYear());
        assertEquals(11, diff.getMonth());
        assertEquals(30, diff.getDay());
        assertEquals(0, diff.getHour());
        assertEquals(0, diff.getMinute());
    }

    // ==================== Getter Tests ====================

    @Test
    @DisplayName("getYear returns correct value")
    void testGetYear() {
        Date date = new Date.Builder(2024, 6, 15).build();
        assertEquals(2024, date.getYear());
    }

    @Test
    @DisplayName("getMonth returns correct value")
    void testGetMonth() {
        Date date = new Date.Builder(2024, 6, 15).build();
        assertEquals(6, date.getMonth());
    }

    @Test
    @DisplayName("getDay returns correct value")
    void testGetDay() {
        Date date = new Date.Builder(2024, 6, 15).build();
        assertEquals(15, date.getDay());
    }

    @Test
    @DisplayName("getHour returns correct value")
    void testGetHour() {
        Date date = new Date.Builder(2024, 6, 15).hour(14).build();
        assertEquals(14, date.getHour());
    }

    @Test
    @DisplayName("getMinute returns correct value")
    void testGetMinute() {
        Date date = new Date.Builder(2024, 6, 15).minute(30).build();
        assertEquals(30, date.getMinute());
    }

    // ==================== Setter Tests ====================

    @Test
    @DisplayName("setYear updates year correctly")
    void testSetYear() {
        Date date = new Date.Builder(2024, 6, 15).build();
        date.setYear(2025);
        assertEquals(2025, date.getYear());
    }

    @Test
    @DisplayName("setMonth updates month correctly")
    void testSetMonth() {
        Date date = new Date.Builder(2024, 6, 15).build();
        date.setMonth(12);
        assertEquals(12, date.getMonth());
    }

    @Test
    @DisplayName("setDay updates day correctly")
    void testSetDay() {
        Date date = new Date.Builder(2024, 6, 15).build();
        date.setDay(25);
        assertEquals(25, date.getDay());
    }

    @Test
    @DisplayName("setHour updates hour correctly")
    void testSetHour() {
        Date date = new Date.Builder(2024, 6, 15).build();
        date.setHour(23);
        assertEquals(23, date.getHour());
    }

    @Test
    @DisplayName("setMinute updates minute correctly")
    void testSetMinute() {
        Date date = new Date.Builder(2024, 6, 15).build();
        date.setMinute(59);
        assertEquals(59, date.getMinute());
    }

    // ==================== equals() Tests ====================

    @Test
    @DisplayName("equals - same object returns true")
    void testEqualsSameObject() {
        Date date = new Date.Builder(2024, 6, 15).hour(14).minute(30).build();
        assertEquals(date, date);
    }

    @Test
    @DisplayName("equals - equal objects return true")
    void testEqualsEqualObjects() {
        Date date1 = new Date.Builder(2024, 6, 15).hour(14).minute(30).build();
        Date date2 = new Date.Builder(2024, 6, 15).hour(14).minute(30).build();
        assertEquals(date1, date2);
    }

    @Test
    @DisplayName("equals - null returns false")
    void testEqualsNull() {
        Date date = new Date.Builder(2024, 6, 15).build();
        assertNotEquals(null, date);
    }

    @Test
    @DisplayName("equals - different class returns false")
    void testEqualsDifferentClass() {
        Date date = new Date.Builder(2024, 6, 15).build();
        String notADate = "2024-06-15";
        assertNotEquals(date, notADate);
    }

    @Test
    @DisplayName("equals - different year returns false")
    void testEqualsDifferentYear() {
        Date date1 = new Date.Builder(2024, 6, 15).build();
        Date date2 = new Date.Builder(2025, 6, 15).build();
        assertNotEquals(date1, date2);
    }

    @Test
    @DisplayName("equals - different month returns false")
    void testEqualsDifferentMonth() {
        Date date1 = new Date.Builder(2024, 6, 15).build();
        Date date2 = new Date.Builder(2024, 7, 15).build();
        assertNotEquals(date1, date2);
    }

    @Test
    @DisplayName("equals - different day returns false")
    void testEqualsDifferentDay() {
        Date date1 = new Date.Builder(2024, 6, 15).build();
        Date date2 = new Date.Builder(2024, 6, 16).build();
        assertNotEquals(date1, date2);
    }

    @Test
    @DisplayName("equals - different hour returns false")
    void testEqualsDifferentHour() {
        Date date1 = new Date.Builder(2024, 6, 15).hour(14).build();
        Date date2 = new Date.Builder(2024, 6, 15).hour(15).build();
        assertNotEquals(date1, date2);
    }

    @Test
    @DisplayName("equals - different minute returns false")
    void testEqualsDifferentMinute() {
        Date date1 = new Date.Builder(2024, 6, 15).minute(30).build();
        Date date2 = new Date.Builder(2024, 6, 15).minute(31).build();
        assertNotEquals(date1, date2);
    }

    @Test
    @DisplayName("equals - default time values are equal")
    void testEqualsDefaultTimeValues() {
        Date date1 = new Date.Builder(2024, 6, 15).build();
        Date date2 = new Date.Builder(2024, 6, 15).hour(0).minute(0).build();
        assertEquals(date1, date2);
    }

    // ==================== hashCode() Tests ====================

    @Test
    @DisplayName("hashCode - equal objects have same hash code")
    void testHashCodeEqualObjects() {
        Date date1 = new Date.Builder(2024, 6, 15).hour(14).minute(30).build();
        Date date2 = new Date.Builder(2024, 6, 15).hour(14).minute(30).build();
        assertEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    @DisplayName("hashCode - different objects may have different hash codes")
    void testHashCodeDifferentObjects() {
        Date date1 = new Date.Builder(2024, 6, 15).build();
        Date date2 = new Date.Builder(2025, 7, 16).build();
        // Note: Different objects are not guaranteed to have different hash codes,
        // but in practice they usually do
        assertNotEquals(date1.hashCode(), date2.hashCode());
    }

    @Test
    @DisplayName("hashCode - consistent on multiple calls")
    void testHashCodeConsistent() {
        Date date = new Date.Builder(2024, 6, 15).hour(14).minute(30).build();
        int hash1 = date.hashCode();
        int hash2 = date.hashCode();
        int hash3 = date.hashCode();
        assertEquals(hash1, hash2);
        assertEquals(hash2, hash3);
    }

    // ==================== Edge Cases ====================

    @Test
    @DisplayName("Date with zero values")
    void testZeroValues() {
        Date date = new Date.Builder(0, 0, 0).hour(0).minute(0).build();

        assertEquals(0, date.getYear());
        assertEquals(0, date.getMonth());
        assertEquals(0, date.getDay());
        assertEquals(0, date.getHour());
        assertEquals(0, date.getMinute());
    }

    @Test
    @DisplayName("Date with negative values")
    void testNegativeValues() {
        Date date = new Date.Builder(-1, -1, -1).hour(-1).minute(-1).build();

        assertEquals(-1, date.getYear());
        assertEquals(-1, date.getMonth());
        assertEquals(-1, date.getDay());
        assertEquals(-1, date.getHour());
        assertEquals(-1, date.getMinute());
    }

    @Test
    @DisplayName("Date with large values")
    void testLargeValues() {
        Date date = new Date.Builder(9999, 12, 31).hour(23).minute(59).build();

        assertEquals(9999, date.getYear());
        assertEquals(12, date.getMonth());
        assertEquals(31, date.getDay());
        assertEquals(23, date.getHour());
        assertEquals(59, date.getMinute());
    }

    @Test
    @DisplayName("Setter after getter returns updated value")
    void testSetterAfterGetter() {
        Date date = new Date.Builder(2024, 6, 15).build();

        assertEquals(2024, date.getYear());
        date.setYear(2030);
        assertEquals(2030, date.getYear());

        assertEquals(6, date.getMonth());
        date.setMonth(1);
        assertEquals(1, date.getMonth());

        assertEquals(15, date.getDay());
        date.setDay(28);
        assertEquals(28, date.getDay());

        assertEquals(0, date.getHour());
        date.setHour(12);
        assertEquals(12, date.getHour());

        assertEquals(0, date.getMinute());
        date.setMinute(45);
        assertEquals(45, date.getMinute());
    }

    @Test
    @DisplayName("Multiple builds from same builder")
    void testMultipleBuilds() {
        Date.Builder builder = new Date.Builder(2024, 6, 15);

        Date date1 = builder.build();
        Date date2 = builder.hour(10).build();
        Date date3 = builder.minute(30).build();

        assertEquals(2024, date1.getYear());
        assertEquals(10, date2.getHour());
        assertEquals(30, date3.getMinute());
    }
}