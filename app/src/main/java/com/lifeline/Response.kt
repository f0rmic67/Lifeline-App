package com.lifeline

data class Response(val message: String?, val responseCode:Int){
    companion object{
        val SUCCESS = 200
        val UNAUTHORIZED = 401
        val FORBIDDEN = 403
        val CONFLICT = 409
    }
}
