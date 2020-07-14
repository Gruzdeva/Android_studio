package com.example.app2.Singletons

class MenuSingleton {
    var name: Array<String?> = arrayOfNulls(20)
    var description: Array<String?> = arrayOfNulls(20)
    var price: Array<Int?> = arrayOfNulls(20)

    companion object{
        private var instance = MenuSingleton()
        fun getInstance(): MenuSingleton?{
            if (instance == null) {
                instance =
                    MenuSingleton()
                instance.name = arrayOfNulls(20)
                instance.description = arrayOfNulls(20)
                instance.price = arrayOfNulls(20)
            }
            return instance
        }

        fun getNewObject(): MenuSingleton {
            MenuSingleton.instance = MenuSingleton()
            return MenuSingleton.instance
        }
    }
}