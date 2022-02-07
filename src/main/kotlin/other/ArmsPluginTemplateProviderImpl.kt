package other

import com.android.tools.idea.wizard.template.*
import com.android.tools.idea.wizard.template.impl.activities.common.MIN_API
import java.io.File


/**
 * Created on 2021/4/19 16:53
 * module name is ArmsPluginTemplateProviderImpl
 */
class ArmsPluginTemplateProviderImpl : WizardTemplateProvider() {
    override fun getTemplates(): List<Template> = listOf(armsTemplate)

    //相对路径，鼠标开始点击创建的位置
    private val fragmentDir = ".fragment"
    private val activityDir = ".activity"
    private val vmDir = ".viewmodel"
    private val modelDir = ".model"
    lateinit var moduleTemplateData: ModuleTemplateData

    val mBaseActivityJavaName = "BaseDataBindingActivity"
    val mBaseActivityJavaPackage = "com.android.base.activity"

    val mBaseActivityKtName = "BaseDataBindingActivity"
    val mBaseActivityKtPackage = "com.android.basekt.activity"

    val mBaseFragmentKtName = "BaseDataBindFragment"
    val mBaseFragmentKtPackage = "com.android.basekt.fragment"

    val mBaseFragmentJavaName = "BaseDataBindFragment"
    val mBaseFragmentJavaPackage = "com.android.base.fragment"

    val mBaseViewModelJavaName = "BaseViewModel"
    val mBaseViewModelJavaPackage = "com.android.base.viewmodel"

    val mBaseViewModelKtName = "BaseViewModel"
    val mBaseViewModelKtPackage = "com.android.basekt.viewmodel"


    private  val armsTemplate: Template
        get() = template {
            revision = 1
            name = "Android MVVM Helper"
            description = "一键生成模板代码，爽歪歪"
            minApi = MIN_API
            minBuildApi = MIN_API
            category = Category.Other
            formFactor = FormFactor.Mobile
            screens = listOf(
                WizardUiContext.ActivityGallery,
                WizardUiContext.MenuEntry,
                WizardUiContext.NewProject,
                WizardUiContext.NewModule
            )
            thumb { File("template_blank_activity.png") }

            widgets(
                TextFieldWidget(pageName),
                PackageNameWidget(appPackageName),
                CheckBoxWidget(needActivity),
                TextFieldWidget(activityPackageName),
                TextFieldWidget(activityLayoutName),
                //默认选中Activity，就生成xml
                //CheckBoxWidget(generateActivityLayout),

                CheckBoxWidget(needFragment),
                TextFieldWidget(fragmentPackageName),
                TextFieldWidget(fragmentLayoutName),
                //默认选中Fragment，就生成xml
                //CheckBoxWidget(generateFragmentLayout),

                //是否需要VM，默认选中，不可选择
                CheckBoxWidget(needViewModel),
                TextFieldWidget(viewModelPackageName),
                CheckBoxWidget(needModel),
                TextFieldWidget(modelPackageName),
                CheckBoxWidget(needBean),
                TextFieldWidget(beanPackageName),
                LanguageWidget()
            )

            //创建所需文件
            recipe = { te ->
                //val (projectData, srcOut, resOut) = te as ModuleTemplateDat
                moduleTemplateData = (te as ModuleTemplateData)
                println("===armsRecipe====")
                armsRecipe(this@ArmsPluginTemplateProviderImpl, moduleTemplateData)
            }


        }


    /** 新建页面名称 */
    val pageName = stringParameter {
        name = "Activity/Fragment Name"
        constraints = listOf(Constraint.UNIQUE, Constraint.NONEMPTY, Constraint.STRING)
        default = "Main"
        help = "请填写页面名,如填写 Main,会自动生成 MainActivity, MainViewModel 等文件"
    }

    /** 包名 */
    val appPackageName = stringParameter {
        name = "Moudle Package Name "
        constraints = listOf(Constraint.PACKAGE)
        default = listOf(Constraint.PACKAGE)[0].toString()
        help = "请填写你的项目包名,请认真核实此包名是否是正确的项目包名,不能包含子包,正确的格式如:me.jessyan.arms"
    }

    /** 是否需要 Activity */
    val needActivity = booleanParameter {
        name = "Generate Activity"
        default = false
        help = "是否需要生成 Activity ? 不勾选则不生成"
    }

