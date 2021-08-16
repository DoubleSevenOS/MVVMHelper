package other

import com.android.tools.idea.wizard.template.Language
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import other.res.layout.simpleLayout
import other.src.app_package.*
import other.src.armsManifest
import java.io.File

fun RecipeExecutor.armsRecipe(provider: ArmsPluginTemplateProviderImpl, data: ModuleTemplateData) {
    if (provider.needActivity.value) {
        mergeXml(armsManifest(provider, data), File(data.manifestDir, "AndroidManifest.xml"))
    }

    if (provider.needActivity.value && provider.generateActivityLayout.value) {
        save(simpleLayout(provider), File(data.resDir, "layout/${provider.activityLayoutName.value}.xml"))
    }

    if (provider.needFragment.value && provider.generateFragmentLayout.value) {
        save(simpleLayout(provider), File(data.resDir, "layout/${provider.fragmentLayoutName.value}.xml"))
    }

    val languageSuffix = if (data.projectTemplateData.language == Language.Java) "java" else "kt"
    val isKt = data.projectTemplateData.language == Language.Kotlin
    if (provider.needActivity.value) {
        val activityFile = File(data.rootDir, "${fFmSlashedPackageName(provider.activityPackageName.value)}/${provider.pageName.value}Activity.$languageSuffix")
        save(baseActivity(isKt, provider,data), activityFile)
        open(activityFile)
    }
    if (provider.needFragment.value) {
        val fragmentFile = File(data.rootDir, "${fFmSlashedPackageName(provider.fragmentPackageName.value)}/${provider.pageName.value}Fragment.$languageSuffix")
        save(baseFragment(isKt, provider,data), fragmentFile)
        open(fragmentFile)
    }

    if (provider.needViewModel.value) {
        val presenterFile = File(data.rootDir, "${fFmSlashedPackageName(provider.presenterPackageName.value)}/${provider.pageName.value}ViewModel.$languageSuffix")
        save(baseViewModel(isKt, provider), presenterFile)
    }
    if (provider.needModel.value) {
        val modelFile = File(data.rootDir, "${fFmSlashedPackageName(provider.modelPackageName.value)}/${provider.pageName.value}Model.$languageSuffix")
        save(baseModel(isKt, provider), modelFile)
    }
    if (provider.needBean.value) {
        val beanFile = File(getRootDirPath("/Users/hyy/WorkSpace/lanshan/testcode"), "${fFmSlashedPackageName(provider.beanPackageName.value)}/${provider.pageName.value}Bean.$languageSuffix")
        save(baseBean(isKt, provider), beanFile)
    }

}

fun fFmSlashedPackageName(oVar: String): String {
    return "src/main/java/${oVar.replace('.', '/')}"
}

fun getRootDirPath(oVar: String): String {
    return "${oVar}/src/main/java/}"
}