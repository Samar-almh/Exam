package database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.samar.exam.Task
import java.util.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE id=(:id)")
    fun getTask(id: UUID): LiveData<Task?>


    @Query("SELECT * FROM task WHERE move=(:cate)")
    fun getTodo(cate: String): LiveData<List<Task>>

    @Query("SELECT * FROM task WHERE move=(:cate)")
   fun getInpro(cate: String): LiveData<List<Task>>


    @Query("SELECT * FROM task WHERE move=(:cate)")
    fun getDone(cate: String): LiveData<List<Task>>


    @Update
    fun moveTask(task: Task){

    }

    @Insert
    fun addTask(task: Task){

    }

    @Delete
    fun deleteTask(task : Task){

    }


}