    /** layout xml 文件 */
    val activityLayoutName = stringParameter {
        name = "Activity Layout Name"
        constraints = listOf(Constraint.LAYOUT, Constraint.NONEMPTY)
        suggest = { activityToLayout(pageName.value) }
        default = "activity_main"
        visible = { needActivity.value }
        help = "Activity 创建之前需要填写 Activity 的布局名,若布局已创建就直接填写此布局名,若还没创建此布局,请勾选下面的单选框"
    }

    /** 是否需要 layout xml 文件 */
    val generateActivityLayout = booleanParameter {
        name = "Generate Activity Layout"
        default = true
        visible = { needActivity.value }
        help = "是否需要给 Activity 生成布局? 若勾选,则使用上面的布局名给此 Activity 创建默认的布局"
    }

    /** Activity 路径 */
    val activityPackageName = stringParameter {
        name = ""
        constraints = listOf(Constraint.PACKAGE, Constraint.STRING)
        suggest = { "${appPackageName.value}${activityDir}" }
        visible = { needActivity.value }
        default = "${appPackageName.value}${activityDir}"
        help = "Activity 将被输出到此包下,请认真核实此包名是否是你需要输出的目标包名"
    }

    /** 是否需要 Fragment */
    val needFragment = booleanParameter {
        name = "Generate Fragment"
        default = false
        help = "是否需要生成 Fragment ? 不勾选则不生成"
    }

    /** Fragment xml 文件 */
    val fragmentLayoutName = stringParameter {
        name = "Fragment Layout Name"
        constraints = listOf(Constraint.LAYOUT, Constraint.NONEMPTY)
        suggest = { "fragment_${classToResource(pageName.value)}" }
        default = "fragment_main"
        visible = { needFragment.value }
        help = "Fragment 创建之前需要填写 Fragment 的布局名,若布局已创建就直接填写此布局名,若还没创建此布局,请勾选下面的单选框"
    }

    /** 是否需要生成 Fragment layout 文件 */
    val generateFragmentLayout = booleanParameter {
        name = "Generate Fragment Layout"
        default = true
        visible = { needFragment.value }
        help = "是否需要给 Fragment 生成布局? 若勾选,则使用上面的布局名给此 Fragment 创建默认的布局"
    }

    /** fragment 路径 */
    val fragmentPackageName = stringParameter {
        name = ""
        constraints = listOf(Constraint.PACKAGE, Constraint.STRING)
        suggest = { "${appPackageName.value}${fragmentDir}" }
        default = "${appPackageName.value}${fragmentDir}"
        visible = { needFragment.value }
        help = "Fragment 将被输出到此包下,请认真核实此包名是否是你需要输出的目标包名"
    }


    val needViewModel = booleanParameter {
        name = "Generate ViewModel"
        default = false
        help = "是否需要生成 VM ? 不勾选则不生成"
    }
    val viewModelPackageName = stringParameter {
        name = "VM Package Name"
        constraints = listOf(Constraint.PACKAGE, Constraint.STRING)
        suggest = { "${appPackageName.value}${vmDir}" }
        default = "${appPackageName.value}${vmDir}"
        visible = { needViewModel.value }
        help = "ViewModel 将被输出到此包下,请认真核实此包名是否是你需要输出的目标包名"
    }
    val needModel = booleanParameter {
        name = "Generate Model"
        default = false
        help = "是否需要生成 Model ? 不勾选则不生成"
    }
    val modelPackageName = stringParameter {
        name = "Model Package Name"
        constraints = listOf(Constraint.PACKAGE, Constraint.STRING)
        suggest = { "${appPackageName.value}${modelDir}" }
        default = "${appPackageName.value}${modelDir}"
        visible = { needModel.value }
        help = "Model 将被输出到此包下,请认真核实此包名是否是你需要输出的目标包名"
    }

    val needBean = booleanParameter {
        name = "Generate Bean"
        default = false
        help = "是否需要生成 Bean ? 不勾选则不生成"
    }
    val beanPackageName = stringParameter {
        name = "Bean Package Name"
        constraints = listOf(Constraint.PACKAGE, Constraint.STRING)
        suggest = { "${appPackageName.value}.bean" }
        default = "${appPackageName.value}.bean"
        visible = { needBean.value }
        help = "Bean 将被输出到此包下,请认真核实此包名是否是你需要输出的目标包名"
    }
//    ${
//        if (data.projectTemplateData.applicationPackage == null || data.projectTemplateData.applicationPackage!!.length == 0) {
//            """${provider.presenterPackageName.value}"""
//        } else {
//            """${data.projectTemplateData.applicationPackage}"""
//        }
//    }

}