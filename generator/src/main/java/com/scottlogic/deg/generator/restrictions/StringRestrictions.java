package com.scottlogic.deg.generator.restrictions;

import com.scottlogic.deg.common.profile.constraints.atomic.IsOfTypeConstraint;
import com.scottlogic.deg.generator.generation.StringGenerator;

public interface StringRestrictions extends Restrictions {
    MergeResult<StringRestrictions> intersect(StringRestrictions other);

    default boolean isInstanceOf(Object o) {
        return IsOfTypeConstraint.Types.STRING.getIsInstanceOf().apply(o);
    }

    boolean match(String x);

    @Override
    default boolean match(Object x) {
        return isInstanceOf(x) && match((String) x);
    }

    StringGenerator createGenerator();
}


