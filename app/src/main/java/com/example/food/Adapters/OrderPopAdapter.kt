import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.food.db.tables.Orders
import com.example.food.databinding.AddOrderBinding

class OrderPopAdapter(
    private val context: Context,
    private var list: ArrayList<Orders>
) : RecyclerView.Adapter<OrderPopAdapter.OrderViewHolder>() {

    private var onDeleteClickListener: OnDeleteClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = AddOrderBinding.inflate(LayoutInflater.from(context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = list[position]

        val orderNumber = order.id.toString()
        holder.totalPrice.text = addRubleSymbol(order.totalPrice.toString())
        holder.orderName.text = "Заказ №$orderNumber"

        holder.deleteBtn.setOnClickListener {
            // Удаление элемента из списка заказов
            onDeleteClickListener?.onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class OrderViewHolder(binding: AddOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        val orderName = binding.orderName
        val totalPrice = binding.totalPrice
        val deleteBtn = binding.deleteBtn
    }

    fun updateList(newList: ArrayList<Orders>) {
        list = newList
        notifyDataSetChanged()
    }

    fun addRubleSymbol(input: String): String {
        return "$input ₽"
    }

    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        onDeleteClickListener = listener
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(position: Int)
    }

}
