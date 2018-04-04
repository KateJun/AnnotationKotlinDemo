package com.kotlin.lib

class ComonResponse<T> : BaseInfo {

    var data: T? = null


    constructor(code: Int = 0, msg: String = "", data: T?) : super(code, msg) {
        this.data = data
    }

    constructor(data: T?) : super(code = 0, msg = "") {
        this.data = data
    }

    override fun toString(): String {
        return super.toString() +",data="+ data.toString()
    }

}