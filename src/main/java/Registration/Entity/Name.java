package Registration.Entity;

import java.util.Objects;

/**
 * Mutable first and last name components.
 *
 * <p>Equality compares both components case-sensitively and handles null values.
 * Instances are not thread-safe; changing a name also changes its hash code.</p>
 */
public class Name {

    /** The first name of the person. */
    private String firstName;

    /** The last name of the person. */
    private String lastName;

    /**
     * Constructs a new Name with the specified first and last names.
     *
     * @param firstName the first name of the person
     * @param lastName the last name of the person
     */
    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Returns the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     *
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns a string representation of this Name.
     * <p>
     * The string representation consists of the class name followed by
     * the first and last names enclosed in braces.
     * </p>
     *
     * @return a string representation of this Name in the format:
     *         {@code Name{firstName='[firstName]', lastName='[lastName]'}}
     */
    @Override
    public String toString() {
        return "Name{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    /**
     * Compares this Name to another object for equality.
     * <p>
     * Two Name objects are considered equal if they have the same first name
     * and last name. The comparison is case-sensitive and handles null values correctly.
     * </p>
     *
     * @param o the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(firstName, name.firstName) && Objects.equals(lastName, name.lastName);
    }

    /**
     * Returns a hash code value for this Name.
     * <p>
     * The hash code is computed based on both the first name and last name,
     * ensuring that equal Name objects have the same hash code.
     * </p>
     *
     * @return a hash code value for this Name
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}