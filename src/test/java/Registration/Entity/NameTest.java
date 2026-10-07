package Registration.Entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests name construction, equality, hashing, and formatting.
 */
class NameTest {

    private Name testName;
    private Name sameName;
    private Name differentFirstName;
    private Name differentLastName;
    private Name differentName;

    /**
     * Sets up test fixtures before each test method.
     * Creates various Name instances for testing different scenarios.
     */
    @BeforeEach
    void setUp() {
        testName = new Name("John", "Doe");
        sameName = new Name("John", "Doe");
        differentFirstName = new Name("Jane", "Doe");
        differentLastName = new Name("John", "Smith");
        differentName = new Name("Jane", "Smith");
    }

    // ============ Constructor Tests ============

    /**
     * Tests creating a Name with valid first and last names.
     * Verifies that the constructor correctly initializes both fields.
     */
    @Test
    void testConstructorWithValidNames() {
        Name name = new Name("Alice", "Johnson");

        assertNotNull(name);
        assertEquals("Alice", name.getFirstName());
        assertEquals("Johnson", name.getLastName());
    }

    /**
     * Tests creating a Name with null first name.
     * Verifies that null values are accepted.
     */
    @Test
    void testConstructorWithNullFirstName() {
        Name name = new Name(null, "Doe");

        assertNull(name.getFirstName());
        assertEquals("Doe", name.getLastName());
    }

    /**
     * Tests creating a Name with null last name.
     * Verifies that null values are accepted.
     */
    @Test
    void testConstructorWithNullLastName() {
        Name name = new Name("John", null);

        assertEquals("John", name.getFirstName());
        assertNull(name.getLastName());
    }

    /**
     * Tests creating a Name with both names null.
     * Verifies that both null values are accepted.
     */
    @Test
    void testConstructorWithBothNamesNull() {
        Name name = new Name(null, null);

        assertNull(name.getFirstName());
        assertNull(name.getLastName());
    }

    /**
     * Tests creating a Name with empty strings.
     * Verifies that empty strings are handled correctly.
     */
    @Test
    void testConstructorWithEmptyStrings() {
        Name name = new Name("", "");

        assertEquals("", name.getFirstName());
        assertEquals("", name.getLastName());
    }

    // ============ Getter Tests ============

    /**
     * Tests getFirstName() method.
     * Verifies that the first name getter returns the correct value.
     */
    @Test
    void testGetFirstName() {
        assertEquals("John", testName.getFirstName());
    }

    /**
     * Tests getLastName() method.
     * Verifies that the last name getter returns the correct value.
     */
    @Test
    void testGetLastName() {
        assertEquals("Doe", testName.getLastName());
    }

    // ============ Setter Tests ============

    /**
     * Tests setFirstName() method with valid value.
     * Verifies that the first name setter correctly updates the field.
     */
    @Test
    void testSetFirstName() {
        testName.setFirstName("Michael");

        assertEquals("Michael", testName.getFirstName());
        assertEquals("Doe", testName.getLastName()); // Last name unchanged
    }

    /**
     * Tests setFirstName() method with null value.
     * Verifies that null can be set as first name.
     */
    @Test
    void testSetFirstNameToNull() {
        testName.setFirstName(null);

        assertNull(testName.getFirstName());
    }

    /**
     * Tests setFirstName() method with empty string.
     * Verifies that empty string can be set as first name.
     */
    @Test
    void testSetFirstNameToEmptyString() {
        testName.setFirstName("");

        assertEquals("", testName.getFirstName());
    }

    /**
     * Tests setLastName() method with valid value.
     * Verifies that the last name setter correctly updates the field.
     */
    @Test
    void testSetLastName() {
        testName.setLastName("Williams");

        assertEquals("John", testName.getFirstName()); // First name unchanged
        assertEquals("Williams", testName.getLastName());
    }

    /**
     * Tests setLastName() method with null value.
     * Verifies that null can be set as last name.
     */
    @Test
    void testSetLastNameToNull() {
        testName.setLastName(null);

        assertNull(testName.getLastName());
    }

