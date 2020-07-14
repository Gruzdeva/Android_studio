package com.example.app2.Singletons

class UserProfile {
    var id = 0
    var login = String()
    var name = String()
    var password = String()
    var points = 0
    var isPointsDeduct = false

    companion object {
        private  var instance: UserProfile =
            UserProfile()

        fun getInstance(): UserProfile {
            if (instance == null) { //если объект еще не создан
                instance =
                    UserProfile() //создать новый объект
            }
            return instance// вернуть ранее созданный объект
        }

        fun getNewObject(): UserProfile {
                instance =
                    UserProfile()
                return instance
            }
    }
}
