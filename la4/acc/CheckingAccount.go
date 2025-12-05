package acc

import "la4/cust"

type CheckingAccount struct {
	Account
}

func NewCheckingAccount(id string, customer *cust.Customer, balance float64) *CheckingAccount {
	return &CheckingAccount{
		Account: Account{
			id:       id,
			customer: *customer,
			balance:  balance,
		},
	}
}

func (ca *CheckingAccount) Accrue(rate float64) float64 {
	return 0.0
}
