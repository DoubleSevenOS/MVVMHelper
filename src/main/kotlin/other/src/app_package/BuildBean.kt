package other.src.app_package

import other.ArmsPluginTemplateProviderImpl
import other.commonAnnotation

fun baseBean(isKt: Boolean, provider: ArmsPluginTemplateProviderImpl) = if (isKt) baseModelKt(provider) else buildBeanJava(provider)

private fun baseModelKt(provider: ArmsPluginTemplateProviderImpl) = """
package ${provider.modelPackageName.value}
${commonAnnotation(provider)}
class${provider.pageName.value}Bean {

} 
"""

fun buildBeanJava(provider: ArmsPluginTemplateProviderImpl) = """
package ${provider.beanPackageName.value};
${commonAnnotation(provider)}
public class ${provider.pageName.value}Bean {

}   
"""