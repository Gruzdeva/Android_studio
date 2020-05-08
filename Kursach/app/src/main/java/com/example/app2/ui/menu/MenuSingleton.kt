package com.example.app2.ui.menu

class MenuSingleton {
    var name: Array<String?> = arrayOfNulls(50)
    var description: Array<String?> = arrayOfNulls(50)
    var price: Array<Int?> = arrayOfNulls(50)

    companion object{
        private var instance = MenuSingleton()
        fun getInstance(): MenuSingleton?{
            if (instance == null) {
                instance = MenuSingleton()
                instance.name = arrayOfNulls(50)
                instance.description = arrayOfNulls(50)
                instance.price = arrayOfNulls(50)
            }
            return instance
        }
    }
}