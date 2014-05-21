trait List[T] {
  def isEmpty: Boolean

  def head: T

  def tail: List[T]
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty = false
}

class Nil[T] extends List[T] {
  def isEmpty = true

  def head: Nothing = throw new NoSuchElementException("Nil.head")

  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
}

object List {
  def apply[T]: List[T] = new Nil[T]
  def apply[T](elem: T): List[T] = new Cons(elem, new Nil)
  def apply[T](elem: T, elem2: T): List[T] = new Cons(elem, new Cons(elem2, new Nil))
}