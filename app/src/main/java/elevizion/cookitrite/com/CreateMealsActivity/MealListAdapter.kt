package elevizion.cookitrite.com.CreateMealsActivity

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import elevizion.cookitrite.com.Enities.Recipe
import elevizion.cookitrite.com.R

class MealListAdapter (val items : ArrayList<Recipe>) : RecyclerView.Adapter<ViewHolder>(){

    // Gets the number of recipe in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        //return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewmeal,parent,false));
        val v= LayoutInflater.from(p0.getContext()).inflate(R.layout.card_view_meal, p0, false)
        return ViewHolder(v)
    }

    // Binds each recipe in the ArrayList to a view
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.creator.text = items.get(p1).getUserName()
        p0.recipeName.text = items.get(p1).getMealName()
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each recipe to recycler view
    internal lateinit var recipeName: TextView
    internal lateinit var creator: TextView

    init{
        recipeName = itemView.findViewById(R.id.meal_name)
        creator = itemView.findViewById(R.id.creator)
    }

}