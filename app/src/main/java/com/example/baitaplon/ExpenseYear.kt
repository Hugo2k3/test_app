package com.example.baitaplon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baitaplon.database.ExpenseDatabase
import com.example.baitaplon.model.Expense
import com.example.baitaplon.model.ExpenseMonth
import com.example.baitaplon.view.ExpenseYearAdapter

// Hoạt động này được sử dụng để hiển thị tổng số tiền đã chi tiêu cho mỗi tháng trong năm và tổng cộng cho cả năm.
class ExpenseYear : AppCompatActivity() {
     //được sử dụng để hiển thị danh sách các tháng và tổng số tiền đã chi tiêu trong mỗi tháng.
    lateinit var expYearRcler: RecyclerView
    //  hiển thị tổng số tiền
    lateinit var totalYear: TextView
    // là 1 button được sử dụng để chuyển đến 1 hoạt động khác
    lateinit var barcharYearBtn: Button
    // mỗi đối tượng là 1 tháng và là tổng chi tiêu trong tháng đó
    lateinit var listMonth: ArrayList<ExpenseMonth>
    // biến truy xuất dữ liệu
    lateinit var sqlExp: ExpenseDatabase
    //được sử dụng để hiển thị danh sách các tháng và tổng số tiền đã chi tiêu trong RecyclerView.
    lateinit var expYearAdapter: ExpenseYearAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        // nạp từ tệp activity_expense_year
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_year)

        listMonth = ArrayList<ExpenseMonth>()

        expYearRcler = findViewById(R.id.viewYearRecycler)

        totalYear = findViewById(R.id.totalYearExp)
        barcharYearBtn = findViewById(R.id.viewBarChartYearBtn)
        sqlExp = ExpenseDatabase(this)
        var obj1: ExpenseMonth = ExpenseMonth()
        var obj2: ExpenseMonth = ExpenseMonth()
        var obj3: ExpenseMonth = ExpenseMonth()
        var obj4: ExpenseMonth = ExpenseMonth()
        var obj5: ExpenseMonth = ExpenseMonth()
        var obj6: ExpenseMonth = ExpenseMonth()
        var obj7: ExpenseMonth = ExpenseMonth()
        var obj8: ExpenseMonth = ExpenseMonth()
        var obj9: ExpenseMonth = ExpenseMonth()
        var obj10: ExpenseMonth = ExpenseMonth()
        var obj11: ExpenseMonth = ExpenseMonth()
        var obj12: ExpenseMonth = ExpenseMonth()


        obj1.nameMonth = "Jan"
        obj1.totalAmountMonth = expMonth(obj1.nameMonth)
        listMonth.add(obj1)
        obj2.nameMonth = "Feb"
        obj2.totalAmountMonth = expMonth(obj2.nameMonth)
        listMonth.add(obj2)
        obj3.nameMonth = "Mar"
        obj3.totalAmountMonth = expMonth(obj3.nameMonth)
        listMonth.add(obj3)
        obj4.nameMonth = "Apr"
        obj4.totalAmountMonth = expMonth(obj4.nameMonth)
        listMonth.add(obj4)
        obj5.nameMonth = "May"
        obj5.totalAmountMonth = expMonth(obj5.nameMonth)
        listMonth.add(obj5)
        obj6.nameMonth = "Jun"
        obj6.totalAmountMonth = expMonth(obj6.nameMonth)
        listMonth.add(obj6)
        obj7.nameMonth = "Jul"
        obj7.totalAmountMonth = expMonth(obj7.nameMonth)
        listMonth.add(obj7)
        obj8.nameMonth = "Aug"
        obj8.totalAmountMonth = expMonth(obj8.nameMonth)
        listMonth.add(obj8)
        obj9.nameMonth = "Sep"
        obj9.totalAmountMonth = expMonth(obj9.nameMonth)
        listMonth.add(obj9)
        obj10.nameMonth = "Oct"
        obj10.totalAmountMonth = expMonth(obj10.nameMonth)
        listMonth.add(obj10)
        obj11.nameMonth = "Nov"
        obj11.totalAmountMonth = expMonth(obj11.nameMonth)
        listMonth.add(obj11)
        obj12.nameMonth = "Dec"
        obj12.totalAmountMonth = expMonth(obj12.nameMonth)
        listMonth.add(obj12)
        //Danh sách các tháng và tổng số tiền đã chi tiêu trong mỗi tháng được tạo và gán vào danh sách listMonth thông qua các đối tượng ExpenseMonth.
        //được sử dụng để hiển thị danh sách các tháng và tổng số tiền đã chi tiêu trong RecyclerView.
        expYearAdapter = ExpenseYearAdapter(this, listMonth)
        expYearRcler.layoutManager = LinearLayoutManager(this)
        expYearRcler.adapter = expYearAdapter
        // hiển thị tổng tiền dưới dạng VNĐ
        totalYear.text = expYear(listMonth) + "Đ"

        //thiết lập một trình nghe sự kiện (listener) để khi được nhấn, nó mở một hoạt động mới (BarChartYearActivity) để hiển thị biểu đồ cột.
        barcharYearBtn.setOnClickListener{
            val intent: Intent = Intent(this, BarChartYearActivity::class.java)
            startActivity(intent)
        }
    }
    // tính tổng số tiền đã chi tiêu trong 1 tháng cụ thể bằng cách truy xuất dữ liệu
    fun expMonth(nameMonth: String): String {
        var total: Int = 0
        var array: ArrayList<Expense> = sqlExp.fillterMonth(nameMonth)
        for ( i in array){
            total += i.ExpenseAmount.toInt()
        }
        return total.toString()
    }
    // tính tổng số tiển của cả năm bằng cách duyệt qua từng tháng
    fun expYear(listExpMonth: ArrayList<ExpenseMonth>): String{
        var total: Int = 0
        for(i in listExpMonth){
            total += i.totalAmountMonth.toInt()
        }
        return total.toString()
    }
}