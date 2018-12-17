package com.scottlogic.deg.generator.inputs.validation.validators;

import com.scottlogic.deg.generator.restrictions.*;
import com.scottlogic.deg.generator.inputs.validation.*;
import com.scottlogic.deg.generator.inputs.validation.messages.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NumericConstraintValidator implements ConstraintValidatorAlerts {


    public final ValidationType validationType = ValidationType.NUMERIC;
    private List<ValidationAlert> alerts;
    private NumericRestrictions currentRestrictions;

    public NumericConstraintValidator() {
        this.alerts = new ArrayList<>();
    }

    public void IsLessThan(String field, Number referenceValue, boolean inclusive) {

        BigDecimal referenceBigDecimal = new BigDecimal(referenceValue.toString());
        NumericRestrictions candidateRestrictions = new NumericRestrictions();
        candidateRestrictions.max = new NumericLimit<>(
            referenceBigDecimal,
            inclusive);

        NumericRestrictionsMerger merger = new NumericRestrictionsMerger();
        MergeResult<NumericRestrictions> result = merger.merge(currentRestrictions, candidateRestrictions);

        if(result.successful){
            currentRestrictions = result.restrictions;

            if (currentRestrictions.min != null
                && currentRestrictions.max != null &&
                currentRestrictions.min.getLimit().compareTo(currentRestrictions.max.getLimit()) >0 ) {

                logInformation(field, new NumericConstraintValidationMessages(
                    currentRestrictions.min == null ? null : currentRestrictions.min.getLimit(),
                    currentRestrictions.max == null ? null : currentRestrictions.max.getLimit(),
                    referenceBigDecimal));
            }

        } else {
            logError(field, new NumericConstraintValidationMessages(
                currentRestrictions.min == null ? null : currentRestrictions.min.getLimit(),
                currentRestrictions.max == null ? null : currentRestrictions.max.getLimit(),
                referenceBigDecimal));
        }

    }

    public void IsGreaterThan(String field, Number referenceValue, boolean inclusive) {

        BigDecimal referenceBigDecimal = new BigDecimal(referenceValue.toString());
        NumericRestrictions candidateRestrictions = new NumericRestrictions();
        candidateRestrictions.min = new NumericLimit<>(
            referenceBigDecimal,
            inclusive);

        NumericRestrictionsMerger merger = new NumericRestrictionsMerger();
        MergeResult<NumericRestrictions> result = merger.merge(currentRestrictions, candidateRestrictions);

        if(result.successful){
            currentRestrictions = result.restrictions;

            if (currentRestrictions.min != null
                && currentRestrictions.max != null &&
                currentRestrictions.min.getLimit().compareTo(currentRestrictions.max.getLimit()) >0 ) {

                logInformation(field, new NumericConstraintValidationMessages(
                    currentRestrictions.min == null ? null : currentRestrictions.min.getLimit(),
                    currentRestrictions.max == null ? null : currentRestrictions.max.getLimit(),
                    referenceBigDecimal));
            }
        } else {
            logError(field, new NumericConstraintValidationMessages(
                currentRestrictions.min == null ? null : currentRestrictions.min.getLimit(),
                currentRestrictions.max == null ? null : currentRestrictions.max.getLimit(),
                referenceBigDecimal));
        }
    }

    private void logError(String field, StandardValidationMessages message) {
        alerts.add(new ValidationAlert(
            Criticality.ERROR,
            message,
            validationType,
            field));
    }


    private void logInformation(String field, StandardValidationMessages message) {
        alerts.add(new ValidationAlert(
            Criticality.INFORMATION,
            message,
            validationType,
            field));
    }

    @Override
    public List<ValidationAlert> getAlerts() {
        return alerts;
    }
}
