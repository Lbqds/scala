/*                     __                                               *\
**     ________ ___   / /  ___     Scala API                            **
**    / __/ __// _ | / /  / _ |    (c) 2003, LAMP/EPFL                  **
**  __\ \/ /__/ __ |/ /__/ __ |                                         **
** /____/\___/_/ |_/____/_/ | |                                         **
**                          |/                                          **
** $Id$
\*                                                                      */

package scala.collection.mutable;


/** This extensible class may be used as a basis for implementing linked
 *  list. Type variable <code>A</code> refers to the element type of the
 *  list, type variable <code>This</code> is used to model self types of
 *  linked lists.
 *
 *  @author  Matthias Zenger
 *  @version 1.0, 08/07/2003
 */
[serializable]
abstract class SingleLinkedList[A, This >: AllRef <: SingleLinkedList[A, This]]
               requires This
               extends AnyRef with Seq[A] {

    var elem: A = _;

    var next: This = null;

    def length: Int = 1 + (if (next == null) 0 else next.length);

    def append(that: This): Unit =
        if (next == null) { next = that; } else next.append(that);

    def insert(that: This): Unit = if (that != null) {
        that.append(next);
        next = that;
    }

    def apply(n: Int): A = {
        if (n == 0) elem
        else if (next == null) error("unknown element")
        else next.apply(n - 1);
    }

    def get(n: Int): Option[A] = {
        if (n == 0) Some(elem)
        else if (next == null) None
        else next.get(n - 1);
    }

    def elements: Iterator[A] = new Iterator[A] {
        var elems = SingleLinkedList.this;
        def hasNext = (elems != null);
        def next = {
            val res = elems.elem;
            elems = elems.next;
            res;
        }
    }

    override def toList: List[A] =
        if (next == null) (elem :: Nil) else (elem :: next.toList);

}
