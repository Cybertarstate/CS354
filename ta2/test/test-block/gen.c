#include <stdio.h>
int main() {
  double a, b, d, c;
  {
    a = 1;
    b = 2;
    d = 0;
    printf("%.2f\n", (double)(a));
    printf("%.2f\n", (double)(b));
    {
      c = a + b;
      printf("%.2f\n", (double)(c));
    }

    if (a < b) {
      printf("%.2f\n", (double)(100));
    } else {
      printf("%.2f\n", (double)(200));
      while (a < 5) {
        a = a + 1;
        printf("%.2f\n", (double)(a));
      }

      d = (a * 2) - (b + 3);
      printf("%.2f\n", (double)(d));
    }
  }

  return 0;
}
