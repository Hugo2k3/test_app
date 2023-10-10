package com.example.baitaplon

import android.content.Intent

import android.os.Bundle

import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baitaplon.database.ExpenseDatabase
import com.example.baitaplon.model.Expense
import com.example.baitaplon.view.ExpensesAdapter


class ExpenseDateActivity : AppCompatActivity() {
    lateinit var sqlExp: ExpenseDatabase
    lateinit var recycler: RecyclerView
    lateinit var expenseAdapter: ExpensesAdapter
    lateinit var passedMonth: String
    lateinit var totalMonth: TextView
    lateinit var viewChartMonthBtn: Button
    lateinit var viewExpenseByMonth: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_date)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sqlExp = ExpenseDatabase(this)

        val intent = intent
        passedMonth = intent.getStringExtra("data")!!
        expenseAdapter = ExpensesAdapter(this, sqlExp.fillterMonth(passedMonth))
        recycler = findViewById(R.id.mRecyclerView)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = expenseAdapter
        totalMonth = findViewById(R.id.totalInMonth)
        totalMonth.text = totalInMonth(sqlExp.fillterMonth((passedMonth))).toString()
        viewChartMonthBtn = findViewById(R.id.viewExpChartMonthBtn)
        viewChartMonthBtn.setOnClickListener{
            val intentNew: Intent = Intent(this@ExpenseDateActivity, BarChartMonth::class.java)
            intentNew.putExtra("dataMonth", passedMonth)
            startActivity(intentNew)
        }
        viewExpenseByMonth = findViewById(R.id.viewExpYearBtn)
        viewExpenseByMonth.setOnClickListener{
            val intentNew: Intent = Intent(this@ExpenseDateActivity, ExpenseYear::class.java)
            startActivity(intentNew)
        }
    }
    fun totalInMonth(arraylist: ArrayList<Expense>): Int {
        var total: Int = 0
        for (i in arraylist){
            total += i.ExpenseAmount.toInt()
        }
        return total
    }
}