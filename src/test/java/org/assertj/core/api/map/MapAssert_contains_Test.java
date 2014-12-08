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
package org.assertj.core.api.map;

import static org.assertj.core.data.MapEntry.entry;
import static org.assertj.core.util.Arrays.array;

import org.assertj.core.api.MapAssert;
import org.assertj.core.api.MapAssertBaseTest;
import org.assertj.core.data.MapEntry;

import static org.mockito.Mockito.verify;


/**
 * Tests for <code>{@link MapAssert#contains(MapEntry...)}</code>.
 * 
 * @author Alex Ruiz
 * @author Nicolas François
 */
public class MapAssert_contains_Test extends MapAssertBaseTest {

  final MapEntry[] entries = array(entry("key1", "value1"), entry("key2", "value2"));

  @Override
  protected MapAssert<Object, Object> invoke_api_method() {
    return assertions.contains(entry("key1", "value1"), entry("key2", "value2"));
  }

  @Override
  protected void verify_internal_effects() {
    verify(maps).assertContains(getInfo(assertions), getActual(assertions), entries);
  }
}
