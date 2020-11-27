package com.example.iquiitest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "reddit_image_table")
class RedditImage (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "articleid")
    var id: Long,
    var tittle:String?,
    var author:String?,
    var url:String?,
    var thumbnail:String?,
    var keyword:String?,
    var fav:Boolean?)