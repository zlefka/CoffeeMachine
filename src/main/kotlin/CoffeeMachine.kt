class CoffeeMachine(
    private var water: Int = 400,
    private var milk: Int = 540,
    private var coffeeBeans: Int = 120,
    private var disposableCups: Int = 9,
    private var money: Int = 550
) {

    private enum class TypeOfCoffee(
        val water: Int, val milk: Int, val coffeeBeans: Int, val price: Int
    ) {
        ESPRESSO(250, 0, 16, 4),
        LATTE(350, 75, 20, 7),
        CAPPUCCINO(200, 100, 12, 6)
    }

    fun startProgram() {
        do {
            println("Write action (buy, fill, take, remaining, exit):")
            val input = readln()
            when (input) {
                "buy"       -> buyCoffee()
                "fill"      -> fillIngredients()
                "take"      -> takeMoney()
                "remaining" -> printMe()
            }
        } while (input != "exit")
    }

    private fun printMe() {
        println(
            "The coffee machine has:\n" +
                    "$water ml of water\n" +
                    "$milk ml of milk\n" +
                    "$coffeeBeans g of coffee beans\n" +
                    "$disposableCups disposable cups\n" +
                    "\$$money of money"
        )
    }

    private fun buyCoffee() {
        println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:")
        when (val input = readln().trim()) {
            "back"        -> return
            "1", "2", "3" -> {
                val coffeeType = when (input) {
                    "1"  -> TypeOfCoffee.ESPRESSO
                    "2"  -> TypeOfCoffee.LATTE
                    else -> TypeOfCoffee.CAPPUCCINO
                }
                if (checkResourcesFor(coffeeType)) {
                    changeIngredients(coffeeType)
                }
            }

            else          -> println("Invalid selection. Please enter 1, 2, 3 or 'back'")
        }
    }

    private fun changeIngredients(type: TypeOfCoffee) {
        water -= type.water
        milk -= type.milk
        coffeeBeans -= type.coffeeBeans
        money += type.price
        disposableCups -= 1
    }

    private fun checkResourcesFor(type: TypeOfCoffee): Boolean {
        when {
            water < type.water             -> {
                println("Sorry, not enough water!")
                return false
            }

            milk < type.milk               -> {
                println("Sorry, not enough milk!")
                return false
            }

            coffeeBeans < type.coffeeBeans -> {
                println("Sorry, not enough coffee beans!")
                return false
            }

            disposableCups < 1             -> {
                println("Sorry, not enough disposable cups!")
                return false
            }

            else                           -> {
                println("I have enough resources, making you a coffee!")
                return true
            }
        }
    }

    private fun fillIngredients() {
        water += readPositiveInt("Write how many ml of water you want to add:")
        milk += readPositiveInt("Write how many ml of milk you want to add:")
        coffeeBeans += readPositiveInt("Write how many grams of coffee beans you want to add:")
        disposableCups += readPositiveInt("Write how many disposable cups you want to add:")
    }

    private fun readPositiveInt(prompt: String): Int {
        while (true) {
            println(prompt)
            val input = readlnOrNull()?.toIntOrNull()
            if (input != null && input >= 0) {
                return input
            }
            println("Please enter a positive number")
        }
    }

    private fun takeMoney() {
        println("I gave you \$${money}")
        money = 0
    }
}

fun main() {
    CoffeeMachine().startProgram()
}