package other.src.app_package

import com.android.tools.idea.wizard.template.ModuleTemplateData
import other.ArmsPluginTemplateProviderImpl
import other.commonAnnotation

fun baseFragment(isKt: Boolean, provider: ArmsPluginTemplateProviderImpl, data: ModuleTemplateData) = if (isKt) baseFragmentKt(provider) else baseFragmentJava(provider, data)

private fun baseFragmentKt(provider: ArmsPluginTemplateProviderImpl) = """
package ${provider.fragmentPackageName.value}
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.skytech.iglobalwin.app.base.SimpleBaseFragment
import ${provider.moudlePackageName.value}.${provider.pageName.value}Module
import ${provider.presenterPackageName.value}.${provider.pageName.value}Presenter
import ${provider.appPackageName.value}.R
import kotlinx.android.synthetic.main.base_title.*

${commonAnnotation(provider)}
class ${provider.pageName.value}Fragment : SimpleBaseFragment<${provider.pageName.value}Presenter>() , ${provider.pageName.value}Contract.View{
    companion object {
    fun newInstance():${provider.pageName.value}Fragment {
        val fragment = ${provider.pageName.value}Fragment()
        return fragment
    }
    }
    override fun setupFragmentComponent(appComponent:AppComponent) {
        Dagger${provider.pageName.value}Component //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .${provider.pageName.value[0].toLowerCase()}${provider.pageName.value.substring(1, provider.pageName.value.length)}Module(${provider.pageName.value}Module(this))
                .build()
                .inject(this)
    }
    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):View{
        return inflater.inflate(R.layout.${provider.fragmentLayoutName.value}, container, false)
    }
    /**
     * 在 onActivityCreate()时调用
     */
    override fun initData(savedInstanceState: Bundle?) {
        setToolBarNoBack(toolbar, "${provider.pageName.value}")
        
        initListener()
    }
    
    private fun initListener() {
    
    }
    
    override fun getFragment(): Fragment = this
}
    
"""


fun baseFragmentJava(provider: ArmsPluginTemplateProviderImpl, data: ModuleTemplateData) = """
package ${provider.fragmentPackageName.value};
import androidx.annotation.NonNull;
import com.lanshan.base.base.BaseDataBindingConfig;
import com.lanshan.base.fragment.BaseDataBindFragment;
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
}.databinding.Fragment${provider.pageName.value}Binding;
import ${provider.appPackageName.value}.vm.${provider.pageName.value}ViewModel;

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