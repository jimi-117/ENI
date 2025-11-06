package objects
import (
	"fmt"
)

// TODO: Object inerface, weapon, potion, tresor, 

type Object interface {
	Name() string
	use()
}

//-------------------------------- arme -----------------
type Arme struct {Nom string}
func (a Arme) Name() string {
	return a.Nom
}
func (a Arme) Use() {
	fmt.Println("Melee with arme !")
}

//-------------------------------- potion -----------------------
type Potion struct {Nom string}
func (p Potion) Name() string {
	return p.Nom
}
func (p Potion) Use() {
	fmt.Println("+50HP")
}

//-------------------------------- tresor ------------------------
type Treasures struct {Nom string}
func (t Treasures) Name() string {
	return t.Nom
}
func (t Treasures) Use() {
	fmt.Println("")
}
