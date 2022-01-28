package other.src.app_package

import other.ArmsPluginTemplateProviderImpl
import other.commonAnnotation

fun baseModel(isKt: Boolean, provider: ArmsPluginTemplateProviderImpl) = if (isKt) baseModelKt(provider) else baseModelJava(provider)

private fun baseModelKt(provider: ArmsPluginTemplateProviderImpl) = """
package ${provider.modelPackageName.value}
${other.commonAnnotation(provider)}
class ${provider.pageName.value}Model {

}    
"""


fun baseModelJava(provider: ArmsPluginTemplateProviderImpl) = """
package ${provider.modelPackageName.value};
${commonAnnotation(provider)}
public class ${provider.pageName.value}Model {

}   
"""