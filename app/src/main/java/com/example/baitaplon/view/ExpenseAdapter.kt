package com.example.baitaplon.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.baitaplon.ExpenseDateActivity
import com.example.baitaplon.R
import com.example.baitaplon.database.ExpenseDatabase

import com.example.baitaplon.model.Expense


class ExpensesAdapter(val c:Context, val ExpenseList: ArrayList<Expense>): RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder>() {
    lateinit var sqlExp: ExpenseDatabase
    inner class ExpensesViewHolder(val v:View) : RecyclerView.ViewHolder(v){
        val nameItem:TextView
        val dayAmount:TextView
        val dayNote: TextView
        val currentDate: TextView
        var delete:ImageView

        init{
            sqlExp = ExpenseDatabase(v.context)
            nameItem = v.findViewById<TextView>(R.id.txtNameItem)
            dayAmount = v.findViewById<TextView>(R.id.txtAmount)
            dayNote = v.findViewById<TextView>(R.id.txtNote)
            currentDate = v.findViewById<TextView>(R.id.txtDate)
            delete = v.findViewById<ImageView>(R.id.mDelete)
            delete.setOnClickListener{
                popupMenus(it)
            }
        }
        private fun popupMenus(v:View) {

            val position = ExpenseList[adapterPosition]
            val popupMenus = PopupMenu(c,v)
            popupMenus.inflate(R.menu.menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editExp->{
                        val v = LayoutInflater.from(c).inflate(R.layout.add_dialog2,null)
                        val newExpItemName = v.findViewById(R.id.spinerListItem2) as Spinner
                        val newExpAmount = v.findViewById(R.id.amountEdText2) as EditText
                        val newExpNote = v.findViewById(R.id.noteEdText2) as EditText
                        AlertDialog.Builder(c)
                            .setView(v)
                            .setPositiveButton("Ok"){
                                    dialog,_->
                                position.ExpenseItem = newExpItemName.selectedItem.toString()
                                position.ExpenseAmount = newExpAmount.text.toString()
                                position.ExpenseNote = newExpNote.text.toString()
                                sqlExp.insertExpense(Expense("",newExpItemName.selectedItem.toString(), newExpAmount.text.toString(), newExpNote.text.toString(), position.ExpenseDate))
                                sqlExp.deleteExpense(position.ExpenseID)
                                notifyDataSetChanged()
                                Toast.makeText(c,"User Information is Edited",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()

                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()

                            }
                            .create()
                            .show()

                        true
                    }
                    R.id.delete->{
                        /**set delete*/
                        AlertDialog.Builder(c)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                sqlExp.deleteExpense(ExpenseList[adapterPosition].ExpenseID)
                                ExpenseList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(c,"Deleted this Information",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else-> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }
    }



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ExpensesViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val v = inflater.inflate(R.layout.card_expense, p0, false)
        return ExpensesViewHolder(v)
    }

    override fun onBindViewHolder(holder: ExpensesViewHolder,position: Int) {
        val newList = ExpenseList[position]
        holder.nameItem.text = "Mô tả loại tiền : " + newList.ExpenseItem
        holder.dayAmount.text = "Chi phí: " + newList.ExpenseAmount.toString()
        holder.dayNote.text ="Chú ý: " + newList.ExpenseNote
        holder.currentDate.text = "Ngày tạo:" + newList.ExpenseDate.toString()
    }

    override fun getItemCount(): Int {
        return ExpenseList.size
    }

}