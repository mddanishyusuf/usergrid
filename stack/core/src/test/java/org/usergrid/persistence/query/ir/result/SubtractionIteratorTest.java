/*******************************************************************************
 * Copyright 2012 Apigee Corporation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.usergrid.persistence.query.ir.result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;
import org.usergrid.utils.UUIDUtils;

/**
 * @author tnine
 * 
 */
public class SubtractionIteratorTest {

  @Test
  public void smallerSubtract() {
    UUID id1 = UUIDUtils.minTimeUUID(1);
    UUID id2 = UUIDUtils.minTimeUUID(2);
    UUID id3 = UUIDUtils.minTimeUUID(3);
    UUID id4 = UUIDUtils.minTimeUUID(4);
    UUID id5 = UUIDUtils.minTimeUUID(5);

    // we should get intersection on 1, 3, and 8
    TreeIterator keep = new TreeIterator();
    keep.add(id1);
    keep.add(id2);
    keep.add(id3);
    keep.add(id4);
    keep.add(id5);

    TreeIterator subtract = new TreeIterator();
    subtract.add(id1);
    subtract.add(id3);
    subtract.add(id5);

    SubtractionIterator sub = new SubtractionIterator();
    sub.setKeepIterator(keep);
    sub.setSubtractIterator(subtract);

    // now make sure it's right, only 2 and 8 aren't intersected
    assertTrue(sub.hasNext());
    assertEquals(id2, sub.next());

    assertTrue(sub.hasNext());
    assertEquals(id4, sub.next());

    assertFalse(sub.hasNext());
  }

  @Test
  public void smallerKeep() {

    UUID id1 = UUIDUtils.minTimeUUID(1);
    UUID id2 = UUIDUtils.minTimeUUID(2);
    UUID id3 = UUIDUtils.minTimeUUID(3);
    UUID id4 = UUIDUtils.minTimeUUID(4);
    UUID id5 = UUIDUtils.minTimeUUID(5);
    UUID id6 = UUIDUtils.minTimeUUID(6);

    // we should get intersection on 1, 3, and 8
    TreeIterator keep = new TreeIterator();
    keep.add(id1);
    keep.add(id2);
    keep.add(id5);
    keep.add(id6);

    TreeIterator subtract = new TreeIterator();
    subtract.add(id1);
    subtract.add(id3);
    subtract.add(id4);
    subtract.add(id5);
    subtract.add(id6);

    SubtractionIterator sub = new SubtractionIterator();
    sub.setKeepIterator(keep);
    sub.setSubtractIterator(subtract);

    // now make sure it's right, only 2 and 8 aren't intersected
    assertTrue(sub.hasNext());
    assertEquals(id2, sub.next());

    assertFalse(sub.hasNext());
  }

}
