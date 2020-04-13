package com.brain.arch.di

import javax.inject.Scope

/**
 * Indicates that the life cycle of the object is consistent with the fragment
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope {
}