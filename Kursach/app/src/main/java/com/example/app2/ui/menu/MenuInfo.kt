package com.example.app2.ui.menu

class MenuInfo {
    var id = 0
    var group_id = 0
    var name = String()
    var description = String()
    var price = 0

    companion object {
        private  var instance: MenuInfo =
            MenuInfo()

        fun getInstance(): MenuInfo {
            if (instance == null) { //если объект еще не создан
                instance =
                    MenuInfo() //создать новый объект
            }
            return instance// вернуть ранее созданный объект
        }

        fun getNewObject(): MenuInfo {
            instance =
                MenuInfo()
            return instance
        }
    }
}