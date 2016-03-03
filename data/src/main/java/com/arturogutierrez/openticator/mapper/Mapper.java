package com.arturogutierrez.openticator.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Mapper<FROM, TO> {

  public abstract TO transform(FROM values);

  public abstract FROM reverseTransform(TO values);

  public List<TO> transform(Collection<FROM> values) {
    List<TO> transformedValues = new ArrayList<>(values.size());
    for (FROM value : values) {
      transformedValues.add(transform(value));
    }
    return transformedValues;
  }

  public List<FROM> reverseTransform(Collection<TO> values) {
    List<FROM> transformedValues = new ArrayList<>(values.size());
    for (TO value : values) {
      transformedValues.add(reverseTransform(value));
    }
    return transformedValues;
  }
}
