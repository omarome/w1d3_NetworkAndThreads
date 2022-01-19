package fi.omaralm.w1d3_networkandthreads.model

class UserRepository constructor(
    private val userService: UserService
) {
    suspend fun getUserById(id: String): Int {
        return userService.getUser(id)
    }
}

class UserService {
    fun getUser(id: String): Int {
    return 1
    }


    class User (id : Int,  name: String){
        // private val _name: String = name
        private var _age: Int = id
            set(value){
                println("set is invoked for name ")
                field= value+ 1
            }
        private var name : String = name
            set(value){
                println("set is invoked for name ")
                field= "$value:"
            }
    }
}
