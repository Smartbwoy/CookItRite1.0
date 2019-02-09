package elevizion.cookitrite.com.Enities

import java.util.ArrayList

class Recipe {
    internal lateinit var mealName: String
    internal lateinit var username:String

    constructor(mealName: String, username: String) {
        this.mealName = mealName
        this.username = username
    }

    fun getMealName(): String {
        return mealName
    }

    fun getUserName(): String {
        return username
    }

    fun setMealName(mealName: String) {
        this.mealName = mealName
    }

    fun setUserName(username: String) {
        this.username = username
    }


}