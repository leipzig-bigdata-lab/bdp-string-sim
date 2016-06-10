package org.bdp.string_sim.importer;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;

import java.io.File;

public class Importer {

    public static final String CSV_TYPE_LINKLION = "linklion";
    public static final String CSV_TYPE_PERFECT = "perfect";

    public Importer(){

    }

    /**
     * Imports the concept.csv from the given directory (type).
     * @param type 'perfect' or 'linklion' - choose wisely
     * @param env the flink execution enviroment
     * @return DataSet<Tuple3> the csv representation as flink dataset
     */
    public DataSet<Tuple3<Integer,String,String>> getConceptDataSetFromCsv(String type, ExecutionEnvironment env){
        if(!type.equals(CSV_TYPE_LINKLION) && !type.equals(CSV_TYPE_PERFECT)){
            return null;
        }
        ClassLoader classLoader = getClass().getClassLoader();
        File conceptFile = new File(classLoader.getResource(type + "/concept.csv").getFile());

        //Get concept.csv
        return env.readCsvFile(conceptFile.toString())
                .includeFields("111")
                .fieldDelimiter(";")
                .ignoreInvalidLines()
                .types(Integer.class,String.class,String.class);
    }

    /**
     * Imports the concept_attributes.csv from the given directory (type).
     * @param type 'perfect' or 'linklion' - choose wisely
     * @param env the flink execution enviroment
     * @return DataSet<Tuple4> the csv representation as flink dataset
     */
    public DataSet<Tuple4<Integer,String,String,String>> getConceptAttrDataSetFromCsv(String type, ExecutionEnvironment env){
        if(!type.equals(CSV_TYPE_LINKLION) && !type.equals(CSV_TYPE_PERFECT)){
            return null;
        }

        ClassLoader classLoader = getClass().getClassLoader();
        File conceptAttrFile = new File(classLoader.getResource(type + "/concept_attributes.csv").getFile());

        //Get concept_attributes.csv
        return env.readCsvFile(conceptAttrFile.toString())
                .includeFields("1111")
                .fieldDelimiter(";")
                .ignoreInvalidLines()
                .types(Integer.class,String.class,String.class,String.class);
    }

    /**
     * Imports the linksWithIDs.csv from the given directory (type).
     * @param type 'perfect' or 'linklion' - choose wisely
     * @param env the flink execution enviroment
     * @return DataSet<Tuple2> the csv representation as flink dataset
     */
    public DataSet<Tuple2<Integer,Integer>> getLinksWithIDsDataSetFromCsv(String type, ExecutionEnvironment env){
        if(!type.equals(CSV_TYPE_LINKLION) && !type.equals(CSV_TYPE_PERFECT)){
            return null;
        }

        ClassLoader classLoader = getClass().getClassLoader();
        File linksWithIDsFile = new File(classLoader.getResource(type + "/linksWithIDs.csv").getFile());

        //Get linksWithIDs.csv
        return env.readCsvFile(linksWithIDsFile.toString())
                .includeFields("11")
                .fieldDelimiter(";")
                .ignoreInvalidLines()
                .types(Integer.class,Integer.class);
    }
}