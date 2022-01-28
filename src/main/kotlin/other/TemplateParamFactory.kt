package com.fdd.mvvmgenerator.core

import org.jetbrains.android.dom.manifest.Manifest
import org.jetbrains.android.facet.AndroidFacet
import org.jetbrains.android.facet.AndroidRootUtil
import org.jetbrains.android.util.AndroidUtils

/**
 * Created by hxs
 * Description : template参数工厂
 */

object TemplateParamFactory {

    //R文件包名
    private fun getRPackage(facet: AndroidFacet): String {
//        //找到当前的module
//        val module = ModuleUtil.findModuleForFile(dir.virtualFile, mProject!!)
//        val facet = AndroidFacet.getInstance(module!!)

        val manifestFile = AndroidRootUtil.getManifestFileForCompiler(facet) ?: return ""
        val manifest = AndroidUtils.loadDomElement(facet.module, manifestFile, Manifest::class.java)
        return manifest?.getPackage()?.value ?: ""
    }

}

