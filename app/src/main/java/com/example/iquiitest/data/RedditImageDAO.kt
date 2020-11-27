package com.example.iquiitest.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RedditImageDAO {

    @Query("Select * from reddit_image_table ORDER BY articleid ASC")
    fun getImages(): LiveData<List<RedditImage>>

    @Query("Select * from reddit_image_table WHERE keyword = :keyword ORDER BY articleid ASC")
    suspend fun getImagesByKeyword(keyword:String): List<RedditImage>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(redditImage: RedditImage )

    @Query("DELETE FROM reddit_image_table WHERE articleid = :deleteId")
    suspend fun deleteWord(deleteId : String)

    @Query("DELETE FROM reddit_image_table")
    suspend fun deleteAll()

    @Query("DELETE FROM reddit_image_table WHERE keyword = :keyword")
    suspend fun deleteByKeyword(keyword:String)
}
