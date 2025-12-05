#include <stdio.h>
int main() {
  double x, y;
  x = 2;
  y = 1;
  printf("%d\n", x > y);

  {
    x = 5;
    y = 5;
    if (x <= y) {
      printf("%.2f\n", (double)(x));
    }
  }

  {
    x = 7;
    y = 7;
    if (x >= y) {
      printf("%.2f\n", (double)(x));
    }
  }

  {
    x = 4;
    y = 9;
    if (x != y) {
      printf("%.2f\n", (double)(x));
    }
  }

  {
    x = 12;
    y = 12;
    if (x == y) {
      printf("%.2f\n", (double)(x));
    }
  }

  {
    x = 12;
    y = 11;
    if (x == y) {
      printf("%.2f\n", (double)(x));
    }
  }

  return 0;
}
