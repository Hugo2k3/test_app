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

// hiển thị danh sách chi tiêu trong một tháng cụ thể và tính tổng số tiền
class ExpenseDateActivity : AppCompatActivity() {
    lateinit var sqlExp: ExpenseDatabase
    lateinit var recycler: RecyclerView
    lateinit var expenseAdapter: ExpensesAdapter
    lateinit var passedMonth: String
    lateinit var totalMonth: TextView
    lateinit var viewChartMonthBtn: Button
    lateinit var viewExpenseByMonth: Button
    // ghi đè nạp dữ liệu từ tệp activity_expense_date
    override fun onCreate(savedInstanceState: Bundle?) {
        //Dữ liệu danh sách chi tiêu trong tháng cụ thể được truy xuất từ cơ sở dữ liệu thông qua đối tượng sqlExp,
        // và sau đó, danh sách này được đưa vào expenseAdapter để hiển thị trong recycler.
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
        //Tổng số tiền đã chi tiêu trong tháng được tính toán bằng cách gọi phương thức totalInMonth() và sau đó hiển thị lên totalMonth.
        totalMonth.text = totalInMonth(sqlExp.fillterMonth((passedMonth))).toString()
        //viewChartMonthBtn được khởi tạo và thiết lập một trình nghe sự kiện (listener) để khi được nhấn, nó mở một hoạt động mới (BarChartMonth)
        // và truyền tháng cụ thể thông qua intent.
        viewChartMonthBtn = findViewById(R.id.viewExpChartMonthBtn)
        viewChartMonthBtn.setOnClickListener{
            val intentNew: Intent = Intent(this@ExpenseDateActivity, BarChartMonth::class.java)
            intentNew.putExtra("dataMonth", passedMonth)
            startActivity(intentNew)
        }
        //viewExpenseByMonth cũng được khởi tạo và thiết lập một trình nghe sự kiện (listener) để khi được nhấn, nó mở một hoạt động mới (ExpenseYear).
        viewExpenseByMonth = findViewById(R.id.viewExpYearBtn)
        viewExpenseByMonth.setOnClickListener{
            val intentNew: Intent = Intent(this@ExpenseDateActivity, ExpenseYear::class.java)
            startActivity(intentNew)
        }
    }
    // tổng số tiền đã chi tiêu trong tháng
    fun totalInMonth(arraylist: ArrayList<Expense>): Int {
        var total: Int = 0
        for (i in arraylist){
            total += i.ExpenseAmount.toInt()
        }
        return total
    }
}