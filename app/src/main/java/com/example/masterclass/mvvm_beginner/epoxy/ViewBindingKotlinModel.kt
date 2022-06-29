package com.example.masterclass.mvvm_beginner.epoxy

import android.view.View
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.airbnb.epoxy.EpoxyModel
import com.example.masterclass.R
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.util.concurrent.ConcurrentHashMap

/**
 * A pattern for using epoxy models with Kotlin with no annotations or code generation.
 *
 * See [com.airbnb.epoxy.kotlinsample.models.ItemViewBindingDataClass] for a usage example.
 *
 * If You use Proguard or R8, be sure to keep the bind method available with the following configuration:
 *
 * -keepclassmembers class * extends androidx.viewbinding.ViewBinding {
 *    public static *** bind(android.view.View);
 * }
 */
abstract class ViewBindingKotlinModel<T : ViewBinding>(
    @LayoutRes private val layoutRes : Int
) : EpoxyModel<View>() {
    // Using reflection to get the statistic binding method
    // Lazy so it's computed only once by instance, when the 1st ViewHolder is actually created.
    private val bindingMethod by lazy {
        getBindMethodFrom(this::class.java)
    }
    abstract fun T.bind()

    override fun bind(view: View) {
        var binding = view.getTag(R.id.epoxy_viewbinding) as? T
        if(binding == null) {
            binding = bindingMethod.invoke(null, view) as T
            view.setTag(R.id.epoxy_viewbinding, binding)
        }
        binding.bind()
    }

    override fun getDefaultLayout() = layoutRes

}

// Static cache of a method pointer for each type of item used.
private val sBindingMethodByClass = ConcurrentHashMap<Class<*>, Method>()

private fun getBindMethodFrom(javaClass: Class<*>) : Method =
    sBindingMethodByClass.getOrPut(javaClass) {
        val actualClassTypeOfThis = getSuperClassParameterizedType(javaClass)
        val viewBindingClass = actualClassTypeOfThis.actualTypeArguments[0] as Class<ViewBinding>
        viewBindingClass.getDeclaredMethod("bind", View::class.java)
            ?: error("The binder class ${javaClass.canonicalName} should have a method bind(View)")
    }

private fun getSuperClassParameterizedType(klass : Class<*>) : ParameterizedType {
    val genericSuperClass = klass.genericSuperclass
    return (genericSuperClass as? ParameterizedType)
        ?: getSuperClassParameterizedType(genericSuperClass as Class<*>)
}