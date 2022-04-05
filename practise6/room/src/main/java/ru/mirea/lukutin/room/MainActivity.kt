package ru.mirea.lukutin.room

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db: AppDatabase = App.getInstance().database
        val employeeDao: EmployeeDao = db.employeeDao()
        var employee = Employee()
        employee.id = 1
        employee.name = "John Smith"
        employee.salary = 10000
        employeeDao.insert(employee)
        val employees = employeeDao.getAll()
        employee = employeeDao.getById(1)!!
        employee.salary = 20000
        employeeDao.update(employee)
        Log.d(MainActivity::class.java.simpleName, employee.name + " " + employee.salary)
    }
}