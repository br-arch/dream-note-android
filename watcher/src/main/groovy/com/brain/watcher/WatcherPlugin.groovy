package com.brain.watcher

import com.android.build.gradle.AppExtension
import com.brain.watcher.route.RouteTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

class WatcherPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        println "Register watcher plugin"
        AppExtension appExtension = project.extensions.getByType(AppExtension)
        appExtension.registerTransform(new RouteTransform(project))
    }
}