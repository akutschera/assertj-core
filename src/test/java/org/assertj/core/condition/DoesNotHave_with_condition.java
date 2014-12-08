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
package org.assertj.core.condition;

import static junit.framework.Assert.assertEquals;
import static org.assertj.core.condition.DoesNotHave.doesNotHave;

import org.assertj.core.api.Condition;
import org.assertj.core.api.TestCondition;
import org.junit.Test;


/**
 * Tests for <code>{@link DoesNotHave#doesNotHave(Condition)}</code>.
 * 
 * @author Nicolas François
 */
public class DoesNotHave_with_condition {

  @Test
  public void should_create_new_doesNotHave_with_passed_Condition() {
    TestCondition<Object> condition = new TestCondition<Object>();
    Condition<Object> created = doesNotHave(condition);
    assertEquals(DoesNotHave.class, created.getClass());
    DoesNotHave<Object> doesNotHave = (DoesNotHave<Object>) created;
    assertEquals(condition, doesNotHave.condition);
  }

}
