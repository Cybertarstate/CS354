package acc

import "la4/cust"

type SavingAccount struct {
	Account
	interest float64
}

func NewSavingAccount(id string, customer *cust.Customer, balance float64) *SavingAccount {
	return &SavingAccount{
		Account: Account{
			id:       id,
			customer: *customer,
			balance:  balance,
		},
	}
}

func (sa *SavingAccount) Accrue(rate float64) float64 {
	interest := sa.balance * rate
	sa.balance += interest
	sa.interest += interest
	return interest
}
