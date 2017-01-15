package com.camlait.global.erp.domain.util;

import static org.apache.commons.lang.reflect.FieldUtils.readField;
import static com.camlait.global.erp.domain.util.JSONUtils.copy;
import java.lang.reflect.InvocationTargetException;
import java.util.function.BiFunction;
import org.apache.commons.beanutils.BeanUtilsBean;

/**
 * Specific implementation of BeanUtilsBean class.
 * <p>
 * Merged the destination object with the source object according to a given rule.
 * <p>
 * <u><b>How to use the merging operation</b></u>
 * <p>
 * {@code}1: mergeBean = new MergeBeanUtilsBean() or mergeBeanUtil = new MergeBeanUtilsBean(builderCondition)
 * <p>
 * {@code}2: merginObject = mergeBean.<b>merge(source, destination)</b>.
 */
public class MergeBeanUtilsBean extends BeanUtilsBean {

    private final BiFunction<Object, Object, Boolean> mergingRuleBuilder;

    /**
     * Creates an instance of mergeBeanUtilsBean with a custom merging rule.
     * <p>
     * Note: The merging rule is based on both source and destination field value.
     * The function should take both source and destination value as parameter and produce a boolean that indicates
     * if the destination field need to be set with the source field value or not.
     * 
     * @param mergingConditionBuilder condition builder
     */
    public MergeBeanUtilsBean(BiFunction<Object, Object, Boolean> mergingConditionBuilder) {
        this.mergingRuleBuilder = mergingConditionBuilder;
    }

    /**
     * Creates an instance of mergeBeanUtilsBean with the default merging rule.
     * <p>
     * Here, only null fields are updated in the destination object with corresponding non null fields value from the source.
     */
    public MergeBeanUtilsBean() {
        this.mergingRuleBuilder = (sourceValue, destinationValue) -> {
            return sourceValue != null && destinationValue == null;
        };
    }

    /**
     * Set the field destination value with the field source value according to a specific rule.
     * 
     * @param destination Destination object that need to be updated.
     * @param fieldName Field name that need to be updated.
     * @param sourceValue Value of the source field.
     * @throws IllegalAccessException
     * @throws Exception
     */
    @Override
    public void copyProperty(Object destination, String fieldName, Object sourceValue) throws IllegalAccessException, InvocationTargetException {
        final Object destinationValue = readField(destination, fieldName, true);
        if (mergingRuleBuilder.apply(sourceValue, destinationValue)) {
            super.copyProperty(destination, fieldName, sourceValue);
        }
    }

    /**
     * Makes a deep copy of the destination object and merges with the source object
     * <p>
     * The source and the destination object should be the same type.
     * 
     * @param from Source object
     * @param to Destination object.
     * @return The merging object.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public <T> T merge(T from, T to) throws IllegalAccessException, InvocationTargetException {
        final T toMerge = copy(to);
        this.copyProperties(toMerge, from);
        return toMerge;
    }
}