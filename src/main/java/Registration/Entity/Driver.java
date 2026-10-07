package Registration.Entity;

/**
 * Mutable driver identity and license details used during application validation.
 *
 * <p>The license defaults to null. Name, birthday, and license references are retained
 * without copying or validation. Instances are not thread-safe.</p>
 */
public class Driver {
    private Name name;
    private Date birthday;
    private DriverLicense license;

    /**
     * Copies the builder's references without validating them.
     *
     * @param builder the driver properties
     */
    public Driver(Builder builder) {
        this.name = builder.name;
        this.birthday = builder.birthday;
        this.license = builder.license;
    }

    /**
     * Builds a driver from a name and birthday, with an optional license.
     * The builder accepts null values; input validation is performed separately.
     */
    public static class Builder {
        private Name name;
        private Date birthday;
        private DriverLicense license = null;

        /**
         * Constructs a Builder with required driver fields.
         *
         * @param name     the driver's name (required)
         * @param birthday the driver's birthday (required)
         */
        public Builder(Name name, Date birthday) {
            this.name = name;
            this.birthday = birthday;
        }

        /**
         * Sets the driver's name.
         *
         * @param name the driver's name
         * @return this Builder instance for method chaining
         */
        public Builder name(Name name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the driver's birthday.
         *
         * @param birthday the driver's birthday
         * @return this Builder instance for method chaining
         */
        public Builder birthday(Date birthday) {
            this.birthday = birthday;
            return this;
        }

        /**
         * Sets the driver's license (optional).
         *
         * @param driverLicense the driver's license information
         * @return this Builder instance for method chaining
         */
        public Builder driverLicense(DriverLicense driverLicense) {
            this.license = driverLicense;
            return this;
        }

        /**
         * Builds and returns a new Driver instance with the configured properties.
         *
         * @return a new Driver instance
         */
        public Driver build() {
            return new Driver(this);
        }
    }

    /**
     * Gets the driver's name.
     *
     * @return the driver's name
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the driver's name.
     *
     * @param name the new name for the driver
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Gets the driver's birthday.
     *
     * @return the driver's birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Sets the driver's birthday.
     *
     * @param birthday the new birthday for the driver
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets the driver's license information.
     *
     * @return the driver's license, or null if not set
     */
    public DriverLicense getLicense() {
        return license;
    }

    /**
     * Sets the driver's license information.
     *
     * @param license the new license for the driver
     */
    public void setLicense(DriverLicense license) {
        this.license = license;
    }
}