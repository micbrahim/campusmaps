/*
 * Copyright (C) 2023 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package sets;

import java.util.List;

/**
 * Represents an immutable set of points on the real line that is easy to
 * describe, either because it is a finite set, e.g., {p1, p2, ..., pN}, or
 * because it excludes only a finite set, e.g., R \ {p1, p2, ..., pN}. As with
 * FiniteSet, each point is represented by a Java float with a non-infinite,
 * non-NaN value.
 */
public class SimpleSet {
  /**
   * Creates a simple set containing only the given points.
   * @param vals Array containing the points to make into a SimpleSet
   * @spec.requires points != null and has no NaNs, no infinities, and no dups
   * @spec.effects this = {vals[0], vals[1], ..., vals[vals.length-1]}
   */
  public SimpleSet(float[] vals) {
    points = FiniteSet.of(vals);
    comp = false;
  }

  private SimpleSet(boolean comp, FiniteSet points){
    this.comp = comp;
    this.points = points;
  }

  // Points of SimpleSet are stored in a FiniteSet, but with another component which stores if it is a finite set
  // or a ℝ excluding finite numbers set (complement set)
  // RI: points != null
  // AF(this) =
  //  R \ {points.getPoints()}  if comp == true
  //  {points.getPoints()}  otherwise
  private FiniteSet points;
  private Boolean comp;
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof SimpleSet))
      return false;

    SimpleSet other = (SimpleSet) o;
    return this.points.equals(other.points) && this.comp == other.comp;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Returns the number of points in this set.
   * @return N      if this = {p1, p2, ..., pN} and
   *         infty  if this = R \ {p1, p2, ..., pN}
   */
  public float size() {
    // if the set is a complement, return the size +∞, otherwise return the size of this current set
    if (this.comp) {
      return Float.POSITIVE_INFINITY;
    } else {
      return this.points.size();
    }
  }

  /**
   * Returns a string describing the points included in this set.
   * @return the string "R" if this contains every point,
   *     a string of the form "R \ {p1, p2, .., pN}" if this contains all
   *        but {@literal N > 0} points, or
   *     a string of the form "{p1, p2, .., pN}" if this contains
   *        {@literal N >= 0} points,
   *     where p1, p2, ... pN are replaced by the individual numbers. These
   *     floats will be turned into strings in the standard manner (the same as
   *     done by, e.g., String.valueOf(float)).
   */
  public String toString() {
    StringBuilder str = new StringBuilder();
    List<Float> vals = this.points.getPoints();
    int i = 0;

    // special case: empty list's complement
    if (vals.size() == 0 && this.comp){
      return "R";
    }

    // start appending each value to str
    str.append("{");
    // Inv: str = "{" + vals[0] + ", " + vals[1] + ", " + ... + vals[i-1]"
    while (i != vals.size()){
      str.append(vals.get(i));
      if (i < vals.size() - 1) {
        str.append(", ");
      }
      i++;
    }

    str.append("}");

    // add additional representation if list is a complement
    if (this.comp){
      str.insert(0,"R \\ ");
    }

    return str.toString();
  }

  /**
   * Returns a set representing the points R \ this.
   * @return R \ this
   */
  public SimpleSet complement() {
    // changes the sign of complement, resulting in R \ this.
    return new SimpleSet(!this.comp, this.points);
  }

  /**
   * Returns the union of this and other.
   * @param other Set to union with this one.
   * @spec.requires other != null
   * @return this union other
   */
  public SimpleSet union(SimpleSet other) {
    FiniteSet x = this.points;
    FiniteSet y = other.points;
    FiniteSet z;

    if (this.comp && other.comp){
      // when both x and y represent a set of points to be excluded, the elements that are shared between the sets will
      // be excluded after a union of x and y
      z = x.intersection(y);
    } else if (this.comp){
      // when x represents a set of points to be excluded and y is a set of points, their SimpleSet union will be
      // (R \ {x.getPoint()}) + {y.getPoint()}, meaning that the difference set of x and y will be excluded from R
      // while getting the union set of x and y.
      z = x.difference(y);
    } else if (other.comp) {
      // using the same previous reasoning, except switching the representation of x and y around.
      z = y.difference(x);
    } else {
      // when x and y both represent a set of points, FiniteSet's union method can be used to represent the union set.
      z = y.union(x);
    }
    // if either other or this complement is true, a new SimpleSet will be created to represent a set of points to be
    // excluded
    return new SimpleSet(other.comp || this.comp, z);
  }

  /**
   * Returns the intersection of this and other.
   * @param other Set to intersect with this one.
   * @spec.requires other != null
   * @return this intersected with other
   */
  public SimpleSet intersection(SimpleSet other) {
    FiniteSet x = this.points;
    FiniteSet y = other.points;
    FiniteSet z;

    if (this.comp && other.comp){
      // when both x and y represent a set of points to be excluded, each element in them will be excluded, so a
      // union set of y and x will be excluded from R after a difference set is taken.
      z = y.union(x);
    } else if (this.comp){
      // when x represent a set of points to be excluded and y represent a set of points, an intersection set of x and y
      // {T.getPoint()} - {S.getPoint()}. So, the difference set between sets x and y will remain after taking the
      // intersection of them
      z = y.difference(x);
    } else if (other.comp) {
      // using the same previous reasoning, except switching the representation of x and y around.
      z = x.difference(y);
    } else {
      // when both x and y represent sets that contain points, FiniteSet's intersection method can be used to represent
      // the intersection set
      z = y.intersection(x);
    }

    // only when both this and other comp are True will a new SimpleSet be representing a set of points to be
    // excluded
    return new SimpleSet(this.comp && other.comp, z);
  }

  /**
   * Returns the difference of this and other.
   * @param other Set to difference from this one.
   * @spec.requires other != null
   * @return this minus other
   */
  public SimpleSet difference(SimpleSet other) {
    FiniteSet x = this.points;
    FiniteSet y = other.points;
    FiniteSet z;

    if (other.comp && this.comp){
      // when both x and y represent a set of points to be excluded, each element in them will be excluded, so a
      // difference set of y and x will be excluded from R after a difference set is taken.
      z = y.difference(x);
    } else if (this.comp){
      // when x represent a set of points to be excluded and y represents a set of points, a difference set of SimpleSet
      // will be R \ {x.getValue()} - {y.getValue()}. So, the union of x and y will be excluded when getting the
      // difference between x and y
      z = y.union(x);
    } else if (other.comp) {
      // when y represents a set of points to be excluded and x represents a set of points, a difference set of SimpleSet
      // will be {x.getPoint()} - {R \ y.getPoint()}. So, the intersection set will be excluded from R when getting the
      // difference set of x and y
      z = x.intersection(y);
    } else {
      // when x and y both represent a set of points, FiniteSet's difference method can be used to represent
      // the difference set
      z = x.difference(y);
    }
    // Only when this.comp is True and other.comp is False will a new SimpleSet be representing a set of points to
    // be excluded
    return new SimpleSet(this.comp && !other.comp, z);
  }

}
