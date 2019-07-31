/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.geode.benchmark.tests;

import static org.apache.geode.benchmark.topology.ClientServerTopology.Roles.LOCATOR;
import static org.apache.geode.benchmark.topology.ClientServerTopology.Roles.SERVER;
import static org.apache.geode.distributed.ConfigurationProperties.LOG_LEVEL;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import org.apache.geode.benchmark.tasks.CreatePdxFromJSONTask;
import org.apache.geode.benchmark.topology.ClientServerTopology;
import org.apache.geode.perftest.PerformanceTest;
import org.apache.geode.perftest.TestConfig;
import org.apache.geode.perftest.TestRunners;

/**
 * Benchmark of puts on a replicated region.
 */
public class CreatePdxFromJSONBenchmark implements PerformanceTest {

  private int batchSize = 10000;

  public CreatePdxFromJSONBenchmark() {}

  public void setBatchSize(int batchSize) {
    this.batchSize = batchSize;
  }

  @Test
  public void run() throws Exception {
    TestRunners.defaultRunner().runTest(this);
  }

  @Override
  public TestConfig configure() {
    TestConfig config = GeodeBenchmark.createConfig();
    Properties customProps = new Properties();
    customProps.setProperty(LOG_LEVEL, "WARN");
    config.props(SERVER, customProps);
    config.props(LOCATOR, customProps);
    ClientServerTopology.configure(config);
    config.workload(new CreatePdxFromJSONTask(batchSize), SERVER);
    return config;

  }
}