    /**
     * Tests setLastName() method with empty string.
     * Verifies that empty string can be set as last name.
     */
    @Test
    void testSetLastNameToEmptyString() {
        testName.setLastName("");

        assertEquals("", testName.getLastName());
    }

    /**
     * Tests setting both names in sequence.
     * Verifies that both setters work correctly when called one after another.
     */
    @Test
    void testSetBothNames() {
        testName.setFirstName("Sarah");
        testName.setLastName("Connor");

        assertEquals("Sarah", testName.getFirstName());
        assertEquals("Connor", testName.getLastName());
    }

    // ============ toString() Tests ============

    /**
     * Tests toString() method with valid names.
     * Verifies correct string representation format.
     */
    @Test
    void testToString() {
        String result = testName.toString();

        assertEquals("Name{firstName='John', lastName='Doe'}", result);
    }

    /**
     * Tests toString() method with null first name.
     * Verifies that null is correctly represented in the string.
     */
    @Test
    void testToStringWithNullFirstName() {
        Name name = new Name(null, "Doe");
        String result = name.toString();

        assertEquals("Name{firstName='null', lastName='Doe'}", result);
    }

    /**
     * Tests toString() method with null last name.
     * Verifies that null is correctly represented in the string.
     */
    @Test
    void testToStringWithNullLastName() {
        Name name = new Name("John", null);
        String result = name.toString();

        assertEquals("Name{firstName='John', lastName='null'}", result);
    }

    /**
     * Tests toString() method with both names null.
     * Verifies that both nulls are correctly represented in the string.
     */
    @Test
    void testToStringWithBothNamesNull() {
        Name name = new Name(null, null);
        String result = name.toString();

        assertEquals("Name{firstName='null', lastName='null'}", result);
    }

    /**
     * Tests toString() method with empty strings.
     * Verifies correct string representation with empty names.
     */
    @Test
    void testToStringWithEmptyStrings() {
        Name name = new Name("", "");
        String result = name.toString();

        assertEquals("Name{firstName='', lastName=''}", result);
    }

    // ============ equals() Tests ============

    /**
     * Tests equals() method with identical objects.
     * Verifies that two Name objects with same values are equal.
     */
    @Test
    void testEqualsWithIdenticalNames() {
        assertTrue(testName.equals(sameName));
        assertTrue(sameName.equals(testName)); // Symmetric
    }

    /**
     * Tests equals() method with same reference.
     * Verifies reflexive property of equals (object equals itself).
     */
    @Test
    void testEqualsWithSameReference() {
        assertTrue(testName.equals(testName));
    }

    /**
     * Tests equals() method with different first name.
     * Verifies that Names with different first names are not equal.
     */
    @Test
    void testEqualsWithDifferentFirstName() {
        assertFalse(testName.equals(differentFirstName));
    }

    /**
     * Tests equals() method with different last name.
     * Verifies that Names with different last names are not equal.
     */
    @Test
    void testEqualsWithDifferentLastName() {
        assertFalse(testName.equals(differentLastName));
    }

    /**
     * Tests equals() method with both names different.
     * Verifies that completely different Names are not equal.
     */
    @Test
    void testEqualsWithBothNamesDifferent() {
        assertFalse(testName.equals(differentName));
    }

    /**
     * Tests equals() method with null object.
     * Verifies that comparison with null returns false.
     */
    @Test
    void testEqualsWithNull() {
        assertFalse(testName.equals(null));
    }

    /**
     * Tests equals() method with different class type.
     * Verifies that comparison with different object type returns false.
     */
    @Test
    void testEqualsWithDifferentClass() {
        String notAName = "John Doe";
        assertFalse(testName.equals(notAName));
    }

    /**
     * Tests equals() method with both names null.
     * Verifies that two Names with null values for both fields are equal.
     */
    @Test
    void testEqualsWithBothNamesNull() {
        Name name1 = new Name(null, null);
        Name name2 = new Name(null, null);

        assertTrue(name1.equals(name2));
    }

    /**
     * Tests equals() method with one name null in first object.
     * Verifies correct handling when first name is null in one object.
     */
    @Test
    void testEqualsWithNullFirstNameInFirstObject() {
        Name name1 = new Name(null, "Doe");
        Name name2 = new Name("John", "Doe");

        assertFalse(name1.equals(name2));
        assertFalse(name2.equals(name1));
    }

