package com.example.baitaplon.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.baitaplon.model.Expense


class ExpenseDatabase(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_NAME = "DBexpenses" +
                ""
        private val DB_VERSION = 1;
        private val TABLE_NAME = "expense"
        private val ID = "ExpenseID"
        private val EXPENSE_NAME_ITEM = "ExpenseNameItem"
        private val EXPENSE_AMOUNT = "ExpenseAmount"
        private val EXPENSE_NOTE = "ExpenseNote"
        private val EXPENSE_DATE = "ExpenseDate"
        //`ExpenseDatabase` là một lớp được kế thừa từ `SQLiteOpenHelper`, được sử dụng để tạo và quản lý cơ sở dữ liệu SQLite.
        //Các hằng số định nghĩa thông tin cơ sở dữ liệu, bao gồm tên cơ sở dữ liệu (`DB_NAME`), phiên bản cơ sở dữ liệu (`DB_VERSION`),
        // tên bảng (`TABLE_NAME`), và các cột trong bảng (`ID`, `EXPENSE_NAME_ITEM`, `EXPENSE_AMOUNT`, `EXPENSE_NOTE`, `EXPENSE_DATE`).

    }

    override fun onCreate(db: SQLiteDatabase?) {
        //`onCreate(db: SQLiteDatabase?)`: Phương thức này được gọi khi cơ sở dữ liệu SQLite được tạo lần đầu tiên.
        // Nó tạo bảng `expense` với các cột đã định nghĩa trong hằng số và thiết lập cấu trúc cơ sở dữ liệu.
        val create_table = ("CREATE TABLE " + TABLE_NAME + "("
                + ID + " Integer primary key , "
                + EXPENSE_NAME_ITEM + " TEXT, "
                + EXPENSE_AMOUNT + " TEXT, "
                + EXPENSE_NOTE + " TEXT, "
                + EXPENSE_DATE + " TEXT" + ")")
        if (db != null) {
            db.execSQL(create_table)
        }
        // Cau lenh tao bang

    }

    fun insertExpense(exp: Expense): Long{
        //. `insertExpense(exp: Expense)`: Phương thức này thêm một khoản chi tiêu (được truyền vào dưới dạng đối tượng `Expense`)
        // vào cơ sở dữ liệu và trả về ID của khoản chi tiêu được thêm.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(EXPENSE_NAME_ITEM, exp.ExpenseItem)
        values.put(EXPENSE_AMOUNT, exp.ExpenseAmount)
        values.put(EXPENSE_NOTE, exp.ExpenseNote)
        values.put(EXPENSE_DATE, exp.ExpenseDate)
        val _success = db.insert(TABLE_NAME,null, values)
        db.close()
        return _success
    }
    @SuppressLint("Range")
    fun getAllData():ArrayList<Expense>{
        //`getAllData()`: Phương thức này truy vấn và trả về tất cả các khoản chi tiêu
        // trong cơ sở dữ liệu dưới dạng một danh sách (`ArrayList`) các đối tượng `Expense`.
        val listExp:ArrayList<Expense> = ArrayList()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor?
        try{
            cursor = db.rawQuery(selectALLQuery, null)
        }catch(e: java.lang.Exception){
            e.printStackTrace()
            db.execSQL(selectALLQuery)
            return ArrayList()
        }
        var idExp: Int
        var nameitemExp: String
        var amountExp:String
        var noteExp:String
        var dateExp :String
        if(cursor.moveToFirst()){
            do{
                idExp = cursor.getInt(cursor.getColumnIndex(ID))
                nameitemExp = cursor.getString(cursor.getColumnIndex(EXPENSE_NAME_ITEM))
                amountExp = cursor.getString(cursor.getColumnIndex(EXPENSE_AMOUNT))
                noteExp = cursor.getString(cursor.getColumnIndex(EXPENSE_NOTE))
                dateExp = cursor.getString(cursor.getColumnIndex(EXPENSE_DATE))
                val expNew = Expense(idExp.toString(), nameitemExp, amountExp, noteExp, dateExp)
                listExp.add(expNew)

            }while (cursor.moveToNext())

        }
        return  listExp
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //`onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)`: Phương thức này được gọi khi cơ sở dữ liệu cần nâng cấp (ví dụ: khi phiên bản cơ sở dữ liệu thay đổi).
        // Nó xóa bảng cơ sở dữ liệu hiện có và sau đó gọi lại `onCreate` để tạo lại cấu trúc cơ sở dữ liệu mới.
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    @SuppressLint("Range")
    fun fillterMonth(s: String) : ArrayList<Expense>{

        //. `filterMonth(s: String)`: Phương thức này truy vấn và trả về danh sách các khoản chi tiêu trong một tháng cụ thể
        // (được truyền vào dưới dạng chuỗi `s`) dưới dạng `ArrayList<Expense>`.
        val listExp:ArrayList<Expense> = ArrayList()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME WHERE $EXPENSE_DATE LIKE '%$s%' "
        val cursor: Cursor?
        try{
            cursor = db.rawQuery(selectALLQuery, null)
        }catch(e: java.lang.Exception){
            e.printStackTrace()
            db.execSQL(selectALLQuery)
            return ArrayList()
        }
        var idExp: Int
        var nameitemExp: String
        var amountExp:String
        var noteExp:String
        var dateExp :String
        if(cursor.moveToFirst()){
            do{
                idExp = cursor.getInt(cursor.getColumnIndex(ID))
                nameitemExp = cursor.getString(cursor.getColumnIndex(EXPENSE_NAME_ITEM))
                amountExp = cursor.getString(cursor.getColumnIndex(EXPENSE_AMOUNT))
                noteExp = cursor.getString(cursor.getColumnIndex(EXPENSE_NOTE))
                dateExp = cursor.getString(cursor.getColumnIndex(EXPENSE_DATE))
                val expNew = Expense(idExp.toString(), nameitemExp, amountExp, noteExp, dateExp)
                listExp.add(expNew)

            }while (cursor.moveToNext())

        }
        return  listExp
    }
    @SuppressLint("Range")
    fun fillterItem(nameMonth: String, nameItem:String):ArrayList<Expense>{
        //. `filterItem(nameMonth: String, nameItem: String)`: Phương thức này truy vấn và trả về danh sách các khoản chi tiêu có tên mặt hàng
        // (`nameItem`) trong một tháng cụ thể (`nameMonth`) dưới dạng `ArrayList<Expense>`.
        val listExp:ArrayList<Expense> = ArrayList()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME WHERE $EXPENSE_NAME_ITEM LIKE '%$nameItem%' AND $EXPENSE_DATE LIKE '$nameMonth'  "
        val cursor: Cursor?
        try{
            cursor = db.rawQuery(selectALLQuery, null)
        }catch(e: java.lang.Exception){
            e.printStackTrace()
            db.execSQL(selectALLQuery)
            return ArrayList()
        }
        var idExp: Int
        var nameitemExp: String
        var amountExp:String
        var noteExp:String
        var dateExp :String
        if(cursor.moveToFirst()){
            do{
                idExp = cursor.getInt(cursor.getColumnIndex(ID))
                nameitemExp = cursor.getString(cursor.getColumnIndex(EXPENSE_NAME_ITEM))
                amountExp = cursor.getString(cursor.getColumnIndex(EXPENSE_AMOUNT))
                noteExp = cursor.getString(cursor.getColumnIndex(EXPENSE_NOTE))
                dateExp = cursor.getString(cursor.getColumnIndex(EXPENSE_DATE))
                val expNew = Expense(idExp.toString(), nameitemExp, amountExp, noteExp, dateExp)
                listExp.add(expNew)

            }while (cursor.moveToNext())

        }
        return  listExp
    }

    fun deleteExpense(_id: String): Boolean {

        //deleteExpense(_id: String)`: Phương thức này xóa một khoản chi tiêu dựa trên ID được cung cấp và trả về `true` nếu xóa thành công.
        //
        //Lớp `Expense` chứa thông tin về một khoản chi tiêu bao gồm `ExpenseItem`, `ExpenseAmount`, `ExpenseNote`, và `ExpenseDate`.
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id)).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }
    @SuppressLint("Range")
    fun fillterItem(nameItem:String):ArrayList<Expense>{
        val listExp:ArrayList<Expense> = ArrayList()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME WHERE $EXPENSE_NAME_ITEM LIKE '%$nameItem%'  "
        val cursor: Cursor?
        try{
            cursor = db.rawQuery(selectALLQuery, null)
        }catch(e: java.lang.Exception){
            e.printStackTrace()
            db.execSQL(selectALLQuery)
            return ArrayList()
        }
        var idExp: Int
        var nameitemExp: String
        var amountExp:String
        var noteExp:String
        var dateExp :String
        if(cursor.moveToFirst()){
            do{
                idExp = cursor.getInt(cursor.getColumnIndex(ID))
                nameitemExp = cursor.getString(cursor.getColumnIndex(EXPENSE_NAME_ITEM))
                amountExp = cursor.getString(cursor.getColumnIndex(EXPENSE_AMOUNT))
                noteExp = cursor.getString(cursor.getColumnIndex(EXPENSE_NOTE))
                dateExp = cursor.getString(cursor.getColumnIndex(EXPENSE_DATE))
                val expNew = Expense(idExp.toString(), nameitemExp, amountExp, noteExp, dateExp)
                listExp.add(expNew)


            }while (cursor.moveToNext())

        }
        return  listExp

    }


}