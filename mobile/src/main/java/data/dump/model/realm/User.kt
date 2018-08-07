package data.dump.model.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class User : RealmObject() {

    lateinit var token: String

}