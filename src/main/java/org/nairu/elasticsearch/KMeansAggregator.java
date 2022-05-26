// package org.nairu.elasticsearch;

// import java.util.Map;

// import org.elasticsearch.common.io.stream.Writeable.Reader;
// import org.elasticsearch.common.xcontent.ContextParser;
// import org.elasticsearch.common.xcontent.ParseField;
// import org.elasticsearch.plugins.SearchPlugin.AggregationSpec;
// import org.elasticsearch.search.DocValueFormat;
// import org.elasticsearch.search.aggregations.AggregationBuilder;
// import org.elasticsearch.search.aggregations.Aggregator;
// import org.elasticsearch.search.aggregations.AggregatorFactories;
// import org.elasticsearch.search.aggregations.CardinalityUpperBound;
// import org.elasticsearch.search.aggregations.bucket.BestBucketsDeferringCollector;
// import org.elasticsearch.search.aggregations.bucket.DeferableBucketAggregator;
// import org.elasticsearch.search.aggregations.support.AggregationContext;
// import org.elasticsearch.search.aggregations.support.ValuesSource;
// import org.elasticsearch.search.aggregations.support.ValuesSourceConfig;
// import java.io.IOException;

//  class KMeansAggregator extends DeferableBucketAggregator {

//     static KMeansAggregator build(
//         String name,
//         AggregatorFactories factories,
//         int targetClusters,
//         ValuesSourceConfig valuesSourceConfig,
//         AggregationContext context,
//         Aggregator parent,
//         CardinalityUpperBound cardinality,
//         Map<String, Object> metadata
//     ) throws IOException {
//         return KMeansAggregator(name, factories, targetClusters, valuesSourceConfig, context, parent, metadata);
//     }

//     private final ValuesSource.GeoPoint valuesSource;
//     private final DocValueFormat formatter;
    
//     /**
//      * A reference to the collector so we can
//      * {@link BestBucketsDeferringCollector#rewriteBuckets}.
//      */
//     private BestBucketsDeferringCollector deferringCollector;

//     protected final int targetClusters;

//     private KMeansAggregator(
//         String name,
//         AggregatorFactories factories,
//         int targetClusters,
//         ValuesSourceConfig valuesSourceConfig,
//         AggregationContext context,
//         Aggregator parent,
//         Map<String, Object> metadata
//     ) throws IOException {
//         super(name, factories, context, parent, metadata);
//         this.targetClusters = targetClusters;
//         this.valuesSource = valuesSourceConfig.hasValues() ? (ValuesSource.GeoPoint) valuesSourceConfig.getValuesSource() : null;
//         this.formatter = valuesSourceConfig.format();
//     }
// }