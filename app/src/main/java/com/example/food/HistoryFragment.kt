import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.R
import com.example.food.databinding.FragmentHistoryBinding
import com.example.food.db.MainDb
import com.example.food.db.tables.Orders
import kotlinx.coroutines.launch

class HistoryFragment : Fragment(), OrderPopAdapter.OnDeleteClickListener {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var orderPopAdapter: OrderPopAdapter
    private lateinit var listOrders: ArrayList<Orders>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        val db = MainDb.getDb(requireContext())
        val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val login = sharedPreferences.getString("login", "").toString()
        val password = sharedPreferences.getString("password", "").toString()
        listOrders = ArrayList()

        lifecycleScope.launch {
            val user = db.getDao().getUser(login, password)

            if (user != null) {
                val orders = db.getDao().getOrderByUserId(user.id ?: return@launch)

                orders.forEach { order ->
                    val userId = user.id
                    val orderId = order.id

                    if (userId != null && orderId != null) {
                        listOrders.add(Orders(orderId, userId, order.totalPrice))
                    }
                }
                orderPopAdapter.notifyDataSetChanged()
            }
        }

        orderPopAdapter = OrderPopAdapter(requireContext(), listOrders)
        orderPopAdapter.setOnDeleteClickListener(this)
        binding.historyRV.layoutManager = LinearLayoutManager(requireContext())
        binding.historyRV.adapter = orderPopAdapter

        return binding.root
    }

    override fun onDeleteClick(position: Int) {
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<AppCompatButton>(R.id.btn_yes)?.setOnClickListener {
            lifecycleScope.launch {
                val db = MainDb.getDb(requireContext())
                val orderDao = db.getDao()

                val orderToDelete = listOrders.getOrNull(position)

                if (orderToDelete != null && orderToDelete.id != null) {
                    orderDao.deleteOrderById(orderToDelete.id!!)
                    orderDao.deleteOrderFoodItemById(orderToDelete.id!!)

                    listOrders.remove(orderToDelete)
                    orderPopAdapter.notifyItemRemoved(position)
                    orderPopAdapter.notifyDataSetChanged()
                }

                dialogBuilder.dismiss()
            }
        }

        dialogView.findViewById<AppCompatButton>(R.id.btn_consel)?.setOnClickListener {
            dialogBuilder.dismiss()
        }

        dialogBuilder.show()
    }
}
