package other.src.app_package

import com.android.tools.idea.wizard.template.ModuleTemplateData
import other.ArmsPluginTemplateProviderImpl
import other.appRPath
import other.commonAnnotation

fun baseFragment(isKt: Boolean, provider: ArmsPluginTemplateProviderImpl, data: ModuleTemplateData) =
    if (isKt) baseFragmentKt(provider, data) else baseFragmentJava(provider, data)

private fun baseFragmentKt(provider: ArmsPluginTemplateProviderImpl, data: ModuleTemplateData) = """
package ${provider.fragmentPackageName.value}
import ${provider.mBaseFragmentKtPackage}.${provider.mBaseFragmentKtName}
import com.android.basekt.base.BaseDataBindingConfig;
import ${appRPath(provider, data)}.R;
import ${appRPath(provider, data)}.databinding.Fragment${provider.pageName.value}Binding;
import ${provider.appPackageName.value}.viewmodel.${provider.pageName.value}ViewModel;
class ${provider.pageName.value}Fragment : ${provider.mBaseFragmentKtName}<Fragment${provider.pageName.value}Binding,${provider.pageName.value}ViewModel>(){

    companion object {
        @JvmStatic
        fun newInstance() =
            ${provider.pageName.value}Fragment()
    }

    override fun initView() {

    }
    
    override fun initData() {
        mBinding.viewModel=mViewModel
    }

    /**
     * 获取binding相关配置
     *
     * @return
     */
    override fun getDataBindingConfig(): BaseDataBindingConfig {
        return BaseDataBindingConfig(R.layout.${provider.fragmentLayoutName.value})
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

fun baseFragmentJava(provider: ArmsPluginTemplateProviderImpl, data: ModuleTemplateData): String = """
    
package ${provider.fragmentPackageName.value};
import androidx.annotation.NonNull;
import ${appRPath(provider, data)}.R;
import ${appRPath(provider, data)}.databinding.Fragment${provider.pageName.value}Binding;
import com.android.base.base.BaseDataBindingConfig;
import ${provider.appPackageName.value}.viewmodel.${provider.pageName.value}ViewModel;
import ${provider.mBaseFragmentJavaPackage}.${provider.mBaseFragmentJavaName};


${commonAnnotation(provider)}
class ${provider.pageName.value}Fragment extends BaseDataBindFragment<Fragment${provider.pageName.value}Binding,${provider.pageName.value}ViewModel> {
    
    public static ${provider.pageName.value}Fragment newInstance() {
        ${provider.pageName.value}Fragment fragment = new ${provider.pageName.value}Fragment();
        return fragment;
    }
    
     @NonNull
    @Override
    public BaseDataBindingConfig getDataBindingConfig() {
        return new BaseDataBindingConfig(R.layout.${provider.fragmentLayoutName.value});
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