package data.dump.model.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User(
        @PrimaryKey
        var _id:Int? = null,
        var token: String? = null
) : RealmObject() {

        fun User() {}

        fun User (token: String) {
                this._id = 1
                this.token = token
        }

}