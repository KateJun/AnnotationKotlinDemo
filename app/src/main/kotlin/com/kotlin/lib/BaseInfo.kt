package com.kotlin.lib

import java.io.Serializable

open class BaseInfo : Serializable {

    var code: Int? = 0
    var msg: String? = null


    constructor(code: Int, msg: String = "") {
        this.code = code
        this.msg = msg
    }

    override fun toString(): String {
        return "default:code=$code,msg=$msg"
    }

}