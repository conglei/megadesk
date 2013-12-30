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

package com.liveramp.megadesk.refactor.gear;

import java.util.ArrayList;
import java.util.List;

import com.liveramp.megadesk.refactor.node.Lock;
import com.liveramp.megadesk.refactor.node.Node;

public final class Gears {

  private Gears() {
  }

  public static List<Lock> getReadLocks(Gear gear) {
    List<Lock> result = new ArrayList<Lock>();
    for (Node node : gear.reads()) {
      result.add(node.getReadLock());
    }
    return result;
  }

  public static List<Lock> getWriteLocks(Gear gear) {
    List<Lock> result = new ArrayList<Lock>();
    for (Node node : gear.writes()) {
      result.add(node.getWriteLock());
    }
    return result;
  }
}
