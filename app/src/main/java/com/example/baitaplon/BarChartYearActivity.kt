package com.example.baitaplon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.baitaplon.database.ExpenseDatabase
import com.example.baitaplon.model.Expense
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class BarChartYearActivity : AppCompatActivity() {
    lateinit var sqlExp: ExpenseDatabase
    lateinit var barChartYear: BarChart
    lateinit var detailYearButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart_year)
        sqlExp = ExpenseDatabase(this)
        barChartYear = findViewById(R.id.barchartExpYear)
        val xvalue = ArrayList<String>()
        val January = "Jan"
        val February = "Fed"
        val March = "Mar"
        val April = "Apr"
        val May = "May"
        val June = "Jun"
        val July = "Jul"
        val August = "Aug"
        val September = "Sep"
        val October = "Oct"
        val November = "Nov"
        val December = "Dec"
        xvalue.add(January)
        xvalue.add(February)
        xvalue.add(March)
        xvalue.add(April)
        xvalue.add(May)
        xvalue.add(June)
        xvalue.add(July)
        xvalue.add(August)
        xvalue.add(September)
        xvalue.add(October)
        xvalue.add(November)
        xvalue.add(December)
        val barentries = ArrayList<BarEntry>()
        barentries.add(BarEntry(totalInMonth(sqlExp.fillterMonth(January)), 0))
        barentries.add(BarEntry(totalInMonth(sqlExp.fillterMonth(February)), 1))
        barentries.add(BarEntry(totalInMonth(sqlExp.fillterMonth(March)), 2))
        barentries.add(BarEntry(totalInMonth(sqlExp.fillterMonth(April)), 3))
        barentries.add(BarEntry(totalInMonth(sqlExp.fillterMonth(May)), 4))
        barentries.add(BarEntry(totalInMonth(sqlExp.fillterMonth(June)), 5))
        barentries.add(BarEntry(totalInMonth(sqlExp.fillterMonth(July)), 6))
        barentries.add(BarEntry(totalInMonth(sqlExp.fillterMonth(August)), 7))
        barentries.add(BarEntry(totalInMonth(sqlExp.fillterMonth(September)), 8))
        barentries.add(BarEntry(totalInMonth(sqlExp.fillterMonth(October)), 9))
        barentries.add(BarEntry(totalInMonth(sqlExp.fillterMonth(November)), 10))
        barentries.add(BarEntry(totalInMonth(sqlExp.fillterMonth(December)), 11))
        val bardataset = BarDataSet(barentries, "Biểu đồ các tháng`")
        bardataset.color = resources.getColor(R.color.purple_200 )
        barChartYear.setBackgroundColor(resources.getColor(R.color.white))
        val data = BarData(xvalue, bardataset)
        barChartYear.data = data
        barChartYear.animateXY(2000, 2000)
        detailYearButton = findViewById(R.id.detailItemYearBtn)
        detailYearButton.setOnClickListener{
            val intent: Intent = Intent(this, BarChartDetail::class.java)
            startActivity(intent)
        }


    }
    fun totalInMonth(arraylist: ArrayList<Expense>): Float {
        var total: Int = 0
        for (i in arraylist){
            total += i.ExpenseAmount.toInt()
        }
        return total.toFloat()
    }
}