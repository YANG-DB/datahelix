/*
 * Copyright 2019 Scott Logic Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.scottlogic.deg.generator.profile.constraints.atomic;

import com.scottlogic.deg.common.profile.Field;
import com.scottlogic.deg.common.profile.HelixDateTime;
import com.scottlogic.deg.generator.fieldspecs.FieldSpec;
import com.scottlogic.deg.generator.fieldspecs.FieldSpecFactory;
import com.scottlogic.deg.generator.restrictions.linear.Limit;
import com.scottlogic.deg.generator.restrictions.linear.LinearRestrictions;

import java.time.OffsetDateTime;
import java.util.Objects;

import static com.scottlogic.deg.generator.restrictions.linear.LinearRestrictionsFactory.createDateTimeRestrictions;
import static com.scottlogic.deg.generator.utils.Defaults.DATETIME_MIN_LIMIT;

public class IsBeforeConstantDateTimeConstraint implements AtomicConstraint {
    public final Field field;
    public final HelixDateTime referenceValue;

    public IsBeforeConstantDateTimeConstraint(Field field, HelixDateTime referenceValue) {
        this.field = field;
        this.referenceValue = referenceValue;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public AtomicConstraint negate() {
        return new IsAfterOrEqualToConstantDateTimeConstraint(field, referenceValue);
    }

    @Override
    public FieldSpec toFieldSpec() {
        final LinearRestrictions<OffsetDateTime> dateTimeRestrictions = createDateTimeRestrictions(DATETIME_MIN_LIMIT, new Limit<>(referenceValue.getValue(), false));
        return FieldSpecFactory.fromRestriction(dateTimeRestrictions);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o instanceof ViolatedAtomicConstraint) {
            return o.equals(this);
        }
        if (o == null || getClass() != o.getClass()) return false;
        IsBeforeConstantDateTimeConstraint constraint = (IsBeforeConstantDateTimeConstraint) o;
        return Objects.equals(field, constraint.field) && Objects.equals(referenceValue, constraint.referenceValue);
    }

    @Override
    public int hashCode(){
        return Objects.hash(field, referenceValue);
    }

    @Override
    public String toString() {
        return String.format("`%s` < %s", field.getName(), referenceValue);
    }
}
