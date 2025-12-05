#include <stdio.h>
int main() {
  double x;
  scanf("%lf", &x);
  while (x > 0) {
    printf("%.2f\n", (double)(x));
    x = x - 1;
  }

  return 0;
}
