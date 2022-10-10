package com.tencao.saoui.api.elements.animator.internal

/**
 * This is a copy from the library LibrarianLib
 * This code is covered under GNU Lesser General Public License v3.0
 */

/**
 * Handles the registering and caching of [ImmutableFieldMutator]s
 */
object ImmutableFieldMutatorHandler {
    private val map = mutableMapOf<Pair<Class<*>, String>, ImmutableFieldMutator<*>?>()
    private val providers = mutableMapOf<Class<*>, ImmutableFieldMutatorProvider<*>>()

    /**
     * Register the [ImmutableFieldMutatorProvider] for the passed class
     */
    fun <T> registerProvider(clazz: Class<T>, provider: ImmutableFieldMutatorProvider<T>) {
        providers[clazz] = provider
    }

    /**
     * Get the mutator for the passed class and field name, if one exists.
     */
    fun getMutator(clazz: Class<*>, field: String): ImmutableFieldMutator<Any>? {
        val key = clazz to field
        if (key !in map) {
            map[key] = providers[clazz]?.getMutatorForImmutableField(field)
        }
        @Suppress("UNCHECKED_CAST")
        return map[key] as ImmutableFieldMutator<Any>?
    }

    init {
        VecMutators
    }
}

/**
 * Provides all the [ImmutableFieldMutator]s for a class.
 */
interface ImmutableFieldMutatorProvider<T> {
    /**
     * Create an [ImmutableFieldMutator] for the field named [name], if appropriate. Else return null.
     */
    fun getMutatorForImmutableField(name: String): ImmutableFieldMutator<T>?
}

/**
 * A mutator for an immutable field. Returns a new instance with the changed value.
 */
@FunctionalInterface
interface ImmutableFieldMutator<T> {
    /**
     * Create a mutated copy of [target] to set this mutator's field to [value]. Return the new value.
     */
    fun mutate(target: T, value: Any?): T
}
