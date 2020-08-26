package com.example.app2.Singletons

class MenuSingleton {
    var name: Array<String?> = arrayOfNulls(42)
    var description: Array<String?> = arrayOfNulls(42)
    var price: Array<Int?> = arrayOfNulls(42)

    companion object{
        private var instance = MenuSingleton()
        fun getInstance(): MenuSingleton?{
            if (instance == null) {
                instance =
                    MenuSingleton()
                instance.name = arrayOfNulls(42)
                instance.description = arrayOfNulls(42)
                instance.price = arrayOfNulls(42)
            }
            return instance
        }

        fun getNewObject(): MenuSingleton {
            MenuSingleton.instance = MenuSingleton()
            return MenuSingleton.instance
        }
    }
}