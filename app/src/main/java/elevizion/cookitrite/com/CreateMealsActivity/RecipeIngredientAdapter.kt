package elevizion.cookitrite.com.CreateMealsActivity

import android.content.Context
import android.support.coordinatorlayout.Recipe_Ingredient
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import elevizion.cookitrite.com.R
import kotlinx.android.synthetic.main.list_ingredient_item.view.*
import java.util.*

class RecipeIngredientAdapter(private val context: Context, private val dataSource: ArrayList<Recipe_Ingredient>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View?{
        val rowView = inflater.inflate(R.layout.list_ingredient_item, parent, false)
        val recipe_ingr = getItem(position) as Recipe_Ingredient


        rowView.amount.text = recipe_ingr.quty
        rowView.measurement_type.text = recipe_ingr.measuretype
        rowView.item_name.text = recipe_ingr.ingr_name

        /*rowView.remove_item.setOnClickListener {
            Toast.makeText(this.context, "Click on " + dataSource[position].label, Toast.LENGTH_SHORT).show()
            dataSource.remove(dataSource.get(position))
            val listView = ingrlist as ListView
            listView.adapter = RecipeIngredientAdapter(this.context, dataSource)
            //ingrlist.set=RecipeIngredientAdapter(this.context, dataSource)
        }*/
        return rowView
    }

    override fun getItem(p0: Int): Any {
        return dataSource[p0]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }


}