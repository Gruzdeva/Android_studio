package com.example.app2

class UserProfile {
    var id: Int = 0
    var login: String = ""
    var name: String = ""
    var password: String = ""
    var points: Int = 0


    companion object{
        private var instance: UserProfile = UserProfile()

        fun getInstance(): UserProfile {
            if(instance == null){
                instance = UserProfile()
            }
            return instance
        }

        fun getNewObject(): UserProfile{
            instance = UserProfile()
            return instance
        }
    }


}