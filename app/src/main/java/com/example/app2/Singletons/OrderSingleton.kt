package com.example.app2.Singletons

class OrderSingleton {
    var id: Array<String?> = arrayOfNulls(200)
    var dates: Array<String?> = arrayOfNulls(200)
    var costs: Array<Int?> = arrayOfNulls(200)

    companion object{
        private var instance = OrderSingleton()

        fun getInstance(): OrderSingleton?{
            if(instance == null){
                instance =
                    OrderSingleton()
                instance.id = arrayOfNulls(200)
                instance.dates = arrayOfNulls(200)
                instance.costs = arrayOfNulls(200)
            }
            return instance
        }
    }
}