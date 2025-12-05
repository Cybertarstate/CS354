// go build bank.go && ./bank
package main

import (
	. "fmt"
	"la4/acc"
	"la4/cust"
)

type Bank struct {
	accounts map[acc.AccountAbstract]struct{}
}

func (b *Bank) Add(a acc.AccountAbstract) {
	b.accounts[a] = struct{}{}

}

func (b *Bank) Accrue(rate float64) {
	ch := make(chan float64)

	for a := range b.accounts {
		go func(acc acc.AccountAbstract) {
			ch <- acc.Accrue(rate)
		}(a)
	}

	total := 0.0
	for range b.accounts {
		total += <-ch
	}

	Println("Total interest accrued:", total)
}

func (b *Bank) ToString() string {
	r := ""
	for a, _ := range b.accounts {
		r += a.ToString() + "\n"
	}
	return r
}

func main() {
	bank := Bank{accounts: make(map[acc.AccountAbstract]struct{})}
	c := cust.NewCustomer("Ann")
	bank.Add(acc.NewCheckingAccount("01001", c, 100.00))
	bank.Add(acc.NewSavingAccount("01002", c, 200.00))
	bank.Accrue(0.02)
	Println(bank.ToString())
}
