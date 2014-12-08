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
package org.assertj.core.internal.objects;

import static org.assertj.core.error.ShouldBeEqualByComparingOnlyGivenFields.shouldBeEqualComparingOnlyGivenFields;
import static org.assertj.core.error.ShouldBeInstance.shouldBeInstance;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.test.TestFailures.failBecauseExpectedAssertionErrorWasNotThrown;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.ObjectsBaseTest;
import org.assertj.core.test.Employee;
import org.assertj.core.test.Jedi;
import org.assertj.core.test.Name;
import org.assertj.core.test.Player;
import org.assertj.core.util.introspection.FieldSupport;
import org.assertj.core.util.introspection.IntrospectionError;
import org.junit.Test;

public class Objects_assertIsEqualToComparingOnlyGivenFields_Test extends ObjectsBaseTest {

  @Test
  public void should_pass_when_selected_fields_are_equal() {
	Jedi actual = new Jedi("Yoda", "Green");
	Jedi other = new Jedi("Yoda", "Green");
	objects.assertIsEqualToComparingOnlyGivenFields(someInfo(), actual, other, "name", "lightSaberColor");
  }

  @Test
  public void should_pass_when_selected_fields_and_nested_fields_accessed_with_getters_are_equal() {
	Player rose = new Player(new Name("Derrick", "Rose"), "Chicago Bulls");
	Player jalen = new Player(new Name("Derrick", "Coleman"), "Chicago Bulls");
	objects.assertIsEqualToComparingOnlyGivenFields(someInfo(), rose, jalen, "team", "name.first");
  }

  @Test
  public void should_pass_when_selected_fields_and_nested_public_fields_are_equal() {
	Player rose = new Player(new Name("Derrick", "Rose"), "Chicago Bulls");
	rose.nickname = new Name("Crazy", "Duncks");
	Player jalen = new Player(new Name("Derrick", "Coleman"), "Chicago Bulls");
	jalen.nickname = new Name("Crazy", "Defense");
	objects.assertIsEqualToComparingOnlyGivenFields(someInfo(), rose, jalen, "team", "nickname.first");
  }

  @Test
  public void should_pass_even_if_non_accepted_fields_differ() {
	Jedi actual = new Jedi("Yoda", "Green");
	Jedi other = new Jedi("Yoda", "Blue");
	objects.assertIsEqualToComparingOnlyGivenFields(someInfo(), actual, other, "name");
  }

  @Test
  public void should_pass_when_field_value_is_null() {
	Jedi actual = new Jedi("Yoda", null);
	Jedi other = new Jedi("Yoda", null);
	objects.assertIsEqualToComparingOnlyGivenFields(someInfo(), actual, other, "name", "lightSaberColor");
  }

  @Test
  public void should_fail_if_actual_is_null() {
	thrown.expectAssertionError(actualIsNull());
	Jedi other = new Jedi("Yoda", "Green");
	objects.assertIsEqualToComparingOnlyGivenFields(someInfo(), null, other, "name", "lightSaberColor");
  }

  @Test
  public void should_fail_when_some_selected_field_values_differ() {
	AssertionInfo info = someInfo();
	Jedi actual = new Jedi("Yoda", "Green");
	Jedi other = new Jedi("Yoda", "Blue");
	try {
	  objects.assertIsEqualToComparingOnlyGivenFields(info, actual, other, "name", "lightSaberColor");
	} catch (AssertionError err) {
	  List<Object> expected = newArrayList((Object) "Blue");
	  List<Object> rejected = newArrayList((Object) "Green");
	  verify(failures).failure(info,
		                       shouldBeEqualComparingOnlyGivenFields(actual, newArrayList("lightSaberColor"), rejected,
		                                                             expected,
		                                                             newArrayList("name", "lightSaberColor")));
	  return;
	}
	failBecauseExpectedAssertionErrorWasNotThrown();
  }

  @Test
  public void should_fail_when_some_inherited_field_values_differ() {
	AssertionInfo info = someInfo();
	Jedi actual = new Jedi("Yoda", "Green");
	Jedi other = new Jedi("Luke", "Green");
	try {
	  objects.assertIsEqualToComparingOnlyGivenFields(info, actual, other, "name", "lightSaberColor");
	} catch (AssertionError err) {
	  List<Object> expected = newArrayList((Object) "Luke");
	  List<Object> rejected = newArrayList((Object) "Yoda");
	  verify(failures).failure(info,
		                       shouldBeEqualComparingOnlyGivenFields(actual, newArrayList("name"), rejected, expected,
		                                                             newArrayList("name", "lightSaberColor")));
	  return;
	}
	failBecauseExpectedAssertionErrorWasNotThrown();
  }

  @Test
  public void should_fail_when_objects_to_compare_are_of_different_types() {
	AssertionInfo info = someInfo();
	Jedi actual = new Jedi("Yoda", "Green");
	Employee other = new Employee();
	try {
	  objects.assertIsEqualToComparingOnlyGivenFields(info, actual, other, "name");
	} catch (AssertionError err) {
	  verify(failures).failure(info, shouldBeInstance(other, actual.getClass()));
	  return;
	}
	failBecauseExpectedAssertionErrorWasNotThrown();
  }

  @Test
  public void should_fail_when_selected_field_does_not_exist() {
	thrown.expect(IntrospectionError.class,
	              "Unable to obtain the value of <'age'> field/property from <Yoda the Jedi>, expecting a public field or getter");
	Jedi actual = new Jedi("Yoda", "Green");
	Jedi other = new Jedi("Yoda", "Blue");
	objects.assertIsEqualToComparingOnlyGivenFields(someInfo(), actual, other, "age");
  }
  
  @Test
  public void should_fail_when_selected_field_is_not_accessible_and_private_field_use_is_forbidden() {
	boolean allowedToUsePrivateFields = FieldSupport.comparison().isAllowedToUsePrivateFields();
    Assertions.setAllowComparingPrivateFields(false);
	thrown.expect(IntrospectionError.class,
		"Unable to obtain the value of <'strangeNotReadablePrivateField'> field/property from <Yoda the Jedi>, expecting a public field or getter");
	Jedi actual = new Jedi("Yoda", "Green");
	Jedi other = new Jedi("Yoda", "Blue");
	objects.assertIsEqualToComparingOnlyGivenFields(someInfo(), actual, other, "strangeNotReadablePrivateField");
    Assertions.setAllowComparingPrivateFields(allowedToUsePrivateFields);
  }

  @Test
  public void should_pass_when_selected_field_is_private_and_private_field_use_is_allowed() {
	Jedi actual = new Jedi("Yoda", "Green");
	Jedi other = new Jedi("Yoda", "Blue");
	objects.assertIsEqualToComparingOnlyGivenFields(someInfo(), actual, other, "strangeNotReadablePrivateField");
  }
  
}
