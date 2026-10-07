package Registration.Validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Aggregated validation results for an application.
 */
public class ValidationReport {
    private final boolean valid;
    private final List<String> reasons;

    public ValidationReport(boolean valid, List<String> reasons) {
        this.valid = valid;
        this.reasons = reasons == null ? new ArrayList<>() : new ArrayList<>(reasons);
    }

    public boolean isValid() {
        return valid;
    }

    /**
     * Returns an unmodifiable list of rejection reasons.
     */
    public List<String> getReasons() {
        return Collections.unmodifiableList(reasons);
    }
}
