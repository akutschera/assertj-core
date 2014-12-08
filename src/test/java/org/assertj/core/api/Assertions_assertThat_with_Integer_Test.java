/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2014 the original author or authors.
 */
package org.assertj.core.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * Tests for <code>{@link Assertions#assertThat(Integer)}</code>.
 * 
 * @author Alex Ruiz
 */
public class Assertions_assertThat_with_Integer_Test {

  @Test
  public void should_create_Assert() {
    Integer zero = 0;
    AbstractIntegerAssert<?> assertions = Assertions.assertThat(zero);
    assertNotNull(assertions);
  }

  @Test
  public void should_pass_actual() {
    Integer eight = 8;
    AbstractIntegerAssert<?> assertions = Assertions.assertThat(eight);
    assertSame(eight, assertions.actual);
  }
}
