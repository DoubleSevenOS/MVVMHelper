package other.src.app_package

import com.android.tools.idea.wizard.template.ModuleTemplateData
import other.ArmsPluginTemplateProviderImpl
import other.appRPath
import other.commonAnnotation

fun baseActivity(isKt: Boolean, provider: ArmsPluginTemplateProviderImpl, data: ModuleTemplateData) = if (isKt) baseActivityKt(provider,data) else baseActivityJava(provider, data)

private fun baseActivityKt(provider: ArmsPluginTemplateProviderImpl, data: ModuleTemplateData) = """
package ${provider.activityPackageName.value}
import ${appRPath(provider,data)}.R
import ${appRPath(provider,data)}.databinding.Activity${provider.pageName.value}Binding
import ${provider.appPackageName.value}.viewmodel.${provider.pageName.value}ViewModel
import ${provider.mBaseActivityKtPackage}.${provider.mBaseActivityKtName}
import com.android.basekt.base.BaseDataBindingConfig
${commonAnnotation(provider)}
class ${provider.pageName.value}Activity : ${provider.mBaseActivityKtName}<Activity${provider.pageName.value}Binding,${provider.pageName.value}ViewModel>() {
    override fun initView() {
    }

    override fun initData() {  
    }
    
    /**
     * 获取binding相关配置
     *
     * @return
     */
    override fun getDataBindingConfig(): BaseDataBindingConfig {
        return BaseDataBindingConfig(R.layout.${provider.activityLayoutName.value})
    }
    
    /**
     * 获取viewmodel Clazz
     *
     * @return
     */
    override fun initViewModelClazz(): Class<${provider.pageName.value}ViewModel> {
        return ${provider.pageName.value}ViewModel::class.java
    }
    
}
    
"""

private fun baseActivityJava(provider: ArmsPluginTemplateProviderImpl, data: ModuleTemplateData) = """
package ${provider.activityPackageName.value};
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.os.Bundle;
import com.lanshan.base.activity.BaseDataBindingActivity;
import com.lanshan.base.base.BaseDataBindingConfig;
import ${
    if (data.projectTemplateData.applicationPackage == null || data.projectTemplateData.applicationPackage!!.length == 0) {
        """${provider.moudlePackageName.value}"""
    } else {
        """${data.projectTemplateData.applicationPackage}"""
    }
}.R;
import ${
    if (data.projectTemplateData.applicationPackage == null || data.projectTemplateData.applicationPackage!!.length == 0) {
        """${provider.moudlePackageName.value}"""
    } else {
        """${data.projectTemplateData.applicationPackage}"""
    }
}.databinding.Activity${provider.pageName.value}Binding;
import ${provider.appPackageName.value}.vm.${provider.pageName.value}ViewModel;

${commonAnnotation(provider)}
public class ${provider.pageName.value}Activity extends BaseDataBindingActivity<Activity${provider.pageName.value}Binding,${provider.pageName.value}ViewModel> {

    @NonNull
    @Override
    public BaseDataBindingConfig getDataBindingConfig() {
        return new BaseDataBindingConfig(R.layout.${provider.activityLayoutName.value});
    }

    @NonNull
    @Override
    public Class<${provider.pageName.value}ViewModel> initViewModelClazz() {
        return ${provider.pageName.value}ViewModel.class;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
    
}
    
"""