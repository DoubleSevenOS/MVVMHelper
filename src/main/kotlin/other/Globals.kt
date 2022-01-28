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

private var replaceFileNameFlag = "{fileName}"
private var replaceFileXmlFlag = "{fileXml}"

fun replaceFileFlag(fileContent: String, provider: ArmsPluginTemplateProviderImpl): String {
    return fileContent.replace(replaceFileNameFlag, "${provider.pageName.value}Fragment")
        .replace(replaceFileXmlFlag, "${provider.fragmentLayoutName.value}")
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
    return replaceFileFlag(result, provider)
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
