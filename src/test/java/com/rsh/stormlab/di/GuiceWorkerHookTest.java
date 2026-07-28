/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.di;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.google.inject.Injector;
import org.apache.storm.task.WorkerUserContext;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class GuiceWorkerHookTest {

  @Test
  void publishesAGuiceInjectorAsAWorkerResource() {
    WorkerUserContext context = mock(WorkerUserContext.class);

    new GuiceWorkerHook().start(java.util.Map.of(), context);

    ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
    verify(context)
        .setResource(org.mockito.ArgumentMatchers.eq("guice.injector"), captor.capture());
    org.junit.jupiter.api.Assertions.assertInstanceOf(Injector.class, captor.getValue());
  }
}
