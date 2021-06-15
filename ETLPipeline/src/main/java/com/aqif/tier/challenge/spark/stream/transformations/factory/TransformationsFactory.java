package com.aqif.tier.challenge.spark.stream.transformations.factory;

import com.aqif.tier.challenge.spark.stream.transformations.ITransformations;
import com.aqif.tier.challenge.spark.stream.transformations.SegmentTrackTransformations;
import com.aqif.tier.challenge.spark.stream.transformations.WeatherTransformations;

public class TransformationsFactory implements ITransformationsFactory {
    @Override
    public ITransformations getTransformations(TransformationsType transformationsType) {
        ITransformations transformations = null;
        switch (transformationsType) {
            case WeatherTransformations:
                transformations = new WeatherTransformations();
                break;
            case SegmentTrackTransformations:
                transformations = new SegmentTrackTransformations();
                break;
        }
        return transformations;
    }
}
