package com.brain.daggerfiller

import com.android.build.gradle.BaseExtension
import com.brain.daggerfiller.filler.DaggerFillerTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class DaggerFillerPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println "Register daggerfiller plugin"
        BaseExtension appExtension = project.extensions.findByType(BaseExtension.class)
        appExtension.registerTransform(new DaggerFillerTransform(project))
    }
}