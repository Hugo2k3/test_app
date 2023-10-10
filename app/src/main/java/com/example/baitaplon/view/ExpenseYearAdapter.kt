package com.example.baitaplon.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.baitaplon.R

import com.example.baitaplon.model.Expense
import com.example.baitaplon.model.ExpenseMonth


class ExpenseYearAdapter(val c:Context, val ExpenseYearList: ArrayList<ExpenseMonth>): RecyclerView.Adapter<ExpenseYearAdapter.ExpensesYearViewHolder>() {
    //lateinit var sqlExp: ExpenseDatabase
    inner class ExpensesYearViewHolder(val v:View) : RecyclerView.ViewHolder(v){
        val nameMonthItem:TextView
        val totalMonth: TextView
        init{
            nameMonthItem = v.findViewById<TextView>(R.id.txtnameMonth)
            totalMonth = v.findViewById<TextView>(R.id.txtAmountMonth)
        }

    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ExpensesYearViewHolder {
        val inflater = LayoutInflater.from(p0.context)
        val v = inflater.inflate(R.layout.card_month_expense, p0, false)
        return ExpensesYearViewHolder(v)
    }

    override fun onBindViewHolder(holder: ExpensesYearViewHolder,position: Int) {
        var newItem: ExpenseMonth = ExpenseYearList[position]
        holder.nameMonthItem.text = newItem.nameMonth
        holder.totalMonth.text = newItem.totalAmountMonth + " $"

    }

    override fun getItemCount(): Int {
        return ExpenseYearList.size
    }

}