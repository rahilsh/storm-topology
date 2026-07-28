/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.di;

import com.google.inject.Guice;
import java.util.Map;
import org.apache.storm.hooks.BaseWorkerHook;
import org.apache.storm.task.WorkerUserContext;

/**
 * Builds the Guice {@link com.google.inject.Injector} once per worker JVM and publishes it as a
 * worker-level resource. This is Storm's recommended way to share an application-level resource
 * across all tasks in a worker (see the Storm "Hooks" documentation): the hook is serialized with
 * the topology, {@code start} runs at worker startup with access to the topology config, and tasks
 * read the resource in their {@code prepare}/{@code open} lifecycle via {@link
 * org.apache.storm.task.TopologyContext#getResource(String)}.
 *
 * <p>Register with {@code TopologyBuilder.addWorkerHook(new GuiceWorkerHook())}.
 */
public class GuiceWorkerHook extends BaseWorkerHook {

  private static final long serialVersionUID = 1L;

  /** Key under which the shared Guice injector is stored in the worker context. */
  public static final String INJECTOR_RESOURCE = "guice.injector";

  @Override
  public void start(Map<String, Object> topoConf, WorkerUserContext context) {
    context.setResource(INJECTOR_RESOURCE, Guice.createInjector(new AppModule()));
  }
}
