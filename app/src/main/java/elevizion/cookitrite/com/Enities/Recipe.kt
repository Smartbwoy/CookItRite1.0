package elevizion.cookitrite.com.Enities

class Recipe {


    var mealId: String = ""
    var mealName: String = ""
    var ingredients: String = ""
    var method: String = ""
    lateinit var username:String

    constructor(mealId: String, mealName: String, ingredients: String, method: String) {
        this.mealId = mealId
        this.mealName = mealName
        this.ingredients = ingredients
        this.method = method
    }

    constructor(mealId: String) {
        this.mealId = mealId
    }
    constructor(mealName: String,username: String) {
        this.mealName=mealName
        this.username=username
    }
    fun getUserName(): String {
        return username
    }


}