/*
 * Copyright 2014-2018 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.spectator.impl.matcher;

import java.util.Objects;

/**
 * Matcher that checks if the string starts with a given character sequence.
 */
final class StartsWithMatcher implements Matcher {

  private final String pattern;
  private final boolean ignoreCase;

  /** Create a new instance. */
  StartsWithMatcher(String pattern) {
    this(pattern, false);
  }

  /** Create a new instance. */
  StartsWithMatcher(String pattern, boolean ignoreCase) {
    this.pattern = pattern;
    this.ignoreCase = ignoreCase;
  }

  /** Pattern to check for at the start of the string. */
  String pattern() {
    return pattern;
  }

  @Override
  public int matches(String str, int start, int length) {
    boolean matched = ignoreCase
        ? str.regionMatches(true, 0, pattern, 0, pattern.length())
        : str.startsWith(pattern);
    return matched ? pattern.length() : Constants.NO_MATCH;
  }

  @Override
  public String prefix() {
    return pattern;
  }

  @Override
  public int minLength() {
    return pattern.length();
  }

  @Override
  public boolean isStartAnchored() {
    return true;
  }

  @Override
  public String toString() {
    return "^" + PatternUtils.escape(pattern);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StartsWithMatcher that = (StartsWithMatcher) o;
    return ignoreCase == that.ignoreCase && Objects.equals(pattern, that.pattern);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pattern, ignoreCase);
  }
}
