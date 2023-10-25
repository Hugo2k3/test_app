package com.example.baitaplon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.baitaplon.database.ExpenseDatabase
import com.example.baitaplon.model.Expense
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
// ke thua appcombatactivity de tao hoat dong
class BarChartDetail : AppCompatActivity() {
    lateinit var sqlExp: ExpenseDatabase
    lateinit var barChart: BarChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // nạp từ tệp laput
        setContentView(R.layout.activity_bar_chart_detail)
        // ánh xạ
        sqlExp = ExpenseDatabase(this)
        barChart = findViewById(R.id.barchartDetail)
        // khoi tao một chuỗi và add các mục chi tiêu vào
        val xvalue = ArrayList<String>()
        val itTienNha = "Tiền nhà"
        val itAnUong = "Ăn uống"
        val itTietKiem = "Tiết kiệm"
        val other = "(Other)"
        xvalue.add(itTienNha)
        xvalue.add(itAnUong)
        xvalue.add(itTietKiem)
        xvalue.add(other)
        // điền vào danh sách bằng cách gọi hàm totalExpItem
        // sử dụng thuôc tính đổi màu đề đổi màu nền, đổi màu biểu đồ
        val barentries = ArrayList<BarEntry>()
        barentries.add(BarEntry(totalExpItem(itTienNha), 0))
        barentries.add(BarEntry(totalExpItem( itAnUong), 1))
        barentries.add(BarEntry(totalExpItem(itTietKiem), 2))
        barentries.add(BarEntry(totalExpItem(other), 3))
        val bardataset = BarDataSet(barentries, "Biểu Đồ Các Loại Tiền Trong Năm")
        bardataset.color = resources.getColor(R.color.purple_200 )
        barChart.setBackgroundColor(resources.getColor(R.color.white))
        val data = BarData(xvalue, bardataset)
        barChart.data = data
        // hiệu ứng xuất hiện của biều đồ trong vòng 2 giây
        barChart.animateXY(2000, 2000)
    }
    // Tính tổng số tiền đã chi tiêu một cho mục tiêu cụ thể bằng cách truy xuất dữ liệu từ cơ sở dũ liệu thông qua đối tượng 'sqlExp'
    private fun totalExpItem(s: String): Float {
        var listExpItem: ArrayList<Expense> = sqlExp.fillterItem(s)
        var Total: Int = 0
        for(i in listExpItem){
            Total += i.ExpenseAmount.toInt()
        }
        return Total.toFloat()
    }
}