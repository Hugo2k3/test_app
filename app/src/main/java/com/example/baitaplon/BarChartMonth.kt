package com.example.baitaplon

//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baitaplon.database.ExpenseDatabase
import com.example.baitaplon.model.Expense

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
// hiển thị chi tiêu cho 1 tháng cụ thể gần tương tự BarChartDetail
class BarChartMonth : AppCompatActivity() {
    lateinit var sqlExp: ExpenseDatabase
    lateinit var barChart: BarChart
    lateinit var passedMonth: String


    lateinit var barEntriesList: ArrayList<BarEntry>
    override fun onCreate(savedInstanceState: Bundle?) {

        sqlExp = ExpenseDatabase(this)
        val intent = intent
        passedMonth = intent.getStringExtra("dataMonth")!!
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart_month)
        barChart = findViewById(R.id.barchartExpYear)
        setBarChartData()

    }

    private fun setBarChartData() {
        val xvalue = ArrayList<String>()
        val itTienNha = "Tiền nhà"
        val itAnUong = "Ăn uống"
        val itTietKiem = "Tiết kiệm"
        val other = "(Other)"
        xvalue.add(itTienNha)
        xvalue.add(itAnUong)
        xvalue.add(itTietKiem)
        xvalue.add(other)
        val barentries = ArrayList<BarEntry>()
        barentries.add(BarEntry(totalExpItem(passedMonth,itTienNha), 0))
        barentries.add(BarEntry(totalExpItem(passedMonth, itAnUong), 1))
        barentries.add(BarEntry(totalExpItem(passedMonth,itTietKiem), 2))
        barentries.add(BarEntry(totalExpItem(passedMonth,other), 3))
        val bardataset = BarDataSet(barentries, "Tiền nhà -> Tiền tiết kiệm (theo thứ tự item)")
        bardataset.color = resources.getColor(R.color.purple_200 )
        barChart.setBackgroundColor(resources.getColor(R.color.white))
        val data = BarData(xvalue, bardataset)
        barChart.data = data
        barChart.animateXY(2000, 2000)
    }

    private fun totalExpItem(month: String, s: String): Float {
        var listExpItem: ArrayList<Expense> = sqlExp.fillterItem(month, s)
        var Total: Int = 0
        for(i in listExpItem){
            Total += i.ExpenseAmount.toInt()
        }
        return Total.toFloat()
    }
}