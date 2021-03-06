/**
 *  Copyright 2014 LiveRamp
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.liveramp.megadesk.base.transaction;

import java.util.Map;

import com.google.common.collect.Maps;

import com.liveramp.megadesk.core.state.Reference;
import com.liveramp.megadesk.core.state.Variable;
import com.liveramp.megadesk.core.transaction.Accessor;
import com.liveramp.megadesk.core.transaction.Commutation;
import com.liveramp.megadesk.core.transaction.Context;
import com.liveramp.megadesk.core.transaction.Dependency;
import com.liveramp.megadesk.core.transaction.DependencyType;
import com.liveramp.megadesk.utils.FormatUtils;

public class BaseContext implements Context {

  private final Map<Reference, Accessor> bindings;

  public BaseContext(Dependency dependency) {
    bindings = Maps.newHashMap();
    for (Variable variable : dependency.snapshots()) {
      addBinding(variable, DependencyType.SNAPSHOT);
    }
    for (Variable variable : dependency.reads()) {
      addBinding(variable, DependencyType.READ);
    }
    for (Variable variable : dependency.writes()) {
      addBinding(variable, DependencyType.WRITE);
    }
    for (Variable variable : dependency.commutations()) {
      addBinding(variable, DependencyType.COMMUTATION);
    }
  }

  private <VALUE> void addBinding(Variable<VALUE> variable, DependencyType dependencyType) {
    bindings.put(variable.reference(), new BaseAccessor<VALUE>(variable.driver().persistence().read(), dependencyType));
  }

  @Override
  public <VALUE> Accessor<VALUE> accessor(Reference<VALUE> reference) {
    if (!bindings.containsKey(reference)) {
      throw new IllegalStateException("Context does not contain " + reference + ". Context: " + this);
    }
    return bindings.get(reference);
  }

  @Override
  public <VALUE> Accessor<VALUE> accessor(Variable<VALUE> variable) {
    return accessor(variable.reference());
  }

  @Override
  public <VALUE> VALUE read(Reference<VALUE> reference) {
    return accessor(reference).read();
  }

  @Override
  public <VALUE> VALUE read(Variable<VALUE> variable) {
    return read(variable.reference());
  }

  @Override
  public <VALUE> void write(Reference<VALUE> reference, VALUE value) {
    accessor(reference).write(value);
  }

  @Override
  public <VALUE> void write(Variable<VALUE> variable, VALUE value) {
    write(variable.reference(), value);
  }

  @Override
  public <VALUE> VALUE commute(Reference<VALUE> reference, Commutation<VALUE> commutation) {
    return accessor(reference).commute(commutation);
  }

  @Override
  public <VALUE> VALUE commute(Variable<VALUE> variable, Commutation<VALUE> commutation) {
    return commute(variable.reference(), commutation);
  }

  @Override
  public String toString() {
    return FormatUtils.formatToString(this, bindings.toString());
  }
}
