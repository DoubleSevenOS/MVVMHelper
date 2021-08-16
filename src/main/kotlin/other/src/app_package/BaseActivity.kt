package other.src.app_package

import com.android.tools.idea.wizard.template.ModuleTemplateData
import other.ArmsPluginTemplateProviderImpl
import other.commonAnnotation

fun baseActivity(isKt: Boolean, provider: ArmsPluginTemplateProviderImpl, data: ModuleTemplateData) = if (isKt) baseActivityKt(provider) else baseActivityJava(provider, data)

private fun baseActivityKt(provider: ArmsPluginTemplateProviderImpl) = """
package ${provider.activityPackageName.value}
import android.app.Activity
import android.os.Bundle
import ${provider.moudlePackageName.value}.${provider.pageName.value}Module
import ${provider.presenterPackageName.value}.${provider.pageName.value}Presenter
import ${provider.appPackageName.value}.R
import kotlinx.android.synthetic.main.base_title.*

${commonAnnotation(provider)}
class ${provider.pageName.value}Activity : SimpleBaseActivity<${provider.pageName.value}Presenter>() , ${provider.pageName.value}Contract.View {

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.${provider.activityLayoutName.value} //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 测试测试
    }
    
    override fun initData(savedInstanceState: Bundle?) {
        setToolBar(toolbar, "${provider.pageName.value}")
        
        initListener()
    }
    private fun initListener() {
    
    }
    
    override fun getActivity(): Activity = this
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