// package org.nairu.elasticsearch;

// import java.io.IOException;
// import java.util.Map;

// import org.elasticsearch.Version;
// import org.elasticsearch.common.io.stream.StreamInput;
// import org.elasticsearch.common.io.stream.StreamOutput;
// import org.elasticsearch.common.settings.Settings;
// import org.elasticsearch.common.xcontent.ObjectParser;
// import org.elasticsearch.common.xcontent.ParseField;
// import org.elasticsearch.common.xcontent.XContentBuilder;
// import org.elasticsearch.search.aggregations.AggregationBuilder;
// import org.elasticsearch.search.aggregations.AggregatorFactories.Builder;
// import org.elasticsearch.search.aggregations.AggregatorFactory;
// import org.elasticsearch.search.aggregations.MultiBucketConsumerService;
// import org.elasticsearch.search.aggregations.support.AggregationContext;
// import org.elasticsearch.search.aggregations.support.CoreValuesSourceType;
// import org.elasticsearch.search.aggregations.support.ValuesSourceAggregationBuilder;
// import org.elasticsearch.search.aggregations.support.ValuesSourceAggregatorFactory;
// import org.elasticsearch.search.aggregations.support.ValuesSourceConfig;
// import org.elasticsearch.search.aggregations.support.ValuesSourceRegistry;
// import org.elasticsearch.search.aggregations.support.ValuesSourceRegistry.RegistryKey;
// import org.elasticsearch.search.aggregations.support.ValuesSourceType;

// public class KMeansAggregatorBuilder extends ValuesSourceAggregationBuilder<KMeansAggregatorBuilder> {
//     public static final String NAME = "kmeans";
//     public static final ValuesSourceRegistry.RegistryKey<KMeansAggregatorSupplier> REGISTRY_KEY =
//         new ValuesSourceRegistry.RegistryKey<>(NAME, KMeansAggregatorSupplier.class);

//     private static final ParseField NUM_CLUSTERS_FIELD = new ParseField("clusters");

//     public static final ObjectParser<KMeansAggregatorBuilder, String> PARSER =
//         ObjectParser.fromBuilder(NAME, KMeansAggregatorBuilder::new);
//     static {
//         ValuesSourceAggregationBuilder.declareFields(PARSER, true, true, true);
//         PARSER.declareInt(KMeansAggregatorBuilder::setNumClusters, NUM_CLUSTERS_FIELD);
//     }

//     public KMeansAggregatorBuilder(String name) {
//         super(name);
//     }

//     private int numClusters = 10;

//     @Override
//     public String getType() {
//         return NAME;
//     }

//     /** Read from a stream, for internal use only. */
//     public KMeansAggregatorBuilder(StreamInput in) throws IOException {
//         super(in);
//         numClusters = in.readVInt();
//     }

//     @Override
//     protected void innerWriteTo(StreamOutput out) throws IOException {
//         out.writeVInt(numClusters);
//     }

//     @Override
//     protected RegistryKey<?> getRegistryKey() {
//         return REGISTRY_KEY;
//     }

//     @Override
//     protected ValuesSourceType defaultValueSourceType() {
//         return CoreValuesSourceType.GEOPOINT;
//     }

//     @Override
//     protected ValuesSourceAggregatorFactory innerBuild(AggregationContext context, ValuesSourceConfig config,
//             AggregatorFactory parent, Builder subFactoriesBuilder) throws IOException {

//         // This is where the magic happens.
//         KMeansAggregatorSupplier aggregatorSupplier =
//             context.getValuesSourceRegistry().getAggregator(REGISTRY_KEY, config);

//         Settings settings = context.getIndexSettings().getNodeSettings();
//         int maxBuckets = MultiBucketConsumerService.MAX_BUCKET_SETTING.get(settings);
//         if (numClusters > maxBuckets) {
//             throw new IllegalArgumentException(NUM_CLUSTERS_FIELD.getPreferredName()+
//                 " must be less than " + maxBuckets);
//         }
//         return new KMeansAggregatorFactory(name, config, numClusters, context,
//                                                     parent, subFactoriesBuilder, metadata, aggregatorSupplier);
//     }

//     @Override
//     protected XContentBuilder doXContentBody(XContentBuilder builder, Params params) throws IOException {
//         builder.field(NUM_CLUSTERS_FIELD.getPreferredName(), numClusters);
//         return builder;
//     }

//     protected KMeansAggregatorBuilder(KMeansAggregatorBuilder clone, Builder factoriesBuilder,
//         Map<String, Object> metadata) {
//         super(clone, factoriesBuilder, metadata);
//         this.numClusters = clone.numClusters;
//     }
    
//     @Override
//     protected AggregationBuilder shallowCopy(Builder factoriesBuilder, Map<String, Object> metadata) {
//         return new KMeansAggregatorBuilder(this, factoriesBuilder, metadata);
//     }

//     @Override
//     public BucketCardinality bucketCardinality() {
//         return BucketCardinality.MANY;
//     }

//     public KMeansAggregatorBuilder setNumClusters(int numClusters) {
//         if (numClusters <= 0) {
//             throw new IllegalArgumentException(NUM_CLUSTERS_FIELD.getPreferredName() + " must be greater than 0 for [" + name + "]");
//         }
//         this.numClusters = numClusters;
//         return this;
//     }

//     public int getNumClusters() {
//         return this.numClusters;
//     }
    
// }
