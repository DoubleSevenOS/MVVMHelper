package other.src.app_package

import other.ArmsPluginTemplateProviderImpl
import other.armsAnnotation
import other.commonAnnotation

fun baseModel(isKt: Boolean, provider: ArmsPluginTemplateProviderImpl) = if (isKt) baseModelKt(provider) else baseModelJava(provider)

private fun baseModelKt(provider: ArmsPluginTemplateProviderImpl) = """
package ${provider.modelPackageName.value}
import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel
${
    if (provider.needActivity.value && provider.needFragment.value)
        "import com.jess.arms.di.scope.ActivityScope"
    else if (provider.needActivity.value)
        "import com.jess.arms.di.scope.ActivityScope"
    else if (provider.needFragment.value)
        "import com.jess.arms.di.scope.FragmentScope"
    else ""
}
class ${provider.pageName.value}Model
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ${provider.pageName.value}Contract.Model{
    @Inject
    lateinit var mGson:Gson
    @Inject
    lateinit var mApplication: Application

    override fun onDestroy() {
          super.onDestroy()
    }
}   
"""


fun baseModelJava(provider: ArmsPluginTemplateProviderImpl) = """
package ${provider.modelPackageName.value};
${commonAnnotation(provider)}
public class ${provider.pageName.value}Model {

}   
"""