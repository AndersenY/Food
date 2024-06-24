package com.example.food

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.example.food.Models.PopularModel
import com.example.food.databinding.ActivityDeatailsBinding
import com.example.food.databinding.FragmentProfileBinding
import com.example.food.databinding.FragmentSearchBinding
import com.example.food.db.MainDb
import com.example.food.db.tables.User
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
private lateinit var binding : FragmentProfileBinding
private lateinit var user : User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val db = MainDb.getDb(requireContext())
        val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val last_login = sharedPreferences.getString("login", "").toString()
        val last_password = sharedPreferences.getString("password", "").toString()

        lifecycleScope.launch {
            user = db.getDao().getUser(last_login, last_password)!!
            binding.login.text = user?.name?.toEditable()
            binding.password.text = user?.password?.toEditable()
            binding.email.text = user?.email?.toEditable()
            binding.address.text = user?.address?.toEditable()
            binding.phone.text = user?.phone?.toEditable()
        }


        binding.change.setOnClickListener {
            lifecycleScope.launch {

                db.getDao().updateUser(
                    last_login,
                    last_password,
                    binding.login.text.toString(),
                    binding.password.text.toString(),
                    binding.email.text.toString(),
                    binding.address.text.toString(),
                    binding.phone.text.toString()
                )

                Toast.makeText(requireContext(), "Данные успешно изменены", Toast.LENGTH_SHORT).show()
                val editor = sharedPreferences.edit()
                editor.putString("login",  binding.login.text.toString()) // сохраняем имя пользователя или другие данные
                editor.putString("password", binding.password.text.toString())
                editor.apply()

            }
        }

        binding.exit.setOnClickListener {
            showConfirmationDialog()
        }

        binding.deleteAccount.setOnClickListener {
            showConfirmationDialogForAccountDeletion()
        }

//        binding.backStack.setOnClickListener{
//            requireActivity().supportFragmentManager.popBackStack()
//        }

        return binding.root
    }

    private fun showConfirmationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<AppCompatButton>(R.id.btn_yes)?.setOnClickListener {
            val intent = Intent(requireContext(), LoginUserActivity::class.java)
            Toast.makeText(requireContext(), "Возвращайтесь скорее!", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            dialogBuilder.dismiss()
        }

        dialogView.findViewById<AppCompatButton>(R.id.btn_consel)?.setOnClickListener {
            dialogBuilder.dismiss()
        }

        dialogBuilder.show()
    }

    private fun showConfirmationDialogForAccountDeletion() {
        val dialogView = layoutInflater.inflate(R.layout.alert_dialog, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<AppCompatButton>(R.id.btn_yes)?.setOnClickListener {
            lifecycleScope.launch {
                val db = MainDb.getDb(requireContext())
                val orders = db.getDao().getOrderByUserId(user.id)
                orders.forEach {
                    it.id?.let { it1 -> db.getDao().deleteOrderFoodItemById(it1) }
                }
                user.id?.let { it1 -> db.getDao().deleteOrdersByUserId(it1) }
                db.getDao().deleteUser(user)
                Toast.makeText(requireContext(), "Аккаунт успешно удален", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), LoginUserActivity::class.java)
                startActivity(intent)
            }
            dialogBuilder.dismiss()
        }

        dialogView.findViewById<AppCompatButton>(R.id.btn_consel)?.setOnClickListener {
            dialogBuilder.dismiss()
        }

        dialogBuilder.show()
    }
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}