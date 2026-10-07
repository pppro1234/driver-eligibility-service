package Registration.Entity;

/**
 * Mutable license details used by application validation.
 *
 * <p>Fields default to null and are not validated by this model. Referenced names
 * and dates are not copied. Instances are not thread-safe.</p>
 *
 * <p>The spellings {@code expiratonDate} and {@code experienceDate} are retained
 * for compatibility; both refer to the license expiration date.</p>
 */
public class DriverLicense {

    /** The unique license number assigned by the issuing authority. */
    private String licenseNumber;

    /** The name of the license holder. */
    private Name name;

    /** The address listed on the license. */
    private String address;

    /** The birth date of the license holder. */
    private Date birthDate;

    /** The country and state/province that issued the license (e.g., "USA-CA", "CAN-ON"). */
    private String countryAndState;

    /** The date the license was issued. */
    private Date issuanceDate;

    /** The expiration date of the license. Note: Typo in field name (expiraton vs expiration). */
    private Date expiratonDate;

    /**
     * Copies license fields from the builder without validating or copying referenced objects.
     *
     * @param builder the license properties
     */
    public DriverLicense(Builder builder) {
        this.licenseNumber = builder.licenseNumber;
        this.name = builder.name;
        this.address = builder.address;
        this.birthDate = builder.birthDate;
        this.countryAndState = builder.countryAndState;
        this.issuanceDate = builder.issuanceDate;
        this.expiratonDate = builder.experienceDate;
    }

    /**
     * Builds license details; unset fields remain null and validation is performed separately.
     */
    public static class Builder {

        /** The unique license number. */
        private String licenseNumber;

        /** The name of the license holder. */
        private Name name;

        /** The address on the license. */
        private String address;

        /** The birth date of the license holder. */
        private Date birthDate;

        /** The country and state/province that issued the license. */
        private String countryAndState;

        /** The date the license was issued. */
        private Date issuanceDate;

        /** The expiration date. Note: Parameter name typo (experience vs expiration). */
        private Date experienceDate;

        /**
         * Sets the license number.
         *
         * @param licenseNumber the unique license number
         * @return this Builder instance for method chaining
         */
        public Builder licenseNumber(String licenseNumber) {
            this.licenseNumber = licenseNumber;
            return this;
        }

        /**
         * Sets the name on the license.
         *
         * @param name the license holder's name
         * @return this Builder instance for method chaining
         */
        public Builder name(Name name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the address on the license.
         *
         * @param address the license holder's address
         * @return this Builder instance for method chaining
         */
        public Builder address(String address) {
            this.address = address;
            return this;
        }

        /**
         * Sets the birth date on the license.
         *
         * @param birthDate the license holder's birth date
         * @return this Builder instance for method chaining
         */
        public Builder birthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        /**
         * Sets the country and state/province that issued the license.
         *
         * @param countryAndState the issuing jurisdiction (e.g., "USA-CA", "CAN-ON")
         * @return this Builder instance for method chaining
         */
        public Builder countryAndState(String countryAndState) {
            this.countryAndState = countryAndState;
            return this;
        }

        /**
         * Sets the license issuance date.
         *
         * @param issuanceDate the date the license was issued
         * @return this Builder instance for method chaining
         */
        public Builder issuanceDate(Date issuanceDate) {
            this.issuanceDate = issuanceDate;
            return this;
        }

        /**
         * Sets the license expiration date.
         * <p>
         * <b>Note:</b> The parameter name "experienceDate" is a typo and should be
         * "expirationDate". This is maintained for backward compatibility.
         * </p>
         *
         * @param experienceDate the date the license expires (typo: should be expirationDate)
         * @return this Builder instance for method chaining
         */
        public Builder experienceDate(Date experienceDate) {
            this.experienceDate = experienceDate;
            return this;
        }

        /**
         * Builds and returns a new DriverLicense instance with the configured properties.
         *
         * @return a new DriverLicense object
         */
        public DriverLicense build() {
            return new DriverLicense(this);
        }
    }

    /**
     * Returns the license number.
     *
     * @return the unique license number, or null if not set
     */
    public String getLicenseNumber() {
        return licenseNumber;
    }

    /**
     * Sets the license number.
     *
     * @param licenseNumber the unique license number to set
     */
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    /**
     * Returns the name on the license.
     *
     * @return the license holder's name, or null if not set
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the name on the license.
     *
     * @param name the license holder's name to set
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Returns the address on the license.
     *
     * @return the license holder's address, or null if not set
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address on the license.
     *
     * @param address the license holder's address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the birth date on the license.
     *
     * @return the license holder's birth date, or null if not set
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birth date on the license.
     *
     * @param birthDate the license holder's birth date to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Returns the country and state/province that issued the license.
     *
     * @return the issuing jurisdiction, or null if not set
     */
    public String getCountryAndState() {
        return countryAndState;
    }

    /**
     * Sets the country and state/province that issued the license.
     *
     * @param countryAndState the issuing jurisdiction to set
     */
    public void setCountryAndState(String countryAndState) {
        this.countryAndState = countryAndState;
    }

    /**
     * Returns the license issuance date.
     *
     * @return the date the license was issued, or null if not set
     */
    public Date getIssuanceDate() {
        return issuanceDate;
    }

    /**
     * Sets the license issuance date.
     *
     * @param issuanceDate the date the license was issued
     */
    public void setIssuanceDate(Date issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    /**
     * Returns the license expiration date.
     * <p>
     * <b>Note:</b> The method name contains a typo ("Expiraton" vs "Expiration").
     * This is maintained for backward compatibility.
     * </p>
     *
     * @return the date the license expires, or null if not set
     */
    public Date getExpiratonDate() {
        return expiratonDate;
    }

    /**
     * Sets the license expiration date.
     * <p>
     * <b>Note:</b> The method name contains a typo ("Expiraton" vs "Expiration").
     * This is maintained for backward compatibility.
     * </p>
     *
     * @param expiratonDate the date the license expires
     */
    public void setExpiratonDate(Date expiratonDate) {
        this.expiratonDate = expiratonDate;
    }
}