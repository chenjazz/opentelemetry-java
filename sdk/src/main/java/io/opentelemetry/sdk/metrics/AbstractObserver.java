/*
 * Copyright 2020, OpenTelemetry Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opentelemetry.sdk.metrics;

import io.opentelemetry.metrics.Observer;
import io.opentelemetry.sdk.common.InstrumentationLibraryInfo;
import io.opentelemetry.sdk.metrics.common.InstrumentType;
import io.opentelemetry.sdk.metrics.common.InstrumentValueType;
import io.opentelemetry.sdk.metrics.data.MetricData;
import java.util.Collections;
import java.util.List;

class AbstractObserver extends AbstractInstrument {
  private final boolean monotonic;
  private final InstrumentValueType instrumentValueType;

  AbstractObserver(
      InstrumentDescriptor descriptor,
      InstrumentValueType instrumentValueType,
      MeterProviderSharedState meterProviderSharedState,
      InstrumentationLibraryInfo instrumentationLibraryInfo,
      boolean monotonic) {
    super(
        descriptor,
        meterProviderSharedState,
        instrumentationLibraryInfo,
        new ActiveBatcher(Batchers.getNoop()));
    this.monotonic = monotonic;
    this.instrumentValueType = instrumentValueType;
  }

  final boolean isMonotonic() {
    return monotonic;
  }

  @Override
  List<MetricData> collect() {
    // TODO: Implement this.
    return Collections.emptyList();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AbstractObserver)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    AbstractObserver that = (AbstractObserver) o;

    return monotonic == that.monotonic && instrumentValueType == that.instrumentValueType;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (monotonic ? 1 : 0);
    result = 31 * result + instrumentValueType.hashCode();
    return result;
  }

  abstract static class Builder<B extends Observer.Builder<B, V>, V>
      extends AbstractInstrument.Builder<B, V> implements Observer.Builder<B, V> {
    private boolean monotonic = false;

    Builder(
        String name,
        MeterProviderSharedState meterProviderSharedState,
        InstrumentationLibraryInfo instrumentationLibraryInfo) {
      super(name, meterProviderSharedState, instrumentationLibraryInfo);
    }

    @Override
    public final B setMonotonic(boolean monotonic) {
      this.monotonic = monotonic;
      return getThis();
    }

    final boolean isMonotonic() {
      return this.monotonic;
    }
  }

  // TODO: make this private
  static InstrumentType getInstrumentType(boolean monotonic) {
    return monotonic ? InstrumentType.OBSERVER_MONOTONIC : InstrumentType.OBSERVER_NON_MONOTONIC;
  }
}
