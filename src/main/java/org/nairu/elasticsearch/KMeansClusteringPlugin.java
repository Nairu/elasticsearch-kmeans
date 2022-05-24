package org.nairu.elasticsearch;

import java.util.List;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.SearchPlugin;

public class KMeansClusteringPlugin extends Plugin implements SearchPlugin {
    public static final String PLUGIN_NAME = "elasticsearch-kmeans-clustering";

    private final boolean pluginEnabled;
    private final List<AggregationSpec> aggregations;

    public KMeansClusteringPlugin(Settings settings) {
        this.pluginEnabled = settings.getAsBoolean("kmeans.enabled", true);

    }

    @Override
    public List<AggregationSpec> getAggregations() {
        if (this.pluginEnabled) {

        } else {
            return SearchPlugin.super.getAggregations();
        }
    }
}