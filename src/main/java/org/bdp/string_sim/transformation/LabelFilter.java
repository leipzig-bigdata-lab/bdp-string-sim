package org.bdp.string_sim.transformation;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.tuple.Tuple4;
import org.bdp.string_sim.DataModel;

public class LabelFilter implements FilterFunction<Tuple4<Integer, String, String, String>> {
    /**
     * This Filter class transformation can be applied in a filter function.
     * It filters all tuples from the dataset, where the property name is not equal to 'label'.
     * As a result, only tuples with property name = 'label' will be returned.
     *
     * @param conceptAttrTuple the input tuple = one dataset from the concept_attributes.csv
     * @return boolean true if the second col equals "label", false if not.
     * @throws Exception
     */
    public boolean filter(Tuple4<Integer, String, String, String> conceptAttrTuple) throws Exception {
        return conceptAttrTuple.getField(DataModel.CONCEPT_ATTR_COL_PROP_NAME).equals("label");
    }
}

