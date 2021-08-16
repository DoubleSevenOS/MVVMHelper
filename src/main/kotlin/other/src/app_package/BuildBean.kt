package other.src.app_package

import other.ArmsPluginTemplateProviderImpl
import other.armsAnnotation
import other.commonAnnotation

fun baseBean(isKt: Boolean, provider: ArmsPluginTemplateProviderImpl) = if (isKt) baseModelKt(provider) else buildBeanJava(provider)

private fun baseModelKt(provider: ArmsPluginTemplateProviderImpl) = """
package ${provider.modelPackageName.value}
import android.app.Application
@Inject
constructor(repositoryManager: IRepositoryManager) {
  
}   
"""

fun buildBeanJava(provider: ArmsPluginTemplateProviderImpl) = """
package ${provider.beanPackageName.value};
${commonAnnotation(provider)}
public class ${provider.pageName.value}Bean {

}   
"""