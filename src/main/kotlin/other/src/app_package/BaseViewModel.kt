package other.src.app_package

import other.ArmsPluginTemplateProviderImpl
import other.commonAnnotation

fun baseViewModel(isKt: Boolean, provider: ArmsPluginTemplateProviderImpl): String =
    if (isKt) baseViewModelKt(provider) else baseViewModelJava(provider)

private fun baseViewModelKt(provider: ArmsPluginTemplateProviderImpl) = """
package ${provider.viewModelPackageName.value};
import ${provider.mBaseViewModelKtPackage}.${provider.mBaseViewModelKtName}
class ${provider.pageName.value}ViewModel : ${provider.mBaseViewModelKtName}() {
   
}

"""

fun baseViewModelJava(provider: ArmsPluginTemplateProviderImpl) = """
package ${provider.viewModelPackageName.value};
import android.app.Application;
import ${provider.mBaseViewModelJavaPackage}.${provider.mBaseViewModelJavaName};
import androidx.annotation.NonNull;
${commonAnnotation(provider)}
public class ${provider.pageName.value}ViewModel extends BaseViewModel{

    public  ${provider.pageName.value}ViewModel(@NonNull Application application) {
        super(application);
    }
  
}   
"""