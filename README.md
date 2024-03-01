# Wizards plugin template
**A quick template for the Wizards server on superawesome**

## How to expand on this plugin
First open the project in Intellij IDEA

Second, add a new class under the `abilities` package

Now you need to name the class and make sure it extends the `Ability` class

Then just implement the methods and fill out all of the methods (Look at documentation in IAbility for guidance)

You can also look at the BoostAbility I created as a basic example

## How to get the actual ability as an item
Right now there's an ingame method and a function of obtaining the item

Ingame you can type `/giveability <id of ability>` and then the ability will be added to your inventory

You can also use the method, `Ability#getAsItem()`, you can get the ability from id by using `AbilityManager.fromID(Integer id)`

