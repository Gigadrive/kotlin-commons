package com.gigadrivegroup.kotlincommons.manager

/**
 * Represents a static manager that holds, fetches or manages data.
 * Should be bound to dependency injection for easier use.
 */
public open class CommonsManager {
	init {
		managers.add(this)
	}

	/**
	 * Shuts down this [CommonsManager] and removes it from the list of loaded managers.
	 * Implementations of this function need to include a super call.
	 */
	public open fun shutdown() {
		managers.remove(this)
	}

	public companion object {
		/**
		 * Holds all [CommonsManager] instances that are currently loaded.
		 */
		public val managers: MutableSet<CommonsManager> = mutableSetOf()

		/**
		 * Stops all [CommonsManager] instances currently loaded in [managers].
		 */
		public fun shutdown() {

		}
	}
}
