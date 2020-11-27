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

    @Query("Select * from reddit_image_table WHERE fav = 1 ORDER BY articleid ASC")
    fun getFavImages(): LiveData<List<RedditImage>>

    @Query("UPDATE reddit_image_table SET fav =:fav WHERE articleid = :tid")
    suspend fun setFavToImage(tid: Long, fav:Int): Int

    @Query("Select * from reddit_image_table WHERE keyword = :keyword ORDER BY articleid ASC")
    suspend fun getImagesByKeyword(keyword:String): List<RedditImage>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(redditImage: RedditImage ):Long

    @Query("DELETE FROM reddit_image_table WHERE articleid = :deleteId ")
    suspend fun deleteWord(deleteId : String)

    @Query("DELETE FROM reddit_image_table")
    suspend fun deleteAll()

    @Query("DELETE FROM reddit_image_table WHERE keyword = :keyword AND fav=0")
    suspend fun deleteByKeyword(keyword:String)
}
