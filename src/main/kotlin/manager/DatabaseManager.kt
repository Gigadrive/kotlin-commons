package com.gigadrivegroup.kotlincommons.manager

import com.gigadrivegroup.kotlincommons.feature.CommonsManager
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * A [CommonsManager] used for establishing a connection to a MySQL server. HikariCP is used as a
 * connection pool.
 *
 * @param databaseHost The hostname of the database server
 * @param databasePort The port of the database server
 * @param databaseName The name of the database to use
 * @param databaseUser The name of the database user to authenticate with
 * @param databasePassword The password of the database user to authenticate with
 */
public class DatabaseManager(
    private val databaseHost: String,
    private val databasePort: Int,
    private val databaseName: String,
    private val databaseUser: String,
    private val databasePassword: String
) : CommonsManager() {
    public lateinit var hikariConfig: HikariConfig
    public lateinit var hikariDataSource: HikariDataSource

    init {
        initHikari()

        println("Successfully connected to the database.")
    }

    /** Initializes HikariCP. */
    private fun initHikari() {
        // load config
        hikariConfig = HikariConfig()
        hikariConfig.jdbcUrl =
            "jdbc:mysql://${databaseHost}:${databasePort}/${databaseName}?serverTimezone=UTC&useSSL=false"
        hikariConfig.username = databaseUser
        hikariConfig.password = databasePassword

        //// initialize connection properties
        // cache prepared statements
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true")

        // amount of prepared statements to cache
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250")

        // maximum limit of prepared statement length in the cache
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")

        // use server-side prepared statements
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true")

        // Use UTF8
        hikariConfig.addDataSourceProperty("characterEncoding", "utf8")
        hikariConfig.addDataSourceProperty("useUnicode", "true")

        // Use UTC timezone
        hikariConfig.connectionInitSql = "SET SESSION sql_mode = 'TRADITIONAL'"

        hikariConfig.leakDetectionThreshold = 60 * 1000
        hikariDataSource = HikariDataSource(hikariConfig)
    }

    /** Shortcut to create a [PreparedStatement] with the specified [sql]. */
    public fun prepareStatement(sql: String): PreparedStatement =
        hikariDataSource.connection.prepareStatement(sql)

    /** Closes a [resultSet]. */
    public fun closeResources(resultSet: ResultSet?): Unit = closeResources(resultSet, null)

    /** Closes a [preparedStatement]. */
    public fun closeResources(preparedStatement: PreparedStatement?): Unit =
        closeResources(null, preparedStatement)

    /** Closes a [resultSet] and a [preparedStatement]. */
    public fun closeResources(resultSet: ResultSet?, preparedStatement: PreparedStatement?): Unit =
        closeResources(resultSet, preparedStatement, preparedStatement?.connection)

    /** Closes a [resultSet], a [preparedStatement] and a [connection]. */
    public fun closeResources(
        resultSet: ResultSet?,
        preparedStatement: PreparedStatement?,
        connection: Connection?
    ) {
        resultSet?.close()
        preparedStatement?.close()
        connection?.close()
    }

    override fun shutdown() {
        super.shutdown()
        hikariDataSource.close()
    }
}
