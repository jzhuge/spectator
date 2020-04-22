/*
 * Copyright 2014-2019 Netflix, Inc.
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
package com.netflix.spectator.spark;

import com.codahale.metrics.MetricRegistry;
import com.netflix.spectator.api.Clock;
import com.netflix.spectator.api.Spectator;
import com.netflix.spectator.metrics3.MetricsRegistry;

import org.apache.spark.metrics.source.Source;

/**
 * Spark metrics source for Spectator global registry.
 */
public class SpectatorSource implements Source {

  private final MetricRegistry metricRegistry;

  public SpectatorSource() {
    this(Clock.SYSTEM, new MetricRegistry());
  }

  public SpectatorSource(Clock clock, MetricRegistry metricRegistry) {
    this.metricRegistry = metricRegistry;
    Spectator.globalRegistry().add(new MetricsRegistry(clock, metricRegistry));
  }

  @Override
  public String sourceName() {
    return "spectator";
  }

  @Override
  public MetricRegistry metricRegistry() {
    return metricRegistry;
  }
}
