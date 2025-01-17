/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.sdk.metrics.data;

import com.google.auto.value.AutoValue;
import io.opentelemetry.api.common.Attributes;
import javax.annotation.concurrent.Immutable;

/** An {@link ExemplarData} with {@code long} measurments. */
@Immutable
@AutoValue
public abstract class LongExemplarData implements ExemplarData {

  /**
   * Construct a new exemplar.
   *
   * @param filteredAttributes The set of {@link Attributes} not already associated with the {@link
   *     PointData}.
   * @param recordTimeNanos The time when the sample qas recorded in nanoseconds.
   * @param spanId (optional) The associated SpanId.
   * @param traceId (optional) The associated TraceId.
   * @param value The value recorded.
   */
  public static LongExemplarData create(
      Attributes filteredAttributes,
      long recordTimeNanos,
      String spanId,
      String traceId,
      long value) {
    return new AutoValue_LongExemplarData(
        filteredAttributes, recordTimeNanos, spanId, traceId, value);
  }

  LongExemplarData() {}

  /** Numerical value of the measurement that was recorded. */
  public abstract long getValue();

  @Override
  public final double getValueAsDouble() {
    return (double) getValue();
  }
}
