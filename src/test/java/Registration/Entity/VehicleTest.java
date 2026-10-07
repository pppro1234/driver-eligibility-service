package Registration.Entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests vehicle construction, field access, and formatting.
 */
class VehicleTest {

    private String testMake;
    private String testModel;
    private int testYear;
    private String testOwner;
    private String testPlateNumber;

    private String altMake;
    private String altModel;
    private int altYear;
    private String altOwner;
    private String altPlateNumber;

    /**
     * Sets up test fixtures before each test method.
     * Initializes test data for vehicle properties.
     */
    @BeforeEach
    void setUp() {
        testMake = "Toyota";
        testModel = "Camry";
        testYear = 2020;
        testOwner = "John Doe";
        testPlateNumber = "ABC123";

        altMake = "Honda";
        altModel = "Accord";
        altYear = 2021;
        altOwner = "Jane Smith";
        altPlateNumber = "XYZ789";
    }

    // ============ Traditional Constructor Tests ============

    /**
     * Tests creating a Vehicle with all parameters using traditional constructor.
     * Verifies that all fields are correctly initialized.
     */
    @Test
    void testTraditionalConstructorWithAllFields() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        assertNotNull(vehicle);
        assertEquals(testMake, vehicle.getMake());
        assertEquals(testModel, vehicle.getModel());
        assertEquals(testYear, vehicle.getYear());
        assertEquals(testOwner, vehicle.getOwner());
        assertEquals(testPlateNumber, vehicle.getPlateNumber());
    }

    /**
     * Tests creating a Vehicle with null plate number using traditional constructor.
     * Verifies that null values are accepted.
     */
    @Test
    void testTraditionalConstructorWithNullPlateNumber() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, null);

        assertEquals(testMake, vehicle.getMake());
        assertEquals(testModel, vehicle.getModel());
        assertEquals(testYear, vehicle.getYear());
        assertEquals(testOwner, vehicle.getOwner());
        assertNull(vehicle.getPlateNumber());
    }

    /**
     * Tests creating a Vehicle with null strings using traditional constructor.
     * Verifies that multiple null values are accepted.
     */
    @Test
    void testTraditionalConstructorWithNullStrings() {
        Vehicle vehicle = new Vehicle(null, null, testYear, null, null);

        assertNull(vehicle.getMake());
        assertNull(vehicle.getModel());
        assertEquals(testYear, vehicle.getYear());
        assertNull(vehicle.getOwner());
        assertNull(vehicle.getPlateNumber());
    }

    /**
     * Tests creating a Vehicle with year zero using traditional constructor.
     * Verifies that zero and negative years are accepted.
     */
    @Test
    void testTraditionalConstructorWithZeroYear() {
        Vehicle vehicle = new Vehicle(testMake, testModel, 0, testOwner, testPlateNumber);

        assertEquals(0, vehicle.getYear());
    }

    // ============ Builder Constructor Tests ============

    /**
     * Tests creating a Vehicle with all fields using Builder pattern.
     * Verifies that builder correctly sets all properties.
     */
    @Test
    void testBuilderWithAllFields() {
        Vehicle vehicle = new Vehicle.Builder()
                .make(testMake)
                .model(testModel)
                .year(testYear)
                .owner(testOwner)
                .plateNumber(testPlateNumber)
                .build();

        assertNotNull(vehicle);
        assertEquals(testMake, vehicle.getMake());
        assertEquals(testModel, vehicle.getModel());
        assertEquals(testYear, vehicle.getYear());
        assertEquals(testOwner, vehicle.getOwner());
        assertEquals(testPlateNumber, vehicle.getPlateNumber());
    }

    /**
     * Tests creating a Vehicle without setting any fields using Builder.
     * Verifies default values (null strings, 0 for int).
     */
    @Test
    void testBuilderWithNoFieldsSet() {
        Vehicle vehicle = new Vehicle.Builder().build();

        assertNotNull(vehicle);
        assertNull(vehicle.getMake());
        assertNull(vehicle.getModel());
        assertEquals(0, vehicle.getYear());
        assertNull(vehicle.getOwner());
        assertNull(vehicle.getPlateNumber());
    }

    /**
     * Tests creating a Vehicle with only some fields set using Builder.
     * Verifies partial initialization works correctly.
     */
    @Test
    void testBuilderWithPartialFields() {
        Vehicle vehicle = new Vehicle.Builder()
                .make(testMake)
                .year(testYear)
                .build();

        assertEquals(testMake, vehicle.getMake());
        assertNull(vehicle.getModel());
        assertEquals(testYear, vehicle.getYear());
        assertNull(vehicle.getOwner());
        assertNull(vehicle.getPlateNumber());
    }

    /**
     * Tests builder make() method and method chaining.
     * Verifies that the make method returns the builder.
     */
    @Test
    void testBuilderMakeMethodChaining() {
        Vehicle vehicle = new Vehicle.Builder()
                .make(testMake)
                .make(altMake) // Override with new value
                .build();

        assertEquals(altMake, vehicle.getMake());
    }

    /**
     * Tests builder model() method and method chaining.
     * Verifies that the model method returns the builder.
     */
    @Test
    void testBuilderModelMethodChaining() {
        Vehicle vehicle = new Vehicle.Builder()
                .model(testModel)
                .build();

        assertEquals(testModel, vehicle.getModel());
    }

    /**
     * Tests builder year() method and method chaining.
     * Verifies that the year method returns the builder.
     */
    @Test
    void testBuilderYearMethodChaining() {
        Vehicle vehicle = new Vehicle.Builder()
                .year(testYear)
                .build();

        assertEquals(testYear, vehicle.getYear());
    }

    /**
     * Tests builder owner() method and method chaining.
     * Verifies that the owner method returns the builder.
     */
    @Test
    void testBuilderOwnerMethodChaining() {
        Vehicle vehicle = new Vehicle.Builder()
                .owner(testOwner)
                .build();

        assertEquals(testOwner, vehicle.getOwner());
    }

    /**
     * Tests builder plateNumber() method and method chaining.
     * Verifies that the plateNumber method returns the builder.
     */
    @Test
    void testBuilderPlateNumberMethodChaining() {
        Vehicle vehicle = new Vehicle.Builder()
                .plateNumber(testPlateNumber)
                .build();

        assertEquals(testPlateNumber, vehicle.getPlateNumber());
    }

    /**
     * Tests complete builder method chaining with all methods.
     * Verifies that all builder methods can be chained together.
     */
    @Test
    void testBuilderCompleteMethodChaining() {
        Vehicle vehicle = new Vehicle.Builder()
                .make(testMake)
                .model(testModel)
                .year(testYear)
                .owner(testOwner)
                .plateNumber(testPlateNumber)
                .build();

        assertEquals(testMake, vehicle.getMake());
        assertEquals(testModel, vehicle.getModel());
        assertEquals(testYear, vehicle.getYear());
        assertEquals(testOwner, vehicle.getOwner());
        assertEquals(testPlateNumber, vehicle.getPlateNumber());
    }

    /**
     * Tests builder with null values explicitly set.
     * Verifies that setting null values works correctly.
     */
    @Test
    void testBuilderWithNullValues() {
        Vehicle vehicle = new Vehicle.Builder()
                .make(null)
                .model(null)
                .owner(null)
                .plateNumber(null)
                .build();

        assertNull(vehicle.getMake());
        assertNull(vehicle.getModel());
        assertNull(vehicle.getOwner());
        assertNull(vehicle.getPlateNumber());
    }

    /**
     * Tests builder with different ordering of method calls.
     * Verifies that method order doesn't affect the result.
     */
    @Test
    void testBuilderWithDifferentMethodOrder() {
        Vehicle vehicle = new Vehicle.Builder()
                .plateNumber(testPlateNumber)
                .owner(testOwner)
                .year(testYear)
                .model(testModel)
                .make(testMake)
                .build();

        assertEquals(testMake, vehicle.getMake());
        assertEquals(testModel, vehicle.getModel());
        assertEquals(testYear, vehicle.getYear());
        assertEquals(testOwner, vehicle.getOwner());
        assertEquals(testPlateNumber, vehicle.getPlateNumber());
    }

    // ============ Getter Tests ============

    /**
     * Tests getMake() method.
     * Verifies that the make getter returns the correct value.
     */
    @Test
    void testGetMake() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);
        assertEquals(testMake, vehicle.getMake());
    }

    /**
     * Tests getModel() method.
     * Verifies that the model getter returns the correct value.
     */
    @Test
    void testGetModel() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);
        assertEquals(testModel, vehicle.getModel());
    }

    /**
     * Tests getYear() method.
     * Verifies that the year getter returns the correct value.
     */
    @Test
    void testGetYear() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);
        assertEquals(testYear, vehicle.getYear());
    }

    /**
     * Tests getOwner() method.
     * Verifies that the owner getter returns the correct value.
     */
    @Test
    void testGetOwner() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);
        assertEquals(testOwner, vehicle.getOwner());
    }

    /**
     * Tests getPlateNumber() method.
     * Verifies that the plate number getter returns the correct value.
     */
    @Test
    void testGetPlateNumber() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);
        assertEquals(testPlateNumber, vehicle.getPlateNumber());
    }

    /**
     * Tests getPlateNumber() when plate number is null.
     * Verifies that null is correctly returned.
     */
    @Test
    void testGetPlateNumberWhenNull() {
        Vehicle vehicle = new Vehicle.Builder().build();
        assertNull(vehicle.getPlateNumber());
    }

    // ============ Setter Tests ============

    /**
     * Tests setMake() method.
     * Verifies that the make setter correctly updates the field.
     */
    @Test
    void testSetMake() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        vehicle.setMake(altMake);

        assertEquals(altMake, vehicle.getMake());
    }

    /**
     * Tests setMake() with null value.
     * Verifies that null can be set as make.
     */
    @Test
    void testSetMakeToNull() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        vehicle.setMake(null);

        assertNull(vehicle.getMake());
    }

    /**
     * Tests setModel() method.
     * Verifies that the model setter correctly updates the field.
     */
    @Test
    void testSetModel() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        vehicle.setModel(altModel);

        assertEquals(altModel, vehicle.getModel());
    }

    /**
     * Tests setModel() with null value.
     * Verifies that null can be set as model.
     */
    @Test
    void testSetModelToNull() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        vehicle.setModel(null);

        assertNull(vehicle.getModel());
    }

    /**
     * Tests setYear() method.
     * Verifies that the year setter correctly updates the field.
     */
    @Test
    void testSetYear() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        vehicle.setYear(altYear);

        assertEquals(altYear, vehicle.getYear());
    }

    /**
     * Tests setYear() with zero value.
     * Verifies that zero can be set as year.
     */
    @Test
    void testSetYearToZero() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        vehicle.setYear(0);

        assertEquals(0, vehicle.getYear());
    }

    /**
     * Tests setYear() with negative value.
     * Verifies that negative values can be set as year.
     */
    @Test
    void testSetYearToNegative() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        vehicle.setYear(-1);

        assertEquals(-1, vehicle.getYear());
    }

    /**
     * Tests setOwner() method.
     * Verifies that the owner setter correctly updates the field.
     */
    @Test
    void testSetOwner() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        vehicle.setOwner(altOwner);

        assertEquals(altOwner, vehicle.getOwner());
    }

    /**
     * Tests setOwner() with null value.
     * Verifies that null can be set as owner.
     */
    @Test
    void testSetOwnerToNull() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        vehicle.setOwner(null);

        assertNull(vehicle.getOwner());
    }

    /**
     * Tests setPlateNumber() method.
     * Verifies that the plate number setter correctly updates the field.
     */
    @Test
    void testSetPlateNumber() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        vehicle.setPlateNumber(altPlateNumber);

        assertEquals(altPlateNumber, vehicle.getPlateNumber());
    }

    /**
     * Tests setPlateNumber() with null value.
     * Verifies that null can be set as plate number.
     */
    @Test
    void testSetPlateNumberToNull() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        vehicle.setPlateNumber(null);

        assertNull(vehicle.getPlateNumber());
    }

    /**
     * Tests setPlateNumber() on a vehicle created without plate number.
     * Verifies that plate number can be added after construction.
     */
    @Test
    void testSetPlateNumberOnVehicleWithoutPlate() {
        Vehicle vehicle = new Vehicle.Builder().build();

        vehicle.setPlateNumber(testPlateNumber);

        assertEquals(testPlateNumber, vehicle.getPlateNumber());
    }

    // ============ toString() Tests ============

    /**
     * Tests toString() method with all fields populated.
     * Verifies correct string representation format.
     */
    @Test
    void testToString() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);
        String result = vehicle.toString();

        String expected = "Vehicle{make='Toyota', model='Camry', year=2020, " +
                "color='null', owner='John Doe', plateNumber='ABC123'}";
        assertEquals(expected, result);
    }

    /**
     * Tests toString() method with null values.
     * Verifies that null values are correctly represented in the string.
     */
    @Test
    void testToStringWithNullValues() {
        Vehicle vehicle = new Vehicle(null, null, 0, null, null);
        String result = vehicle.toString();

        String expected = "Vehicle{make='null', model='null', year=0, " +
                "color='null', owner='null', plateNumber='null'}";
        assertEquals(expected, result);
    }

    /**
     * Tests toString() method with partial null values.
     * Verifies mixed null and non-null values in string representation.
     */
    @Test
    void testToStringWithPartialNullValues() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, null);
        String result = vehicle.toString();

        assertTrue(result.contains("make='Toyota'"));
        assertTrue(result.contains("model='Camry'"));
        assertTrue(result.contains("year=2020"));
        assertTrue(result.contains("owner='John Doe'"));
        assertTrue(result.contains("plateNumber='null'"));
    }

    /**
     * Tests toString() after modifying values with setters.
     * Verifies that toString reflects current state after modifications.
     */
    @Test
    void testToStringAfterSetters() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        vehicle.setMake(altMake);
        vehicle.setModel(altModel);

        String result = vehicle.toString();
        assertTrue(result.contains("make='Honda'"));
        assertTrue(result.contains("model='Accord'"));
    }

    // ============ Integration Tests ============

    /**
     * Tests multiple setter calls in sequence.
     * Verifies that all setters work correctly when called multiple times.
     */
    @Test
    void testMultipleSetterCalls() {
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        vehicle.setMake(altMake);
        vehicle.setModel(altModel);
        vehicle.setYear(altYear);
        vehicle.setOwner(altOwner);
        vehicle.setPlateNumber(altPlateNumber);

        assertEquals(altMake, vehicle.getMake());
        assertEquals(altModel, vehicle.getModel());
        assertEquals(altYear, vehicle.getYear());
        assertEquals(altOwner, vehicle.getOwner());
        assertEquals(altPlateNumber, vehicle.getPlateNumber());
    }

    /**
     * Tests complete vehicle lifecycle using traditional constructor.
     * Verifies create → modify → verify workflow.
     */
    @Test
    void testCompleteLifecycleTraditionalConstructor() {
        // Create
        Vehicle vehicle = new Vehicle(testMake, testModel, testYear, testOwner, testPlateNumber);

        // Verify initial state
        assertEquals(testMake, vehicle.getMake());
        assertEquals(testModel, vehicle.getModel());
        assertEquals(testYear, vehicle.getYear());
        assertEquals(testOwner, vehicle.getOwner());
        assertEquals(testPlateNumber, vehicle.getPlateNumber());

        // Modify
        vehicle.setMake(altMake);
        vehicle.setModel(altModel);
        vehicle.setYear(altYear);
        vehicle.setOwner(altOwner);
        vehicle.setPlateNumber(altPlateNumber);

        // Verify modified state
        assertEquals(altMake, vehicle.getMake());
        assertEquals(altModel, vehicle.getModel());
        assertEquals(altYear, vehicle.getYear());
        assertEquals(altOwner, vehicle.getOwner());
        assertEquals(altPlateNumber, vehicle.getPlateNumber());
    }

    /**
     * Tests complete vehicle lifecycle using Builder pattern.
     * Verifies builder creation → modify → verify workflow.
     */
    @Test
    void testCompleteLifecycleBuilderConstructor() {
        // Create with builder
        Vehicle vehicle = new Vehicle.Builder()
                .make(testMake)
                .model(testModel)
                .year(testYear)
                .owner(testOwner)
                .plateNumber(testPlateNumber)
                .build();

        // Verify initial state
        assertEquals(testMake, vehicle.getMake());

        // Modify all fields
        vehicle.setMake(altMake);
        vehicle.setModel(altModel);
        vehicle.setYear(altYear);
        vehicle.setOwner(altOwner);
        vehicle.setPlateNumber(altPlateNumber);

        // Verify modified state
        assertEquals(altMake, vehicle.getMake());
        assertEquals(altModel, vehicle.getModel());
        assertEquals(altYear, vehicle.getYear());
        assertEquals(altOwner, vehicle.getOwner());
        assertEquals(altPlateNumber, vehicle.getPlateNumber());
    }

    /**
     * Tests that builder creates independent instances.
     * Verifies that multiple vehicles created are not affected by each other.
     */
    @Test
    void testBuilderCreatesIndependentInstances() {
        Vehicle.Builder builder = new Vehicle.Builder()
                .make(testMake)
                .model(testModel)
                .year(testYear);

        Vehicle vehicle1 = builder.build();
        Vehicle vehicle2 = builder.build();

        // Modify vehicle1
        vehicle1.setMake(altMake);

        // Verify vehicle2 is not affected
        assertEquals(testMake, vehicle2.getMake());
        assertNotEquals(vehicle1.getMake(), vehicle2.getMake());
    }

    /**
     * Tests equivalence of traditional and builder constructors.
     * Verifies that both construction methods create equivalent objects.
     */
    @Test
    void testTraditionalAndBuilderConstructorEquivalence() {
        Vehicle traditionalVehicle = new Vehicle(testMake, testModel, testYear,
                testOwner, testPlateNumber);

        Vehicle builderVehicle = new Vehicle.Builder()
                .make(testMake)
                .model(testModel)
                .year(testYear)
                .owner(testOwner)
                .plateNumber(testPlateNumber)
                .build();

        assertEquals(traditionalVehicle.getMake(), builderVehicle.getMake());
        assertEquals(traditionalVehicle.getModel(), builderVehicle.getModel());
        assertEquals(traditionalVehicle.getYear(), builderVehicle.getYear());
        assertEquals(traditionalVehicle.getOwner(), builderVehicle.getOwner());
        assertEquals(traditionalVehicle.getPlateNumber(), builderVehicle.getPlateNumber());
        assertEquals(traditionalVehicle.toString(), builderVehicle.toString());
    }
}