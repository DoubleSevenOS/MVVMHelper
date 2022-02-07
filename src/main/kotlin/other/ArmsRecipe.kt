package other

import com.android.tools.idea.wizard.template.Language
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.lint.detector.api.isXmlFile
import other.res.layout.simpleLayout
import other.src.app_package.*
import other.src.armsManifest
import java.io.File

fun RecipeExecutor.armsRecipe(provider: ArmsPluginTemplateProviderImpl, data: ModuleTemplateData) {
    if (provider.needActivity.value) {
        mergeXml(armsManifest(provider, data), File(data.manifestDir, "AndroidManifest.xml"))
    }
    /**
     * 判断当前语言java / kt
     */
    val languageSuffix = if (data.projectTemplateData.language == Language.Java) "java" else "kt"
    var isKt = data.projectTemplateData.language == Language.Kotlin

//****************************************************************************************************************
//    val testFile = File(
//        data.rootDir,
//        "${fFmSlashedPackageName(provider.fragmentPackageName.value)}/testFile"
//    )
//    save(baseFragmentLocal(isKt, provider, data), testFile)
//****************************************************************************************************************

    /**
     * 创建activity
     */
    if (provider.needActivity.value) {
        val activityFile = File(
            data.rootDir,
            "${fFmSlashedPackageName(provider.activityPackageName.value)}/${provider.pageName.value}Activity.$languageSuffix"
        )
        if (isExitFile(data, activityFile = true)) {
            save(readTargetFile(data, provider, activityFile = true), activityFile)
        } else {
            save(baseActivity(isKt, provider, data), activityFile)
        }
        open(activityFile)
    }
    /**
     * 创建 activity  xml
     */
    if (provider.needActivity.value && provider.generateActivityLayout.value) {
        val xmlFile = File(data.resDir, "layout/${provider.activityLayoutName.value}.xml")
        /**
         * 创建 activity  xml
         */
        if (isExitFile(data, xmlFile = true)) {
            save(readTargetFile(data, provider, xmlFile = true), xmlFile)
        } else {
            save(simpleLayout(provider), xmlFile)
        }
    }
    /**
     * 创建fragment
     */
    if (provider.needFragment.value) {
        val fragmentFile = File(
            data.rootDir,
            "${fFmSlashedPackageName(provider.fragmentPackageName.value)}/${provider.pageName.value}Fragment.$languageSuffix"
        )
        if (isExitFile(data, fragmentFile = true)) {
            save(readTargetFile(data, provider, fragmentFile = true), fragmentFile)
        } else {
            save(baseFragment(isKt, provider, data), fragmentFile)
        }
        open(fragmentFile)
    }
    /**
     * 创建 fragment  xml
     */
    if (provider.needFragment.value && provider.generateFragmentLayout.value) {
        val xmlFile = File(data.resDir, "layout/${provider.fragmentLayoutName.value}.xml")
        if (isExitFile(data, xmlFile = true)) {
            save(readTargetFile(data, provider, xmlFile = true), xmlFile)
        } else {
            save(simpleLayout(provider), xmlFile)
        }
    }
    /**
     * 创建viewmodel
     */
    if (provider.needViewModel.value) {
        val presenterFile = File(
            data.rootDir,
            "${fFmSlashedPackageName(provider.viewModelPackageName.value)}/${provider.pageName.value}ViewModel.$languageSuffix"
        )
        if (isExitFile(data, vMFile = true)) {
            save(readTargetFile(data, provider, vMFile = true), presenterFile)
        } else {
            save(baseViewModel(isKt, provider), presenterFile)
        }
    }
    /**
     * 创建model
     */
    if (provider.needModel.value) {
        val modelFile = File(
            data.rootDir,
            "${fFmSlashedPackageName(provider.modelPackageName.value)}/${provider.pageName.value}Model.$languageSuffix"
        )
        save(baseModel(isKt, provider), modelFile)
    }
    if (provider.needBean.value) {
        val beanFile = File(
            data.rootDir,
            "${fFmSlashedPackageName(provider.beanPackageName.value)}/${provider.pageName.value}Bean.$languageSuffix"
        )
        save(baseBean(isKt, provider), beanFile)
    }
}

fun fFmSlashedPackageName(oVar: String): String {
    return "src/main/java/${oVar.replace('.', '/')}"
}

fun getRootDirPath(oVar: String): String {
    return "${oVar}/src/main/java/}"
}
