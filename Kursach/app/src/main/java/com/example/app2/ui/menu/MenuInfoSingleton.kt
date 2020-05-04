package com.example.app2.ui.menu

class MenuInfoSingleton {
    var id: Array<Int?> = arrayOfNulls(100)
    var group_id: Array<Int?> = arrayOfNulls(100)
    var name: Array<String?> = arrayOfNulls(100)
    var description: Array<String?> = arrayOfNulls(100)
    var price: Array<Int?> = arrayOfNulls(100)

    companion object{
        private var instance = MenuInfoSingleton()

        fun getInstance(): MenuInfoSingleton {
            if(instance == null){
                instance =
                    MenuInfoSingleton()
//                instance.id = arrayOfNulls(100)
//                instance.group_id = arrayOfNulls(100)
//                instance.name = arrayOfNulls(100)
//                instance.description = arrayOfNulls(100)
//                instance.price = arrayOfNulls(100)
            }
            return instance
        }
    }
}