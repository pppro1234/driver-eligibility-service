package Registration.Entity;

import java.util.List;

/**
 * Mutable policy holder, covered drivers, and policy expiration date.
 *
 * <p>Unset fields remain null. The model retains supplied objects and lists without
 * validation or defensive copies. Instances are not thread-safe.</p>
 */
public class VehicleInsurance {

    /** The name of the person on the insurance policy. */
    private Name nameOnPolicy;

    /** List of drivers covered under this insurance policy. */
    private List<Driver> drivers;

    /** The expiration date of the insurance policy. */
    private Date expirationDate;

    /**
     * Copies insurance references from the builder without validating them.
     *
     * @param builder the insurance properties
     */
    public VehicleInsurance(Builder builder) {
        this.nameOnPolicy = builder.nameOnPolicy;
        this.drivers = builder.drivers;
        this.expirationDate = builder.expirationDate;
    }

    /**
     * Builds policy details; unset fields remain null and validation is performed separately.
     */
    public static class Builder {

        /** The name of the person on the insurance policy. */
        private Name nameOnPolicy;

        /** List of drivers covered under this insurance policy. */
        private List<Driver> drivers;

        /** The expiration date of the insurance policy. */
        private Date expirationDate;

        /**
         * Sets the name on the insurance policy.
         *
         * @param nameOnPolicy the name of the policy holder
         * @return this Builder instance for method chaining
         */
        public Builder nameOnPolicy(Name nameOnPolicy) {
            this.nameOnPolicy = nameOnPolicy;
            return this;
        }

        /**
         * Sets the list of drivers covered under the insurance policy.
         *
         * @param drivers the list of covered drivers
         * @return this Builder instance for method chaining
         */
        public Builder drivers(List<Driver> drivers) {
            this.drivers = drivers;
            return this;
        }

        /**
         * Sets the expiration date of the insurance policy.
         *
         * @param expirationDate the policy expiration date
         * @return this Builder instance for method chaining
         */
        public Builder expirationDate(Date expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        /**
         * Builds and returns a new VehicleInsurance instance with the configured properties.
         *
         * @return a new VehicleInsurance object
         */
        public VehicleInsurance build() {
            return new VehicleInsurance(this);
        }
    }

    /**
     * Returns the name on the insurance policy.
     *
     * @return the policy holder's name, or null if not set
     */
    public Name getNameOnPolicy() {
        return nameOnPolicy;
    }

    /**
     * Sets the name on the insurance policy.
     *
     * @param nameOnPolicy the policy holder's name to set
     */
    public void setNameOnPolicy(Name nameOnPolicy) {
        this.nameOnPolicy = nameOnPolicy;
    }

    /**
     * Returns the list of drivers covered under the insurance policy.
     *
     * @return the list of covered drivers, or null if not set
     */
    public List<Driver> getDrivers() {
        return drivers;
    }

    /**
     * Sets the list of drivers covered under the insurance policy.
     *
     * @param drivers the list of covered drivers to set
     */
    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    /**
     * Returns the expiration date of the insurance policy.
     *
     * @return the policy expiration date, or null if not set
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expiration date of the insurance policy.
     *
     * @param expirationDate the policy expiration date to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}