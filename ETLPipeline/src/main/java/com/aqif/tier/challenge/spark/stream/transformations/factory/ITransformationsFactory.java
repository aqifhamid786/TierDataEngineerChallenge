package com.aqif.tier.challenge.spark.stream.transformations.factory;

import com.aqif.tier.challenge.spark.stream.transformations.ITransformations;

public interface ITransformationsFactory {
    ITransformations getTransformations(TransformationsType transformationsType);
}
