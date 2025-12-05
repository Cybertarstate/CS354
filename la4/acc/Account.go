package acc

import (
	"la4/cust"

	. "fmt"
)

type Account struct {
	id       string
	balance  float64
	customer cust.Customer
}

type AccountAbstract interface {
	Accrue(rate float64) float64
	ToString() string
}

func (a *Account) GetBalance() float64 {
	return a.balance
}

func (a *Account) Deposit(amount float64) {
	a.balance += amount
}

func (a *Account) Withdraw(amount float64) {
	a.balance -= amount
}

func (a *Account) ToString() string {
	return a.id + ":" + a.customer.ToString() + ":" + Sprintf("%.2f", a.balance)
}
