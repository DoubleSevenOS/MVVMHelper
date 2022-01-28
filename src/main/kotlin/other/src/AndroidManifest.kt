package other.src

import com.android.tools.idea.wizard.template.ModuleTemplateData
import other.ArmsPluginTemplateProviderImpl

fun armsManifest(provider: ArmsPluginTemplateProviderImpl, data: ModuleTemplateData) = """
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="${
    if (data.projectTemplateData.applicationPackage == null || data.projectTemplateData.applicationPackage!!.length == 0) {
        """${data.packageName}"""
    } else {
        """${data.projectTemplateData.applicationPackage}"""
    }
}">
    <application>
${
    if (data.isLibrary) {
        """
        <activity android:name="${provider.activityPackageName.value}.${provider.pageName.value}Activity"
         android:screenOrientation="portrait">
	    </activity> 
    """
    } else {
        """
        <activity
	        android:name="${provider.activityPackageName.value}.${provider.pageName.value}Activity"
            android:screenOrientation="portrait"
	        />
    """
    }
}
    </application>
</manifest>
"""