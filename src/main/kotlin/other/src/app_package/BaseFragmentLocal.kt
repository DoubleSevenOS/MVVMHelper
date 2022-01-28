package other.src.app_package

import com.android.tools.idea.wizard.template.ModuleTemplateData
import other.ArmsPluginTemplateProviderImpl

fun baseFragmentLocal(isKt: Boolean, provider: ArmsPluginTemplateProviderImpl, data: ModuleTemplateData) =
    if (isKt) baseFragmentKt(provider, data) else baseFragmentKt(provider, data)

private fun baseFragmentKt(provider: ArmsPluginTemplateProviderImpl, data: ModuleTemplateData): String = """
${other.readTargetFile(data,provider,activityFile = true)}
"""

