package data.dump.model.DAO

import data.dump.model.DAO.base.BaseDAO
import data.dump.model.realm.User
import io.realm.Realm
import io.realm.kotlin.where

class UserDAO : BaseDAO() {

    companion object {

        private const val UNIQUE_USER_ID = 1

        private lateinit var token: String
        private var saved: Boolean = false

        fun saveToken(token: String): Boolean {
            val realm: Realm = Realm.getDefaultInstance()

            realm.executeTransaction {
                var newUser: User = User(UNIQUE_USER_ID, token)
                it.copyToRealmOrUpdate(newUser)
                saved = true
            }

            return saved
        }

        fun saveUser(user: User): Boolean {
            val realm: Realm = Realm.getDefaultInstance()
            saved = false

            realm.executeTransaction {
                it.copyToRealmOrUpdate(user)
                saved = true
            }
            return saved
        }

        fun isLogged(): Boolean {
            val realm: Realm = Realm.getDefaultInstance()
            var isLogged: Boolean = false

            realm.executeTransaction {
                var user: User? = it.where<User>().findFirst()

                if (user != null) {
                    isLogged = user.token != null && !user.token.equals("")
                } else {
                    isLogged = false
                }
            }
            return isLogged
        }

    }
}
