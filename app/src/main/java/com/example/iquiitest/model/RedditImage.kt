package com.example.iquiitest.model

class RedditImage {

    var id:Int ? = 0
    var url:String ? = null
    var tittle:String ? = null
    var author:String ? = null

    constructor(id: Int?, url: String?, tittle: String?, author: String? ) {
        this.id = id
        this.url = url
        this.tittle = tittle
        this.author = author
    }
}