    /**
     * Tests equals() method with matching null first names.
     * Verifies that Names with same null first name and same last name are equal.
     */
    @Test
    void testEqualsWithMatchingNullFirstNames() {
        Name name1 = new Name(null, "Doe");
        Name name2 = new Name(null, "Doe");

        assertTrue(name1.equals(name2));
    }

    /**
     * Tests equals() method transitivity.
     * Verifies transitive property: if a=b and b=c, then a=c.
     */
    @Test
    void testEqualsTransitivity() {
        Name name1 = new Name("John", "Doe");
        Name name2 = new Name("John", "Doe");
        Name name3 = new Name("John", "Doe");

        assertTrue(name1.equals(name2));
        assertTrue(name2.equals(name3));
        assertTrue(name1.equals(name3)); // Transitivity
    }

    // ============ hashCode() Tests ============

    /**
     * Tests hashCode() method for equal objects.
     * Verifies that equal objects have the same hash code.
     */
    @Test
    void testHashCodeForEqualObjects() {
        assertEquals(testName.hashCode(), sameName.hashCode());
    }

    /**
     * Tests hashCode() method consistency.
     * Verifies that multiple calls return the same hash code.
     */
    @Test
    void testHashCodeConsistency() {
        int hash1 = testName.hashCode();
        int hash2 = testName.hashCode();

        assertEquals(hash1, hash2);
    }

    /**
     * Tests hashCode() method for different objects.
     * Note: Different objects may have same hash code (collision), but
     * we verify they can have different hash codes.
     */
    @Test
    void testHashCodeForDifferentObjects() {
        int hash1 = testName.hashCode();
        int hash2 = differentName.hashCode();

        // Different objects typically have different hash codes
        // but this is not guaranteed (collisions are allowed)
        assertNotEquals(hash1, hash2);
    }

    /**
     * Tests hashCode() with null values.
     * Verifies that hash code can be computed even with null fields.
     */
    @Test
    void testHashCodeWithNullValues() {
        Name name = new Name(null, null);

        // Should not throw exception
        assertDoesNotThrow(() -> name.hashCode());
    }

    /**
     * Tests hashCode() for Names with only first name null.
     * Verifies consistent hash code with partial null values.
     */
    @Test
    void testHashCodeWithNullFirstName() {
        Name name1 = new Name(null, "Doe");
        Name name2 = new Name(null, "Doe");

        assertEquals(name1.hashCode(), name2.hashCode());
    }

    /**
     * Tests hashCode() for Names with only last name null.
     * Verifies consistent hash code with partial null values.
     */
    @Test
    void testHashCodeWithNullLastName() {
        Name name1 = new Name("John", null);
        Name name2 = new Name("John", null);

        assertEquals(name1.hashCode(), name2.hashCode());
    }

    // ============ Integration Tests ============

    /**
     * Tests complete lifecycle: create, modify, verify.
     * Verifies that all operations work correctly in sequence.
     */
    @Test
    void testCompleteLifecycle() {
        // Create
        Name name = new Name("Original", "Name");
        assertEquals("Original", name.getFirstName());
        assertEquals("Name", name.getLastName());

        // Modify
        name.setFirstName("Modified");
        name.setLastName("Updated");
        assertEquals("Modified", name.getFirstName());
        assertEquals("Updated", name.getLastName());

        // Verify toString
        assertEquals("Name{firstName='Modified', lastName='Updated'}", name.toString());

        // Verify equals with new matching object
        Name matching = new Name("Modified", "Updated");
        assertTrue(name.equals(matching));
        assertEquals(name.hashCode(), matching.hashCode());
    }

    /**
     * Tests case sensitivity of equals.
     * Verifies that name comparison is case-sensitive.
     */
    @Test
    void testCaseSensitivity() {
        Name name1 = new Name("John", "Doe");
        Name name2 = new Name("john", "doe");
        Name name3 = new Name("JOHN", "DOE");

        assertFalse(name1.equals(name2));
        assertFalse(name1.equals(name3));
        assertFalse(name2.equals(name3));
    }
}