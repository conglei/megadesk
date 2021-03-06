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

package com.liveramp.megadesk.recipes.pipeline;

import com.liveramp.megadesk.base.transaction.BaseDependency;
import com.liveramp.megadesk.core.transaction.Context;
import com.liveramp.megadesk.recipes.gear.ConditionalGear;
import com.liveramp.megadesk.recipes.gear.Outcome;

public abstract class Operator extends ConditionalGear {

  private Pipeline pipeline;

  protected Operator(BaseDependency dependency, Pipeline pipeline) {
    super(dependency);
    this.pipeline = pipeline;
  }

  @Override
  public Outcome check(Context context) {
    if (pipeline.shouldShutdown()) {
      return Outcome.ABANDON;
    } else {
      return Outcome.SUCCESS;
    }
  }
}
