package other

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

private var fragmentFileName = "targetFragment"
private var activityFileName = "targetActivity"
private var viewModelFileName = "targetViewModel"
private var xmlFileName = "targetXml"

private var activityPackageNameFlag = "{activityPackageName}"
private var fragmentPackageNameFlag = "{fragmentPackageName}"
private var appRPathFlag = "{appRPath}"
private var activityDataBindingPathFlag = "{activityDataBindingPath}"
private var fragmentDataBindingPathPathFlag = "{fragmentDataBindingPath}"
private var viewModelPathFlag = "{viewModelPath}"
private var baseActivityPathFlag = "{baseActivityPath}"
private var baseFragmentPathFlag = "{baseFragmentPath}"

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
        .replace(activityPackageNameFlag, "${provider.activityPackageName.value}")
        .replace(fragmentPackageNameFlag, "${provider.fragmentPackageName.value}")
        .replace(appRPathFlag, "${appRPath(provider, data)}.R")
        .replace(
            activityDataBindingPathFlag,
            "${appRPath(provider, data)}.databinding.Activity${provider.pageName.value}Binding"
        )
        .replace(viewModelPathFlag, "${provider.appPackageName.value}.viewmodel.${provider.pageName.value}ViewModel")
        .replace(baseActivityPathFlag, "${provider.mBaseActivityJavaPackage}.${provider.mBaseActivityJavaName}")
        .replace(pageNameFlag, "${provider.pageName.value}")
        .replace(fragmentDataBindingPathPathFlag, "${appRPath(provider, data)}.databinding.Fragment${provider.pageName.value}Binding")
        .replace(baseFragmentPathFlag, "${provider.mBaseFragmentJavaPackage}.${provider.mBaseFragmentJavaName}")

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
        fileName = fragmentFileName
    } else if (activityFile) {
        fileName = activityFileName
    } else if (vMFile) {
        fileName = viewModelFileName
    } else if (xmlFile) {
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
        fileName = fragmentFileName
    } else if (activityFile) {
        fileName = activityFileName
    } else if (vMFile) {
        fileName = viewModelFileName
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
