package org.bdp.string_sim.transformation;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.util.Collector;
import org.bdp.string_sim.utilities.Dictionary;
import org.bdp.string_sim.types.ResultTuple5;
import org.bdp.string_sim.utilities.DiceMetric;
import org.bdp.string_sim.utilities.Tokenizer;

import java.util.ArrayList;
import java.util.Collections;

public class SortMergeFlatMap implements FlatMapFunction<Tuple4<Integer,String,Integer,String>,ResultTuple5> {

    /**
     * Only tuples with a similarity value above (>=) the threshold will be collected.
     * The threshold has to be between 0 and 1.
     */
    private double threshold = 0;

    /**
     * Used by the tokenizer. The size of a n-gram.
     */
    private int nGramDigits = 3;

    /**
     * Extended constructor. The threshold and digit size can be specified.
     *
     * @param threshold Only tuples with a similarity value above (>=) the threshold will be collected. The threshold has to be between 0 and 1.
     * @param nGramDigits Used by the tokenizer. The size of a n-gram.
     */
    public SortMergeFlatMap(double threshold, int nGramDigits) {
        if(threshold >= 0 && threshold <= 1){
            this.threshold = threshold;
        }
        if(nGramDigits > 0){
            this.nGramDigits = nGramDigits;
        }
    }

    /**
     * Takes the tuple4 with both strings to compare and calculates a similarity value using sort merge algorithm.
     * For every comparison a new dictionary will be created.
     *
     * @param input the tuple4 with the entities to compare
     * @param collector collects the result tuple5
     * @throws Exception
     */
    @Override
    public void flatMap(Tuple4<Integer, String, Integer, String> input, Collector<ResultTuple5> collector) throws Exception {
        Tokenizer tokenizer = new Tokenizer(nGramDigits);
        Dictionary dictionary = new Dictionary();

        ArrayList<String> tokenizedStringA = tokenizer.tokenize(input.getField(1));
        ArrayList<String> tokenizedStringB = tokenizer.tokenize(input.getField(3));

        dictionary.add(tokenizedStringA);
        dictionary.add(tokenizedStringB);

        ArrayList<Long> intArrayA = dictionary.getIndexListForNGrams(tokenizedStringA);
        ArrayList<Long> intArrayB = dictionary.getIndexListForNGrams(tokenizedStringB);

        Collections.sort(intArrayA);
        Collections.sort(intArrayB);

        int left = 0;
        int right = 0;
        int overlap = 0;
        float diceSim;

        int lengthA = intArrayA.size();
        int lengthB = intArrayB.size();

        while ((left < lengthA) && (right < lengthB))
        {
            if(intArrayA.get(left) == intArrayB.get(right)){
                overlap++;
                left++;
                right++;
            } else if (intArrayA.get(left) < intArrayB.get(right)){
                left++;
            }else {
                right++;
            }
        }

        diceSim = DiceMetric.calculate(lengthA,lengthB,overlap);

        if(diceSim >= threshold){
            collector.collect(new ResultTuple5(
                    input.getField(0),
                    input.getField(1),
                    input.getField(2),
                    input.getField(3),
                    diceSim
            ));
        }
    }
}