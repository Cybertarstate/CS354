package cust

type Customer struct {
	Name string
}

func NewCustomer(name string) *Customer {
	return &Customer{Name: name}
}

func (c *Customer) ToString() string {
	return c.Name
}
