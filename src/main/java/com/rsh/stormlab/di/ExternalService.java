/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.di;

import com.google.inject.Inject;

public class ExternalService {

  private final DummyClass dummyClass;

  @Inject
  public ExternalService(DummyClass dummyClass) {
    this.dummyClass = dummyClass;
  }

  public String getSomething() {
    return dummyClass.something();
  }
}
