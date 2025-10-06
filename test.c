class Circle {
  init(radius) {
    this.radius = radius;
  }

  area {
    return 3.141592653 * this.radius * this.radius;
  }

  class test() {
    print "hello world";
  }

  test2() {
    print "cannot use without instance";
  }
}

var circle = Circle(4);
print circle.area; // Prints roughly "50.2655".

