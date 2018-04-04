package com.kotlin.lib

class User : BaseInfo {

    var id: Int? = 0
    var name: String? = null
    var age: Int? = 0
    var address: String? = null

    constructor(code: Int = 0, msg: String = "user", id: Int?, name: String?, age: Int?, address: String?) : super(code, msg) {
        this.id = id
        this.name = name
        this.age = age
        this.address = address
    }

    constructor(id: Int?) : super(code = 0, msg = "user") {
        this.id = id
    }
}