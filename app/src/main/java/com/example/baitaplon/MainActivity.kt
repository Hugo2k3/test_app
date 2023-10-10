package com.example.baitaplon


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.baitaplon.database.ExpenseDatabase
import com.example.baitaplon.model.Expense


import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    // tao UI
    lateinit var addBtn: Button
    lateinit var viewcurrentMonthBtn:Button
    lateinit var chooseDate:Button
    lateinit var viewYear: Button
    lateinit var selectedDateText: TextView
    //
    lateinit var mAlterDialog : AlertDialog
    lateinit var cal: Calendar
    lateinit var passedMonth :String
    lateinit var sqlExpense: ExpenseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sqlExpense = ExpenseDatabase(this)
        initUI()
        initListener()
        initTodayDate()

    }
    fun initUI(){
        addBtn = findViewById(R.id.addNewExpenseBtn)
        viewcurrentMonthBtn = findViewById(R.id.currentMonthExpenseBtn)
        chooseDate = findViewById(R.id.chooseDateBtn)
        viewYear = findViewById(R.id.viewExpensesYearBtn)
        selectedDateText = findViewById(R.id.selectedDateTxtView)
    }
    fun initListener(){
        addBtn.setOnClickListener(this)
        viewYear.setOnClickListener(this)
        chooseDate.setOnClickListener(this)
        viewcurrentMonthBtn.setOnClickListener(this)
    }
    private fun initTodayDate() {
        cal = Calendar.getInstance()
        val mYear = cal.get(Calendar.YEAR)
        val mMonth = cal.getDisplayName(Calendar.MONTH,Calendar.SHORT,Locale.US)
        val mDay = cal.get(Calendar.DAY_OF_MONTH)

        var strDate: String = mDay.toString() + "-" + mMonth.toString() + "-" + mYear.toString()
        selectedDateText.setText(strDate)
        passedMonth = mMonth

    }
    var mDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, monthOfYear)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val myFormat = "d-MMM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        selectedDateText.text = sdf.format(cal.time)
        passedMonth = cal.getDisplayName(Calendar.MONTH,Calendar.SHORT, Locale.US)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.addNewExpenseBtn->{
                val view = layoutInflater.inflate(R.layout.add_dialog, null)
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Add New Expense")
                builder.setView(view)
                val newExpItemName = view.findViewById(R.id.spinerListItem) as Spinner
                val newExpAmount = view.findViewById(R.id.amountEdText) as EditText
                val newExpNote = view.findViewById(R.id.noteEdText) as EditText
                val addBtn = view.findViewById(R.id.addBtn) as Button
                val cancelBtn = view.findViewById(R.id.cancelBtn) as Button
                mAlterDialog = builder.show()
                cancelBtn.setOnClickListener{
                    mAlterDialog.dismiss()
                }
                addBtn.setOnClickListener{
                    if(newExpItemName.selectedItem.toString() != "Select item"){
                        val newExp = Expense("0", newExpItemName.selectedItem.toString(), newExpAmount.text.toString(), newExpNote.text.toString(), passedMonth.toString())
                        val staus = sqlExpense.insertExpense(newExp)
                        Toast.makeText(this@MainActivity, "Đã lưu thành công chi phí", Toast.LENGTH_SHORT).show()
                        val intent: Intent = Intent(this, ExpenseDateActivity::class.java)
                        intent.putExtra("data", passedMonth)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@MainActivity, "Bạn phải select item!!!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            R.id.viewExpensesYearBtn->{
                val intent: Intent = Intent(this, ExpenseYear::class.java)
                startActivity(intent)
           }
            R.id.chooseDateBtn->{
                val dialog = DatePickerDialog(
                    this,
                    mDateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                )
                dialog.show()
            }
            R.id.currentMonthExpenseBtn->{
                val intent: Intent = Intent(this, ExpenseDateActivity::class.java)
                intent.putExtra("data", passedMonth)
                startActivity(intent)
            }
        }
    }
}