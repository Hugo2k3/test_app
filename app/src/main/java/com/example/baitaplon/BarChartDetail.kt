package com.example.baitaplon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.baitaplon.database.ExpenseDatabase
import com.example.baitaplon.model.Expense
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class BarChartDetail : AppCompatActivity() {
    lateinit var sqlExp: ExpenseDatabase
    lateinit var barChart: BarChart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bar_chart_detail)
        sqlExp = ExpenseDatabase(this)
        barChart = findViewById(R.id.barchartDetail)
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
        barentries.add(BarEntry(totalExpItem(itTienNha), 0))
        barentries.add(BarEntry(totalExpItem( itAnUong), 1))
        barentries.add(BarEntry(totalExpItem(itTietKiem), 2))
        barentries.add(BarEntry(totalExpItem(other), 3))
        val bardataset = BarDataSet(barentries, "Biểu Đồ Các Loại Tiền Trong Năm")
        bardataset.color = resources.getColor(R.color.purple_200 )
        barChart.setBackgroundColor(resources.getColor(R.color.white))
        val data = BarData(xvalue, bardataset)
        barChart.data = data
        barChart.animateXY(2000, 2000)
    }

    private fun totalExpItem(s: String): Float {
        var listExpItem: ArrayList<Expense> = sqlExp.fillterItem(s)
        var Total: Int = 0
        for(i in listExpItem){
            Total += i.ExpenseAmount.toInt()
        }
        return Total.toFloat()
    }
}