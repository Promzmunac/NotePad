package com.example.notepad2.model.db
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notepad2.model.datamodel.User

@Dao
interface UserDao{
    //create operation
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(user: User): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(user: User)

    //Read operation
    @Query("SELECT * FROM UserModel ORDER BY id ASC")
    fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM UserModel WHERE id=:id")
    fun getUser(id: Long): User

    //update operation
    @Query("UPDATE UserModel SET  userName =:UserName, email=:Email, photo=:Image, password=:Password WHERE id=:id")
    fun updateUserAll(id: Long, UserName: String,Email: String,Image: String,Password: String)

    //Delete operation
    @Delete
    fun deleteUser(user: User)
}
