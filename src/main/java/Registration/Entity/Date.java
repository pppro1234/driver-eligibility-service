package Registration.Entity;

import java.util.Objects;

/**
 * Mutable date and time components used by the domain model.
 *
 * <p>Construction and setters do not validate calendar values. The builder defaults
 * hour and minute to zero. Instances are not thread-safe.</p>
 */
public class Date {

    /** The year component of the date. */
    private int year;

    /** The raw month component; normal dates use 1-12. */
    private int month;

    /** The raw day component; normal dates use 1-31. */
    private int day;

    /** The raw hour component; defaults to 0. */
    private int hour;

    /** The raw minute component; defaults to 0. */
    private int minute;

    /**
     * Private constructor that creates a Date from a Builder.
     *
     * @param builder the Builder containing date and time values
     */
    private Date(Builder builder) {
        this.year = builder.year;
        this.month = builder.month;
        this.day = builder.day;
        this.hour = builder.hour;
        this.minute = builder.minute;
    }

    /**
     * Subtracts each component of the supplied date from this date.
     *
     * <p>No calendar normalization is performed; individual differences may be negative.
     * Use calendar-aware date arithmetic when evaluating elapsed time.</p>
     *
     * @param date the date to subtract
     * @return a new object containing the component differences
     */
    public Date getDateDiff(Date date) {
        int yearDiff = this.year - date.getYear();
        int monthDiff = this.month - date.getMonth();
        int dayDiff = this.day - date.getDay();
        int hourDiff = this.hour - date.getHour();
        int minuteDiff = this.minute - date.getMinute();
        return new Date.Builder(yearDiff, monthDiff, dayDiff)
                .hour(hourDiff)
                .minute(minuteDiff)
                .build();
    }

    /**
     * Builds date components without calendar validation; hour and minute default to zero.
     */
    public static class Builder {

        /** The year component. */
        private int year;

        /** The month component. */
        private int month;

        /** The day component. */
        private int day;

        /** The hour component. Default is 0. */
        private int hour = 0;

        /** The minute component. Default is 0. */
        private int minute = 0;

        /**
         * Constructs a new Builder with required date components.
         *
         * @param year the year value
         * @param month the stored month component
         * @param day the stored day component
         */
        public Builder(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        /**
         * Sets the year component.
         *
         * @param year the year value to set
         * @return this Builder instance for method chaining
         */
        public Builder year(int year) {
            this.year = year;
            return this;
        }

        /**
         * Sets the month component.
         *
         * @param month the month value to store without range validation
         * @return this Builder instance for method chaining
         */
        public Builder month(int month) {
            this.month = month;
            return this;
        }

        /**
         * Sets the day component.
         *
         * @param day the day value to store without range validation
         * @return this Builder instance for method chaining
         */
        public Builder day(int day) {
            this.day = day;
            return this;
        }

        /**
         * Sets the hour component.
         *
         * @param hour the hour value to store without range validation
         * @return this Builder instance for method chaining
         */
        public Builder hour(int hour) {
            this.hour = hour;
            return this;
        }

        /**
         * Sets the minute component.
         *
         * @param minute the minute value to store without range validation
         * @return this Builder instance for method chaining
         */
        public Builder minute(int minute) {
            this.minute = minute;
            return this;
        }

        /**
         * Builds and returns a new Date instance.
         *
         * @return a new Date object with the configured values
         */
        public Date build() {
            return new Date(this);
        }
    }

    /**
     * Returns the year component of this date.
     *
     * @return the year value
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year component of this date.
     *
     * @param year the year value to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns the month component of this date.
     *
     * @return the stored month component
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the month component of this date.
     *
     * @param month the month value to store without range validation
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Returns the day component of this date.
     *
     * @return the stored day component
     */
    public int getDay() {
        return day;
    }

    /**
     * Sets the day component of this date.
     *
     * @param day the day value to store without range validation
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Returns the hour component of this date.
     *
     * @return the stored hour component
     */
    public int getHour() {
        return hour;
    }

    /**
     * Sets the hour component of this date.
     *
     * @param hour the hour value to store without range validation
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * Returns the minute component of this date.
     *
     * @return the stored minute component
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Sets the minute component of this date.
     *
     * @param minute the minute value to store without range validation
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * Compares this Date to another object for equality.
     *
     * <p>Two Date objects are considered equal if they have the same
     * year, month, day, hour, and minute values.</p>
     *
     * @param o the object to compare with
     * @return {@code true} if the objects are equal; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Date date = (Date) o;
        return year == date.year && month == date.month && day == date.day
                && hour == date.hour && minute == date.minute;
    }

    /**
     * Returns a hash code value for this Date.
     *
     * <p>The hash code is computed from all five components:
     * year, month, day, hour, and minute.</p>
     *
     * @return a hash code value for this Date
     */
    @Override
    public int hashCode() {
        return Objects.hash(year, month, day, hour, minute);
    }
}