// package org.nairu.elasticsearch;

// import org.elasticsearch.search.aggregations.Aggregator;
// import org.elasticsearch.search.aggregations.AggregatorFactories;
// import org.elasticsearch.search.aggregations.CardinalityUpperBound;
// import org.elasticsearch.search.aggregations.support.AggregationContext;
// import org.elasticsearch.search.aggregations.support.ValuesSourceConfig;

// import java.io.IOException;
// import java.util.Map;

// public interface KMeansAggregatorSupplier {
//     Aggregator build(
//         String name,
//         AggregatorFactories factories,
//         int clusters,
//         ValuesSourceConfig valuesSourceConfig,
//         AggregationContext context,
//         Aggregator parent,
//         CardinalityUpperBound cardinality,
//         Map<String, Object> metadata
//     ) throws IOException;
// }
