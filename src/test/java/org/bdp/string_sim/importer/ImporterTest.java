package org.bdp.string_sim.importer;

import junit.framework.TestCase;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

public class ImporterTest extends TestCase {
    private Importer importer;
    private ExecutionEnvironment environment;

    public void setUp() throws Exception {
        super.setUp();
        importer = new Importer();
        environment = ExecutionEnvironment.getExecutionEnvironment();
    }

    /**
     * Test the import of the concept.csv
     * @throws Exception
     */
    public void testGetConceptDataSetFromCsv() throws Exception {
        DataSet dataSet =  importer.getConceptDataSetFromCsv(Importer.CSV_TYPE_PERFECT,environment);
        assertEquals((long)30,dataSet.count());
    }

    /**
     * Test the import of the concept_attributes.csv
     * @throws Exception
     */
    public void testGetConceptAttrDataSetFromCsv() throws Exception {
        DataSet dataSet = importer.getConceptAttrDataSetFromCsv(Importer.CSV_TYPE_PERFECT,environment);
        assertEquals((long) 142,dataSet.count());
    }

    /**
     * Test the import of the linksWithIDs.csv
     * @throws Exception
     */
    public void testGetLinksWithIDsDataSetFromCsv() throws Exception {
        DataSet dataSet = importer.getLinksWithIDsDataSetFromCsv(Importer.CSV_TYPE_PERFECT,environment);
        assertEquals((long) 5627 , dataSet.count());
    }

}