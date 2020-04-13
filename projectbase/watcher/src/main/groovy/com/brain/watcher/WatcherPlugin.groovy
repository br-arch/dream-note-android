package com.brain.watcher

import com.android.build.gradle.BaseExtension
import com.brain.watcher.route.RouteTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class WatcherPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println "Register watcher plugin"
        BaseExtension appExtension = project.extensions.getByType(BaseExtension.class)
        appExtension.registerTransform(new RouteTransform(project))
    }
}