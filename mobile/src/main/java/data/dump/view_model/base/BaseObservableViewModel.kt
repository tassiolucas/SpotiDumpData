package data.dump.view_model.base

import android.content.Context
import android.databinding.BaseObservable

open class BaseObservableViewModel : BaseObservable() {

    protected lateinit var context: Context

}