package com.example.iquiitest.model

class RedditImage {

    var id:Int ? = 0
    var thumbUrl:String ? = null
    var url:String ? = null
    var tittle:String ? = null
    var author:String ? = null

    constructor(id: Int?, thumbUrl: String?, url: String?, tittle: String?, author: String? ) {
        this.id = id
        this.thumbUrl = thumbUrl
        this.url = url
        this.tittle = tittle
        this.author = author
    }
}