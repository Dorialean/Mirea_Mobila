package ru.mirea.lukutin.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Employee::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao?
}