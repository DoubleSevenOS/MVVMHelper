package other

import com.android.tools.idea.wizard.template.Language
import com.android.tools.idea.wizard.template.ModuleTemplateData
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun commonAnnotation(provider: ArmsPluginTemplateProviderImpl) = """
/**
 * @Created on ${SimpleDateFormat("yyyy/MM/dd HH:mm").format(Date(System.currentTimeMillis()))}
 * @Author 
 * @Describe  
 */
""".trimIndent()

private var fragmentKtFileName = "targetKtFragment"
private var activityKtFileName = "targetKtActivity"
private var viewModelKtFileName = "targetKtViewModel"

private var fragmentFileName = "targetFragment"
private var activityFileName = "targetActivity"
private var viewModelFileName = "targetViewModel"
private var xmlFileName = "targetXml"


private var appRPathFlag = "{appRPath}"
private var baseActivityPathFlag = "{baseActivityPath}"
private var activityPackageNameFlag = "{activityPackageName}"
private var activityDataBindingPathFlag = "{activityDataBindingPath}"
private var baseFragmentPathFlag = "{baseFragmentPath}"
private var fragmentPackageNameFlag = "{fragmentPackageName}"
private var fragmentDataBindingPathPathFlag = "{fragmentDataBindingPath}"
private var baseViewModelPathFlag = "{baseViewModelPath}"
private var viewModelPackageNameFlag = "{viewModelPackageName}"
private var viewModelPathFlag = "{viewModelPath}"
private var pageNameFlag = "{pageName}"
private var pageXmlFlag = "{layoutId}"

fun replaceFileFlag(
    fileContent: String,
    data: ModuleTemplateData,
    provider: ArmsPluginTemplateProviderImpl,
    fragmentFile: Boolean = false,
    activityFile: Boolean = false,
    vMFile: Boolean = false,
    xmlFile: Boolean = false,
): String {
    var content = fileContent
        .replaceActivityFlag(data, provider)
        .replaceFragmentFlag(data, provider)
        .replaceViewModelFlag(data, provider)
        .replace(pageNameFlag, "${provider.pageName.value}")
        .replace(appRPathFlag, "${appRPath(provider, data)}.R")


    if (activityFile) {
        content = content.replace(pageXmlFlag, "R.layout.${provider.activityLayoutName.value}")
    }

    if (fragmentFile) {
        content = content.replace(pageXmlFlag, "R.layout.${provider.fragmentLayoutName.value}")
    }


    return content
}

fun readTargetFile(
    data: ModuleTemplateData,
    provider: ArmsPluginTemplateProviderImpl,
    fragmentFile: Boolean = false,
    activityFile: Boolean = false,
    vMFile: Boolean = false,
    xmlFile: Boolean = false,
): String {
    var fileName = ""
    if (fragmentFile) {
        fileName = if (data.isJavaLanguage()) fragmentFileName else fragmentKtFileName
    } else if (activityFile) {
        fileName = if (data.isJavaLanguage()) activityFileName else activityKtFileName
    } else if (vMFile) {
        fileName = if (data.isJavaLanguage()) viewModelFileName else viewModelKtFileName
    }else if (xmlFile) {
        fileName = xmlFileName
    }
    var parentFile = data.rootDir.parentFile
    var path = parentFile.absolutePath + File.separator + fileName
    var fileChild = File(path)
    var contents = fileChild.readLines()
    var result = ""
    contents.forEach {
        result = result + it + "\n"
    }

    return replaceFileFlag(
        result,
        data,
        provider,
        activityFile = activityFile,
        fragmentFile = fragmentFile,
        vMFile = vMFile,
        xmlFile = xmlFile
    )
}

fun isExitFile(
    data: ModuleTemplateData,
    fragmentFile: Boolean = false,
    activityFile: Boolean = false,
    vMFile: Boolean = false,
    xmlFile: Boolean = false,
): Boolean {
    var fileName = ""
    if (fragmentFile) {
        fileName = if (data.isJavaLanguage()) fragmentFileName else fragmentKtFileName
    } else if (activityFile) {
        fileName = if (data.isJavaLanguage()) activityFileName else activityKtFileName
    } else if (vMFile) {
        fileName = if (data.isJavaLanguage()) viewModelFileName else viewModelKtFileName
    } else if (xmlFile) {
        fileName = xmlFileName
    }
    var parentFile = data.rootDir.parentFile
    var path = parentFile.absolutePath + File.separator + fileName
    var fileChild = File(path)
    return fileChild.isFile
}

fun appRPath(provider: ArmsPluginTemplateProviderImpl, moduleTemplateData: ModuleTemplateData): String {
    if (moduleTemplateData.projectTemplateData.applicationPackage == null || moduleTemplateData.projectTemplateData.applicationPackage!!.isEmpty()) {
        return moduleTemplateData.packageName
    } else {
        return moduleTemplateData.projectTemplateData.applicationPackage!!
    }
    return ""
}

//替换Activity文件相关关键字
fun String.replaceActivityFlag(
    data: ModuleTemplateData,
    provider: ArmsPluginTemplateProviderImpl,
): String {
    return this.replace(activityPackageNameFlag, "${provider.activityPackageName.value}")
        .replace(baseActivityPathFlag, "${provider.mBaseActivityJavaPackage}.${provider.mBaseActivityJavaName}")
        .replace(
            activityDataBindingPathFlag,
            "${appRPath(provider, data)}.databinding.Activity${provider.pageName.value}Binding"
        )
}

//替换Fragment文件相关关键字
fun String.replaceFragmentFlag(
    data: ModuleTemplateData,
    provider: ArmsPluginTemplateProviderImpl,
): String {
    return this.replace(fragmentPackageNameFlag, "${provider.fragmentPackageName.value}")
        .replace(baseFragmentPathFlag, "${provider.mBaseFragmentJavaPackage}.${provider.mBaseFragmentJavaName}")
        .replace(
            fragmentDataBindingPathPathFlag,
            "${appRPath(provider, data)}.databinding.Fragment${provider.pageName.value}Binding"
        )
}

//替换viewmodel相关关键字
fun String.replaceViewModelFlag(
    data: ModuleTemplateData,
    provider: ArmsPluginTemplateProviderImpl,
): String {
    return this.replace(viewModelPackageNameFlag, "${provider.viewModelPackageName.value}")
        .replace(baseViewModelPathFlag, "${provider.mBaseViewModelJavaPackage}.${provider.mBaseViewModelJavaName}")
        .replace(viewModelPathFlag, "${provider.viewModelPackageName.value}.${provider.pageName.value}ViewModel")
}

fun ModuleTemplateData.isJavaLanguage(): Boolean {
    return this.projectTemplateData.language == Language.Java
}