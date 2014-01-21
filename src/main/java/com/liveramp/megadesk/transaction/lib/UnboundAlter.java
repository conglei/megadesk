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

package com.liveramp.megadesk.transaction.lib;

import com.liveramp.megadesk.state.Value;
import com.liveramp.megadesk.transaction.Arguments;
import com.liveramp.megadesk.transaction.BaseDependency;
import com.liveramp.megadesk.transaction.BaseUnboundProcedure;
import com.liveramp.megadesk.transaction.UnboundProcedure;
import com.liveramp.megadesk.transaction.UnboundTransaction;

public abstract class UnboundAlter<V> extends BaseUnboundProcedure implements UnboundProcedure {

  public UnboundAlter() {
    super(new Arguments("input"),
             BaseDependency.<String>builder().writes("input").build());
  }

  @Override
  public void run(UnboundTransaction transaction) throws Exception {
    transaction.write("input", alter(transaction.<V>read("input")));
  }

  public abstract Value<V> alter(Value<V> value);
}
